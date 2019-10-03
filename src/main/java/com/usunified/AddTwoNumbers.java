package com.usunified;

import java.util.Stack;

public class AddTwoNumbers {

    public static void main(String[] args) {
        ListNode l1 = ListNode.of(5);//7, 2, 4, 3);
        ListNode l2 = ListNode.of(5);//, 6, 4);
        System.out.println(addTwoNumbers(l1, l2));
        System.out.println(addTwoNumbersII(l1, l2));
    }

    static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode p = l1, q = l2, curr = result;
        int carry = 0;
        while (p != null || q != null) {
            int sum = carry;
            if (p != null) {
                sum += p.val;
                p = p.next;
            }

            if (q != null) {
                sum += q.val;
                q = q.next;
            }
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return result.next;
    }

    static ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = fromListNode(l1);
        Stack<Integer> s2 = fromListNode(l2);
        Stack<Integer> s3 = new Stack<>();
        ListNode result = new ListNode(0);
        ListNode curr = result;
        int carry = 0;
        while (!s1.empty() || !s2.empty()) {
            int sum = carry;
            if (!s1.empty()) {
                sum += s1.pop();
            }
            if (!s2.empty()) {
                sum += s2.pop();
            }

            carry = sum / 10;
            s3.push(sum % 10);
        }
        if (carry > 0) {
            s3.push(carry);
        }
        while (!s3.empty()) {
            curr.next = new ListNode(s3.pop());
            curr = curr.next;
        }
        return result.next;
    }

    static Stack<Integer> fromListNode(ListNode l) {
        Stack<Integer> s = new Stack<>();
        ListNode p = l;
        while (p != null) {
            s.push(p.val);
            p = p.next;
        }
        return s;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public static ListNode of(int... numbers) {
            ListNode l = new ListNode(numbers[0]);
            ListNode curr = l;
            for (int i = 1; i < numbers.length; i++) {
                curr.next = new ListNode(numbers[i]);
                curr = curr.next;
            }
            return l;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            ListNode curr = this;
            while (curr != null) {
                builder.append(curr.val + " ");
                curr = curr.next;
            }
            return builder.toString();
        }
    }
}
