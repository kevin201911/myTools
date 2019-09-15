package com.my.tools.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wq
 * @date 2019/7/28
 */
public class CopyOnWriteListMain {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("11212");
        list.add("11213");
        list.add("11214");
        list.add("11215");
        list.add("11216");
        for (String str : list) {
            System.out.println(str);
            list.remove(str);
        }
        System.out.println(list.size());
    }
}
