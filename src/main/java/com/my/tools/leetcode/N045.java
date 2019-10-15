package com.my.tools.leetcode;

/**
 * @author wq
 * @date 2019/10/3
 */
public class N045 {
    public int jump(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        int current_max_pos = nums[0];
        int next_max_pos = nums[0];

        int step = 1;
        for (int i = 1; i < nums.length; i++) {
            if (next_max_pos < i + nums[i]) {
                next_max_pos = i + nums[i];
            }
            if (i == current_max_pos && i != nums.length - 1) {
                current_max_pos = next_max_pos;
                step++;
            }
        }

        return step;
    }
}
