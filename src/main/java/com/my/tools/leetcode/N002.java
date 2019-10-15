package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wq
 * @date 2019/9/28
 */
public class N002 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode tail = head;
        int flag = 0;
        while (l1 != null || l2 != null || flag != 0) {
            int val1 = l1 == null ? 0 : l1.val;
            int val2 = l2 == null ? 0 : l2.val;
            int sum = val1 + val2 + flag;
            ListNode temp = new ListNode(sum % 10);
            if (sum >= 10) {
                flag = 1;
            } else {
                flag = 0;
            }
            tail.next = temp;
            tail = tail.next;

            l1 = l1 == null ? l1 : l1.next;
            l2 = l2 == null ? l2 : l2.next;
        }

        return head.next;
    }

    class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
    }
}
