package com.usunified;

public class LongestSubstring {
    public static void main(String[] args) {
        String s = "abcaxcbb";
//        String s = "bbbbb";
//        String s = "au";
//        String s = "dvdf";

        System.out.println(lengthOfLongestSubstring(s));
        System.out.println(lengthOfLongestSubstringII(s));
    }

    static int lengthOfLongestSubstring(String s) {
        int len_s = s.length();
        if (len_s <= 1) return len_s;

        int[] index_s = new int[len_s];
        int index = 0;
        for (int i = 0; i < len_s; ++i) {
            index = s.indexOf(s.charAt(i), i + 1);
            index_s[i] = index >= 0 ? index : len_s;
        }

        int length = 1, min = len_s;
        for (int i = len_s - 2; i >= 0; --i) {
            if (index_s[i] < min) {
                min = index_s[i];
            }
            if (min - i > length) {
                length = min - i;
            }
        }
        return length;
    }

    static int lengthOfLongestSubstringII(String s) {
        int max = 0, left = 0;
        int length = s.length();
        int[] indices = new int[256];
        for (int right = 0; right < length; right++) {
            char c = s.charAt(right);
            int index = indices[c];
            left = Math.max(index, left);
            indices[c] = right + 1;
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}
