package com.my.tools.zookeeper.distributedlock;

import com.my.tools.zookeeper.distributedqueue.DistributedQueue;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author wq
 * @date 2019/9/22
 */
public class DistributedLock implements Lock {
    private static Logger logger = LoggerFactory.getLogger(DistributedQueue.class);

    private ZooKeeper zooKeeper;

    private String dir;

    private String node;

    private List<ACL> acls;

    private String fullPath;

    private volatile int state;

    /**
     * Constructor.
     *
     * @param zooKeeper the zoo keeper
     * @param dir       the dir
     * @param node      the node
     * @param acls      the acls
     */
    public DistributedLock(ZooKeeper zooKeeper, String dir, String node, List<ACL> acls) {
        this.zooKeeper = zooKeeper;
        this.dir = dir;
        this.node = node;
        this.acls = acls;
        this.fullPath = dir.concat("/").concat(node);
        init();
    }

    private void init() {
        try {
            Stat stat = zooKeeper.exists(dir, false);
            if (stat == null) {
                zooKeeper.create(dir, null, acls, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            logger.error("[DistributedLock#init] error : " + e.toString(), e);
        }
    }

    /**
     * Lock.
     */
    @Override
    public void lock() {
        if (addLockCount()) {
            return;
        }
        for (;;) {
            try {
                zooKeeper.create(fullPath, null, acls, CreateMode.EPHEMERAL);
                state++;
                break;
            } catch (InterruptedException ie) {
                logger.error("[DistributedLock#lock] error : " + ie.toString(), ie);
                Thread.currentThread().interrupt();
            } catch (KeeperException ke) {
                logger.error("[DistributedLock#lock] error : " + ke.toString(), ke);
                if (!KeeperException.Code.NODEEXISTS.equals(ke.code())) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Lock interruptibly.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (addLockCount()) {
            return;
        }
        for (;;) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            try {
                zooKeeper.create(fullPath, null, acls, CreateMode.EPHEMERAL);
                break;
            } catch (InterruptedException ie) {
                logger.error("[DistributedLock#lockInterruptibly] error : " + ie.toString(), ie);
                Thread.currentThread().interrupt();
            } catch (KeeperException ke) {
                logger.error("[DistributedLock#lockInterruptibly] error : " + ke.toString(), ke);
                if (!KeeperException.Code.NODEEXISTS.equals(ke.code())) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Try lock boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean tryLock() {
        if (addLockCount()) {
            return true;
        }

        try {
            zooKeeper.create(fullPath, null, acls, CreateMode.EPHEMERAL);
            return true;
        } catch (Exception e) {
            logger.error("[DistributedLock#tryLock] error : " + e.toString(), e);
        }

        return false;
    }

    /**
     * Try lock boolean.
     *
     * @param time the time
     * @param unit the unit
     * @return the boolean
     * @throws InterruptedException the interrupted exception
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (addLockCount()) {
            return true;
        }

        long nanosTimeout = unit.toNanos(time);
        if (nanosTimeout <= 0L) {
            return false;
        }

        final long deadline = System.nanoTime() + nanosTimeout;
        for (;;) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }

            nanosTimeout = deadline - System.nanoTime();
            if (nanosTimeout <= 0L) {
                return false;
            }
            try {
                zooKeeper.create(fullPath, null, acls, CreateMode.EPHEMERAL);
                return true;
            } catch (InterruptedException ie) {
                logger.error("[DistributedLock#tryLock] error : " + ie.toString(), ie);
                return false;
            } catch (KeeperException ke) {
                logger.error("[DistributedLock#tryLock] error : " + ke.toString(), ke);
                if (!KeeperException.Code.NODEEXISTS.equals(ke.code())) {
                    return false;
                }
            }
        }
    }

    /**
     * Unlock.
     */
    @Override
    public void unlock() {
        delLockCount();
        if (state == 0 && zooKeeper != null) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                logger.error("[DistributedLock#unlock] error : " + e.toString(), e);
            }
        }
    }

    /**
     * New condition condition.
     *
     * @return the condition
     */
    @Override
    public Condition newCondition() {
        return null;
    }

    private boolean addLockCount() {
        if (state > 0) {
            synchronized (this) {
                if (state > 0) {
                    state++;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean delLockCount() {
        if (state > 0) {
            synchronized (this) {
                if (state > 0) {
                    state--;
                    return true;
                }
            }
        }
        return false;
    }
}
