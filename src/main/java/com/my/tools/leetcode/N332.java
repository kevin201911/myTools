package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wq
 * @date 2019/10/6
 */
public class N332 {
    public int coinChange(int[] coins, int amount) {
        List<Integer> list = new ArrayList<>(coins.length);
        for (int coin : coins) {
            list.add(coin);
        }
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        int i = 0;
        int count = 0;
        int sum = 0;

        while (i < coins.length) {
            while (sum < amount) {

            }
        }

        return 0;
    }
}
