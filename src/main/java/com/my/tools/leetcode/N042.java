package com.my.tools.leetcode;

import java.util.Stack;

/**
 * @author wq
 * @date 2019/10/5
 */
public class N042 {
    public int trap(int[] height) {
        int max = 0;
        int min = 0;

        int count = 0;
        for (int i = 1; i < height.length; i++) {
            if (height[i] >= height[max]) {
                max = i;
            }
        }

        int left = 0;
        for (int i = 1; i <= max; i++) {
            if (height[i] >= height[left]) {

                for (int j = left; j < i; j++) {
                    count += height[left] - height[j];
                }

                left = i;
            }
        }

        int right = height.length - 1;
        for (int i = height.length - 2; i >= max; i--) {

            if (height[i] >= height[right]) {

                for (int j = right; j > i; j--) {
                    count += height[right] - height[j];
                }

                right = i;
            }
        }

        return count;
    }
}
