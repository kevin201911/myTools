package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wq
 * @date 2019/10/13
 */
public class N015 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int l = i + 1;
            int r = nums.length - 1;
            int sum = 0 - nums[i];

            while (l < r) {
                if (nums[l] + nums[r] == sum) {
                    List<Integer> element = Arrays.asList(nums[i], nums[l], nums[r]);
                    if (!result.contains(element)) {
                        result.add(element);
                    }
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    r--;
                    l++;

                } else if (nums[l] + nums[r] > sum) {
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    r--;

                } else {
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    l++;
                }
            }
        }
        return result;
    }
}
