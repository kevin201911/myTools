package com.my.tools.oj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author wq
 * @date 2019/9/25
 */
public class HW006 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        getResult(scanner.nextLong());
    }

    public static void getResult(long ulDataInput) {
        for (long i = 2; i <= ulDataInput; i++) {
            while (ulDataInput%i == 0) {
                System.out.print(i + " ");
                ulDataInput = ulDataInput/i;
            }
        }
    }

}
