package com.my.tools.thread;


/**
 * @author wq105907
 * @date 2018/11/5
 */
public class Print {
    public static volatile String flag;
    public static volatile int count = 0;
    public static void main(String[] args) {
        flag = "a";
        Thread thread1 = new Thread(() -> {
            while (true && count < 3) {
                if (flag.equals("a")) {
                    System.out.println("a");
                    flag = "b";

                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true && count < 3) {
                if (flag.equals("b")) {
                    System.out.println("b");
                    flag = "c";

                }
            }
        });

        Thread thread3 = new Thread(() -> {
            while (true && count < 3) {
                if (flag.equals("c")) {
                    ++count;
                    System.out.println("c");
                    flag = "a";
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
