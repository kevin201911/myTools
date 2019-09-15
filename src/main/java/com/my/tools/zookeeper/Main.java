package com.my.tools.zookeeper;

import com.my.tools.zookeeper.distributedqueue.DistributedQueue;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;


/**
 * @author wq
 * @date 2019/9/8
 */
public class Main implements Watcher {
    private static final String DIR = "/distributed";
    private static final String NODE = "data";
    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("localhost", 2181, null);
        DistributedQueue queue = new DistributedQueue(zooKeeper, DIR, NODE, ZooDefs.Ids.OPEN_ACL_UNSAFE);
        //queue.offer("test1111");
        Object object = queue.poll();

        System.out.println(object);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("process");
    }
}
