package com.my.tools.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wq
 * @date 2019/9/28
 */
public class ListToBST {
    public static void main(String[] args) {
    }
    public  TreeNode sortedListToBST(ListNode head) {
        return listToBst(transToList(head));
    }

    public  List<Integer> transToList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return list;
    }

    public  TreeNode listToBst(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int mid = list.size()/2;

        TreeNode node = new TreeNode(list.get(mid));

        node.left = listToBst(list.subList(0, mid));
        node.right = listToBst(list.subList(mid, list.size()));
        return node;
    }

     class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

     class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
}
