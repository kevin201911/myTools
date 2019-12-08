package com.my.tools.zookeeper.distributedlock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author wq
 * @date 2019/10/30
 */
public class DistributedFairLock implements Lock {
    private static Logger logger = LoggerFactory.getLogger(DistributedFairLock.class);

    //ZooKeeper客户端，进行ZooKeeper操作
    private ZooKeeper zooKeeper;

    //根节点名称
    private String dir;

    //加锁节点
    private String node;

    //ZooKeeper鉴权信息
    private List<ACL> acls;

    //要加锁节点
    private String fullPath;

    //加锁标识，为0时表示未获取到锁，每获取一次锁则加一，释放锁时减一。减到0时断开连接，删除临时节点。
    private volatile int state;

    //当前锁创建的节点id
    private String id;

    //通过CountDownLatch阻塞，直到监听上一节点被取消，再进行后续操作
    private CountDownLatch countDownLatch;

    /**
     * Constructor.
     *
     * @param zooKeeper the zoo keeper
     * @param dir       the dir
     * @param node      the node
     * @param acls      the acls
     */
    public DistributedFairLock(ZooKeeper zooKeeper, String dir, String node, List<ACL> acls) {
        this.zooKeeper = zooKeeper;
        this.dir = dir;
        this.node = node;
        this.acls = acls;
        this.fullPath = dir.concat("/").concat(this.node);
        init();
    }

    private void init() {
        try {
            Stat stat = zooKeeper.exists(dir, false);
            if (stat == null) {
                zooKeeper.create(dir, null, acls, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            logger.error("[DistributedFairLock#init] error : " + e.toString(), e);
        }
    }

    @Override
    public void lock() {
        try {
            synchronized (this) {
                if (state <= 0) {
                    if (id == null) {
                        id = zooKeeper.create(fullPath, null, acls, CreateMode.EPHEMERAL_SEQUENTIAL);
                    }

                    List<String> nodes = zooKeeper.getChildren(dir, false);
                    SortedSet<String> sortedSet = new TreeSet<>();
                    for (String node : nodes) {
                        sortedSet.add(dir.concat("/").concat(node));
                    }


                    SortedSet<String> lessSet = ((TreeSet<String>) sortedSet).headSet(id);

                    if (!lessSet.isEmpty()) {
                        Stat stat = zooKeeper.exists(lessSet.last(), new LockWatcher());
                        if (stat != null) {
                            countDownLatch = new CountDownLatch(1);
                            countDownLatch.await();
                        }

                    }
                }

                state++;
            }
        } catch (InterruptedException e) {
            logger.error("[DistributedFairLock#lock] error : " + e.toString(), e);
            Thread.currentThread().interrupt();
        } catch (KeeperException ke) {
            logger.error("[DistributedFairLock#lock] error : " + ke.toString(), ke);
            if (!KeeperException.Code.NODEEXISTS.equals(ke.code())) {
                Thread.currentThread().interrupt();
            }
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock();
    }

    @Override
    public boolean tryLock() {
        try {
            synchronized (this) {
                if (state <= 0) {
                    if (id == null) {
                        id = zooKeeper.create(fullPath, null, acls, CreateMode.EPHEMERAL_SEQUENTIAL);
                    }

                    List<String> nodes = zooKeeper.getChildren(dir, false);
                    SortedSet<String> sortedSet = new TreeSet<>();
                    for (String node : nodes) {
                        sortedSet.add(dir.concat("/").concat(node));
                    }


                    SortedSet<String> lessSet = ((TreeSet<String>) sortedSet).headSet(id);

                    if (!lessSet.isEmpty()) {
                        return false;
                    }
                }
                state++;
            }
        } catch (InterruptedException e) {
            logger.error("[DistributedFairLock#tryLock] error : " + e.toString(), e);
            return false;
        } catch (KeeperException ke) {
            logger.error("[DistributedFairLock#tryLock] error : " + ke.toString(), ke);
            if (!KeeperException.Code.NODEEXISTS.equals(ke.code())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            synchronized (this) {
                if (state <= 0) {
                    if (id == null) {
                        id = zooKeeper.create(fullPath, null, acls, CreateMode.EPHEMERAL_SEQUENTIAL);
                    }

                    List<String> nodes = zooKeeper.getChildren(dir, false);
                    SortedSet<String> sortedSet = new TreeSet<>();
                    for (String node : nodes) {
                        sortedSet.add(dir.concat("/").concat(node));
                    }


                    SortedSet<String> lessSet = ((TreeSet<String>) sortedSet).headSet(id);

                    if (!lessSet.isEmpty()) {
                        Stat stat = zooKeeper.exists(lessSet.last(), new LockWatcher());
                        if (stat != null) {
                            countDownLatch = new CountDownLatch(1);
                            countDownLatch.await(time, unit);
                        }

                    }
                }

                state++;
            }
        } catch (InterruptedException e) {
            logger.error("[DistributedFairLock#tryLock] error : " + e.toString(), e);
            return false;
        } catch (KeeperException ke) {
            logger.error("[DistributedFairLock#tryLock] error : " + ke.toString(), ke);
            if (!KeeperException.Code.NODEEXISTS.equals(ke.code())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (state > 0) {
                state--;
            }
        }
        if (state == 0 && zooKeeper != null) {
            try {
                zooKeeper.delete(id, -1);
                id = null;
            } catch (Exception e) {
                logger.error("[DistributedFairLock#unlock] error : " + e.toString(), e);
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    private class LockWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            synchronized (this) {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        }
    }
}
