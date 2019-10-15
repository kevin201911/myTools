package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wq
 * @date 2019/10/3
 */
public class N200 {
    public int numIslands(char[][] grid) {
        List<String> list = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    isIsland(grid, i, j);
                }

            }
        }

        return count;
    }

    public void isIsland(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] != '1') {
            return;
        }

        grid[i][j] = '2';
        isIsland(grid, i - 1, j);
        isIsland(grid, i + 1, j);
        isIsland(grid, i, j - 1);
        isIsland(grid, i, j + 1);
    }

}
