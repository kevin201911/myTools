package com.my.tools.leetcode;

/**
 * @author wq
 * @date 2019/10/5
 */
public class N008 {
    public int myAtoi(String str) {
        str = str.trim();

        if (str.isEmpty()) {
            return 0;
        }
        String symbolStr = "+-";
        String numStr = "0123456789";

        String symbol = "";
        if (symbolStr.contains(str.substring(0, 1))) {
            symbol = str.substring(0, 1);
            str = str.substring(1);
        }


        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (!numStr.contains(str.substring(i, i + 1))) {
                break;
            }
            stringBuilder.append(str.charAt(i));
        }

        String string = stringBuilder.toString();

        if (string.isEmpty()) {
            return 0;
        }
        string = symbol + string;
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            return string.charAt(0) == '-' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}
