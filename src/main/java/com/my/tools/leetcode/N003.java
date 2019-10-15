package com.my.tools.leetcode;

import com.alibaba.common.lang.StringUtil;

/**
 * @author wq
 * @date 2019/9/28
 */
public class N003 {
    public int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            String substr = "";
            for (int j = i; j < chars.length; j++) {
                if (substr.contains(String.valueOf(chars[j]))) {
                    break;
                }
                substr = s.substring(i, j + 1);
            }
            if (substr.length() > max) {
                max = substr.length();
            }
        }
        return max;
    }
}
