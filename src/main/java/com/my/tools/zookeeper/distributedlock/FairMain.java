package com.my.tools.zookeeper.distributedlock;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author wq
 * @date 2019/9/22
 */
public class FairMain {
    private static final String DIR = "/distributedfairlock";
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
                DistributedFairLock lock1 = new DistributedFairLock(zooKeeper, DIR, NODE, ZooDefs.Ids.OPEN_ACL_UNSAFE);
                System.out.println("thread1 begin");
                lock1.lock();
                System.out.println("thread1 locked");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("thread1 InterruptedException");
                }

                lock1.unlock();
                System.out.println(lock1.tryLock());
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
                DistributedFairLock lock2 = new DistributedFairLock(zooKeeper, DIR, NODE, ZooDefs.Ids.OPEN_ACL_UNSAFE);

                System.out.println("thread2 begin");
                lock2.lock();
                System.out.println("thread2 locked");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("thread2 InterruptedException");
                }
                System.out.println("thread2 locked");
                lock2.unlock();

                System.out.println("thread2 unlocked");
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                ZooKeeper zooKeeper = null;
                try {
                    zooKeeper = new ZooKeeper("localhost", 2181, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DistributedFairLock lock2 = new DistributedFairLock(zooKeeper, DIR, NODE, ZooDefs.Ids.OPEN_ACL_UNSAFE);

                System.out.println("thread3 begin");
                lock2.lock();
                System.out.println("thread3 locked");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("thread3 InterruptedException" + e);
                }
                lock2.unlock();

                System.out.println("thread3 unlocked");
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
