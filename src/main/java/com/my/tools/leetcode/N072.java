package com.my.tools.leetcode;

/**
 * @author wq
 * @date 2019/10/3
 */
public class N072 {
    public int minDistance(String word1, String word2) {
        int[][] length = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            length[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {
            length[0][j] = j;
        }

        int flag = 0;

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    flag = 0;
                } else {
                    flag = 1;
                }

                length[i][j] = Math.min(Math.min(length[i - 1][j] + 1, length[i][j - 1] + 1), length[i - 1][j - 1] + flag);
            }
        }

        return length[word1.length()][word2.length()];
    }
}
