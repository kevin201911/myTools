package com.my.tools.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author wq
 * @date 2019/10/3
 */
public class N221 {
    public int maximalSquare(char[][] matrix) {
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] heights = new int[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++) {
                int k = i;
                while (k < matrix.length && matrix[k][j] == '1') {
                    heights[j]++;
                    k++;
                }
                max = Math.max(max, largestRectangleArea(heights));
            }
        }

        return max;
    }

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
                int width = Math.min(heights[top], (stack.isEmpty() ? i : (i - stack.peek() - 1)));
                area = Math.max(area, width * width);
            }
        }

        return area;
    }
}
