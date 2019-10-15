package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wq
 * @date 2019/10/5
 */
public class N054 {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        int u = 0;
        int d = matrix.length - 1;
        int l = 0;
        int r = matrix[0].length - 1;
        while (u <= d && l <= r) {
            for (int i = l; i <= r; i++) {
                list.add(matrix[u][i]);
            }
            u++;

            for (int i = u; i <= d; i++) {
                list.add(matrix[i][r]);
            }
            r--;

            for (int i = r; i >= l && u <= d; i--) {
                list.add(matrix[d][i]);
            }
            d--;

            for (int i = d; i >= u && l <= r; i--) {
                list.add(matrix[i][l]);
            }
            l++;
        }


        return list;
    }
}
