package com.my.tools.zookeeper.distributedlock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author wq
 * @date 2019/9/22
 */
public class Main implements Watcher {
    private static final String DIR = "/distributedlock";
    private static final String NODE = "data";
    public static void main(String[] args) throws Exception {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                ZooKeeper zooKeeper = null;
                try {
                    zooKeeper = new ZooKeeper("localhost", 2181, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DistributedLock lock1 = new DistributedLock(zooKeeper, DIR, NODE, ZooDefs.Ids.OPEN_ACL_UNSAFE);

                lock1.lock();
                System.out.println("thread1 locked");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("thread1 InterruptedException");
                }

                lock1.unlock();
                System.out.println("thread1 unlocked");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                ZooKeeper zooKeeper = null;
                try {
                    zooKeeper = new ZooKeeper("localhost", 2181, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DistributedLock lock2 = new DistributedLock(zooKeeper, DIR, NODE, ZooDefs.Ids.OPEN_ACL_UNSAFE);

                lock2.lock();
                System.out.println("thread2 locked");


                lock2.lock();
                System.out.println("thread2 locked 2");

                lock2.unlock();
                System.out.println("thread2 unlocked 1");

                if (lock2.tryLock()) {
                    System.out.println("thread2 trylock succ");
                } else {
                    System.out.println("thread2 trylock fail");
                }

                try {
                    lock2.lockInterruptibly();
                    System.out.println("thread2 lockInterruptibly succ");
                } catch (InterruptedException e) {
                    System.out.println("thread2 lockInterruptibly fail");
                }

                lock2.unlock();
                System.out.println("thread2 unlocked 2");

                lock2.unlock();
                System.out.println("thread2 unlocked 3");

                lock2.lock();
            }
        });

        thread1.run();
        thread2.run();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("process");
    }
}
