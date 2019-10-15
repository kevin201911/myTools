package com.my.tools.leetcode;

import java.util.Stack;

/**
 * @author wq
 * @date 2019/10/1
 */
public class N032 {
    public int longestValidParentheses(String s) {
        char[] chars = s.toCharArray();
        int max = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    max = Math.max(max, i - stack.peek());
                }
            }
        }

        return max;
    }
}
