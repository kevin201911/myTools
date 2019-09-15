package com.my.tools.juc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wq
 * @date 2019/7/29
 */
public class ConCurrentHashMapMain {
    public static void main(String[] args) {
        Map<Integer, String> map = new ConcurrentHashMap<>(1);
        for (int i = 0; i < 10000000; i++) {
            map.put(i, "111");
        }

    }
}
