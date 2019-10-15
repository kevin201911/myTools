package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wq
 * @date 2019/10/5
 */
public class N093 {
    public List<String> restoreIpAddresses(String s) {
        List<String> ips = new ArrayList<>(4);

        String a,b,c,d;
        for (int i = 1; i <= 3; i++) {
            if (s.length() < i) {
                continue;
            }
            a = s.substring(0, i);
            if (Integer.parseInt(a) > 255 || (a.charAt(0) == '0' && !a.equalsIgnoreCase("0"))) {
                continue;
            }
            for (int j = 1; j <= 3; j++) {
                if (s.length() < i + j) {
                    continue;
                }
                b = s.substring(i, i + j);
                if (Integer.parseInt(b) > 255 || (b.charAt(0) == '0' && !b.equalsIgnoreCase("0"))) {
                    continue;
                }
                for (int m = 1; m <= 3; m++) {
                    if (s.length() < i + j + m) {
                        continue;
                    }
                    c = s.substring(i + j, i + j + m);
                    if (Integer.parseInt(c) > 255 || (c.charAt(0) == '0' && !c.equalsIgnoreCase("0"))) {
                        continue;
                    }
                    for (int n = 1; n <= 3; n++) {
                        if (s.length() != i + j + m + n) {
                            continue;
                        }
                        d = s.substring(i + j + m, i + j + m + n);
                        if (Integer.parseInt(d) > 255 || (d.charAt(0) == '0' && !d.equalsIgnoreCase("0"))) {
                            continue;
                        }
                        ips.add(a + "." + b + "." + c + "." + d);
                    }
                }
            }
        }
        return ips;
    }
}
