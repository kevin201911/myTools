package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wq
 * @date 2019/10/5
 */
public class N679 {
    public boolean judgePoint24(int[] nums) {
        List<Double> numList = new ArrayList<>();
        for (int num : nums) {
            numList.add((double) num);
        }

        return calculate(numList);
    }

    public boolean calculate(List<Double> numList) {
        if (numList.size() < 1) {
            return false;
        }
        if (numList.size() == 1) {
            return Math.abs(numList.get(0) - 24) < 1e-6;
        }
        for (int i = 0; i < numList.size(); i++) {
            for (int j = 0; j < numList.size(); j++) {
                if (i == j) {
                    continue;
                }
                List<Double> list = new ArrayList<>();
                for (int k = 0; k < numList.size(); k++) {
                    if (i != k && j != k) {
                        list.add(numList.get(k));
                    }
                }

                for (int m = 0; m < 4; m++) {
                    //如果是加法和乘法，只处理一次
                    if (m < 2 && j > i) {
                        continue;
                    }

                    //除数不可以为0
                    if (m == 3 && numList.get(j) == 0) {
                        continue;
                    }
                    if (m == 0) {
                        list.add(numList.get(i) + numList.get(j));
                    }

                    if (m == 1) {
                        list.add(numList.get(i) * numList.get(j));
                    }

                    if (m == 2) {
                        list.add(numList.get(i) - numList.get(j));
                    }

                    if (m == 3) {
                        list.add(numList.get(i) / numList.get(j));
                    }

                    if (calculate(list)) {
                        return true;
                    }

                    //如果不能构成24点，删除最后加入的数，换其他操作符测试
                    list.remove(list.size() - 1);
                }
            }
        }

        return false;
    }
}
