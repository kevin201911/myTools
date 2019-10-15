package com.my.tools.leetcode;

import java.util.Stack;

/**
 * @author wq
 * @date 2019/10/2
 */
public class N394 {
    public String decodeString(String s) {
        if (!s.contains("[") && !s.contains("]")) {
            return s;
        }
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer num = new StringBuffer();

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '[') {
                stack.push(i);
            } else if (s.charAt(i) == ']') {
                if (stack.size() > 1) {
                    stack.pop();
                    continue;
                }
                int count = Integer.parseInt(num.toString());
                num.delete(0, num.length());
                int index = stack.pop();
                for (int j = 0; j < count; j++) {
                    stringBuffer.append(decodeString(s.substring(index + 1, i)));
                }
            } else if ("0123456789".contains(String.valueOf(s.charAt(i)))  && stack.isEmpty()) {
                num.append(s.charAt(i));
            } else if (stack.isEmpty()) {
                stringBuffer.append(s.charAt(i));
            }
        }
        return stringBuffer.toString();
    }
}
