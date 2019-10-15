package com.my.tools.oj;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author wq
 * @date 2019/9/25
 */
public class HW008 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = Integer.parseInt(scanner.nextLine());
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < count; i++) {
            String str = scanner.nextLine();
            String[] strs = str.split(" ");
            Integer int1 = Integer.parseInt(strs[0]);
            Integer int2 = Integer.parseInt(strs[1]);
            map.putIfAbsent(int1, 0);
            map.put(int1, map.get(int1) + int2);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
