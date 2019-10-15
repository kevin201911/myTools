package com.my.tools.leetcode;

/**
 * @author wq
 * @date 2019/10/5
 */
public class N037 {
    public void solveSudoku(char[][] board) {
        dfs(board, 0, 0);
        print(board);
    }

    public boolean dfs(char[][] board, int i, int j) {
        while (board[i][j] != '.') {
            if (++j >= 9) {
                j = 0;
                i++;
            }
            if (i >=9) {
                return true;
            }
        }
        for (int k = 1; k <= 9; k++) {
            char num = Character.forDigit(k, 10);
            if (isOk(board, i, j, num)) {
                board[i][j] = num;
                if (dfs(board, i, j)) {
                    return true;
                } else {
                    board[i][j] = '.';
                }
            }
        }
        return false;
    }

    public boolean isOk(char[][] board, int i, int j, char num) {
        for (int m = 0; m < 9; m++) {
            if (board[i][m] == num) {
                return false;
            }

            if (board[m][j] == num) {
                return false;
            }
        }

        int x = i / 3 * 3;
        int y = j / 3 * 3;
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                if (board[m + x][n + y] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public void print(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
