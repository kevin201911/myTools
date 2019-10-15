package com.my.tools.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author wq
 * @date 2019/10/1
 */
public class N084 {
    public int largestRectangleArea(int[] heights) {
        heights = Arrays.copyOf(heights, heights.length + 1);
        heights[heights.length - 1] = 0;
        Stack<Integer> stack = new Stack<>();
        int area = 0;
        for (int i = 0; i < heights.length;) {
            if (stack.isEmpty() || heights[i] >= heights[stack.peek()]) {
                stack.push(i);
                i++;
            } else {
                int top = stack.pop();
                area = Math.max(area, heights[top] * (stack.isEmpty() ? i : (i - stack.peek() - 1)));
            }
        }

        return area;
    }
}
