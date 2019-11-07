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

    private ZooKeeper zooKeeper;

    private String dir;

    private String node;

    private List<ACL> acls;

    private String fullPath;

    private volatile int state;

    private String id;

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
                zooKeeper.close();
            } catch (InterruptedException e) {
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
