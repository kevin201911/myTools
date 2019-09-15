package com.my.tools.thread;

/**
 * @author wq105907
 * @date 2019/7/14
 */
public class Synchronized {
    private static int count;

    public static void main(String[] args) {
        synchronized (Synchronized.class) {
            count++;
        }
    }
}
