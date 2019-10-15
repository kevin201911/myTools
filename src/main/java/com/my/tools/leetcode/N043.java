package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wq
 * @date 2019/9/29
 */
public class N043 {
    public String multiply(String num1, String num2) {
        char[] chars1 = num1.toCharArray();
        char[] chars2 = num2.toCharArray();

        List<String> strList = new ArrayList<>();

        for (int i = 0; i < chars1.length; i++) {
            int flag = 0;
            StringBuffer stringBuffer = new StringBuffer();
            int intNum1 = Integer.parseInt(String.valueOf(chars1[chars1.length - 1 - i]));
            for (int j = 0; j < chars2.length; j++) {
                int intNum2 = Integer.parseInt(String.valueOf(chars2[chars2.length - 1 - j]));

                int total = intNum1 * intNum2 + flag;
                flag = total / 10;
                stringBuffer.insert(0, total % 10);
            }

            if (flag > 0) {
                stringBuffer.insert(0, flag);
            }

            for (int k = 0; k < i; k++) {
                if (stringBuffer.toString().equalsIgnoreCase("0")) {
                    continue;
                }
                stringBuffer.append('0');
            }

            strList.add(stringBuffer.toString());
        }


        String string = "0";
        for (String str : strList) {
            string = add(string, str);
        }

        int pos = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '0') {
                pos = i;
            } else {
                break;
            }
        }
        return (pos == string.length() - 1) && pos != 0 ? "0" : string.substring(pos);
    }

    public String add(String num1, String num2) {
        char[] chars1 = num1.toCharArray();
        char[] chars2 = num2.toCharArray();

        int flag = 0;
        int i = 0;

        StringBuffer stringBuffer = new StringBuffer();
        while (i < num1.length() || i < num2.length() || flag != 0) {
            int intNum1 = num1.length() > i ? Integer.parseInt(String.valueOf(chars1[chars1.length - 1 - i])) : 0;
            int intNum2 = num2.length() > i ? Integer.parseInt(String.valueOf(chars2[chars2.length - 1 - i])) : 0;
            int sum = intNum1 + intNum2 + flag;
            flag = sum / 10;
            stringBuffer.insert(0, sum % 10);
            i++;
        }
        return stringBuffer.toString();
    }
}
