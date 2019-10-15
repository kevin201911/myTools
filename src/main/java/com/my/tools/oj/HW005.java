package com.my.tools.oj;

import java.util.Scanner;

/**
 * @author wq
 * @date 2019/9/25
 */
public class HW005 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            if (string.isEmpty()) {
                break;
            }

            System.out.println(Integer.parseInt(string.substring(2), 16));
        }
    }
}
