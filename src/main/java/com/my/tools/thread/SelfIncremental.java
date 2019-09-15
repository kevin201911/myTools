package com.my.tools.thread;

/**
 * @author wq105907
 * @date 2019/7/13
 */
public class SelfIncremental {
    private static int count;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i< 10000; i++) {
                count++;
                System.out.println("111" + count);

            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i< 10000; i++) {
                count++;
                System.out.println("222" + count);

            }
        });

        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        System.out.println(count);
    }
}
