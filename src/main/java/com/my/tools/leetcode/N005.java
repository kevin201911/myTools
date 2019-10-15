package com.my.tools.leetcode;

/**
 * @author wq
 * @date 2019/9/28
 */
public class N005 {
    public String longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        String str = "";
        for (int i = 0; i < chars.length; i++) {
            int j = 0;
            while ((i - j) >= 0 && (i + j) < chars.length && chars[i-j] == chars[i+j]) {
                if (2 * j + 1 > str.length()) {
                    str = s.substring(i - j, i + j + 1);
                }
                j++;
            }

            j = 0;
            while ((i - j) >= 0 && (i + j + 1) < chars.length && chars[i-j] == chars[i+j+1]) {
                if (2 * j + 2 > str.length()) {
                    str = s.substring(i - j, i + j + 2);
                }
                j++;
            }
        }
        return str;
    }

}
