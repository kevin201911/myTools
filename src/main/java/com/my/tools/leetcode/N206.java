package com.my.tools.leetcode;

/**
 * @author wq
 * @date 2019/10/13
 */
public class N206 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        return reverse(null, head);
    }

    public ListNode reverse(ListNode newNode, ListNode oldNode) {
        if (oldNode != null) {
            ListNode node = new ListNode(oldNode.val);
            node.next = newNode;
            return reverse(node, oldNode.next);
        }

        return newNode;
    }
}
