package com.my.tools.zookeeper.distributedqueue;

import com.my.tools.zookeeper.utils.ConversionUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author wq
 * @date 2019/9/8
 */
public class DistributedQueue<E> extends AbstractQueue<E> implements Queue<E> {
    private static Logger logger = LoggerFactory.getLogger(DistributedQueue.class);
    private ZooKeeper zooKeeper;

    private String dir;

    private String node;

    private List<ACL> acls;

    /**
     * Constructor.
     *
     * @param zooKeeper the zoo keeper
     * @param dir       the dir
     * @param node      the node
     * @param acls      the acls
     */
    public DistributedQueue (ZooKeeper zooKeeper, String dir, String node, List<ACL> acls) {
        this.zooKeeper = zooKeeper;
        this.dir = dir;
        this.node = node;
        this.acls = acls;
        init();
    }


    private void init() {
        try {
            Stat stat = zooKeeper.exists(dir, false);
            if (stat == null) {
                zooKeeper.create(dir, null, acls, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            logger.error("[DistributedQueue#init] error : " + e.toString(), e);
        }
    }

    /**
     * Iterator iterator.
     *
     * @return the iterator
     */
    @Override
    public Iterator iterator() {
        return null;
    }

    /**
     * Size int.
     *
     * @return the int
     */
    @Override
    public int size() {
        try {
            List<String> children = zooKeeper.getChildren(dir, null);
            return children.size();
        } catch (Exception e) {
            logger.error("[DistributedQueue#offer] size : " + e.toString(), e);
        }

        return 0;
    }

    /**
     * Offer boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean offer(E o) {
        String fullPath = dir.concat("/").concat(node);
        try {
            zooKeeper.create(fullPath, ConversionUtil.objectToBytes(o), acls, CreateMode.PERSISTENT_SEQUENTIAL);
            return true;
        } catch (Exception e) {
            logger.error("[DistributedQueue#offer] error : " + e.toString(), e);
        }
        return false;
    }

    /**
     * Poll e.
     *
     * @return the e
     */
    @Override
    public E poll() {
        try {
            List<String> children = zooKeeper.getChildren(dir, null);
            if (children == null || children.isEmpty()) {
                return null;
            }

            Collections.sort(children);
            for (String child : children) {
                String fullPath = dir.concat("/").concat(child);
                try {
                    byte[] bytes = zooKeeper.getData(fullPath, false, null);
                    E data = (E) ConversionUtil.bytesToObject(bytes);
                    zooKeeper.delete(fullPath, -1);
                    return data;
                } catch (Exception e) {
                    logger.warn("[DistributedQueue#poll] warn : " + e.toString(), e);
                }
            }

        } catch (Exception e) {
            logger.error("[DistributedQueue#peek] poll : " + e.toString(), e);
        }

        return null;
    }

    /**
     * Peek e.
     *
     * @return the e
     */
    @Override
    public E peek() {
        try {
            List<String> children = zooKeeper.getChildren(dir, null);
            if (children == null || children.isEmpty()) {
                return null;
            }

            Collections.sort(children);
            for (String child : children) {
                String fullPath = dir.concat("/").concat(child);
                try {
                    byte[] bytes = zooKeeper.getData(fullPath, false, null);
                    E data = (E) ConversionUtil.bytesToObject(bytes);
                    return data;
                } catch (Exception e) {
                    logger.warn("[DistributedQueue#peek] warn : " + e.toString(), e);
                }
            }

        } catch (Exception e) {
            logger.error("[DistributedQueue#peek] warn : " + e.toString(), e);
        }

        return null;
    }



}
