package com.my.tools;

/**
 * @author wq105907
 * @date 2018/11/5
 */
public class StringAppendCompare {
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            stringBuffer.append("sadada");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        StringBuilder stringBuilder = new StringBuilder();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            stringBuilder.append("sadada");
        }
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);

        String string = new String();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            string += "sadada";
        }
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }

}
