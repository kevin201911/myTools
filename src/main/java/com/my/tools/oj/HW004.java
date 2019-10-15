package com.my.tools.oj;

import java.util.Scanner;

/**
 * @author wq
 * @date 2019/9/25
 */
public class HW004 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.isEmpty()) {
                break;
            }
            for (int i = 0; i < str.length(); i++) {
                if (i % 8 == 0) {
                    String temp = str.substring(i, (i + 8) < str.length() ? i + 8 : str.length());
                    System.out.println(String.format("%-8s", temp).replace(' ', '0'));
                }
            }
        }
    }
}
