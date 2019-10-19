package com.my.tools;

import java.util.*;

public class Yuebing{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int people = scanner.nextInt();
            int count = scanner.nextInt();
            int sum = count - people;
            System.out.println(foo(people, count - people));
        }

    }

    public static int foo(int n, int m) {
        if (n == 0 && m != 0) {
            return 0;
        } else if (m == 0) {
            return 1;
        } else if (m >= n) {
            return foo(n, m - n) + foo(n - 1, m);
        } else {
            return foo(m, m);
        }
    }
}