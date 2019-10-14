package com.usunified;

import java.util.ArrayList;
import java.util.List;

public class ZigZag {
    public static void main(String[] args) {
//        String s = "PAYPALISHIRING";
        String s = "AB";
        System.out.println(convert(s, 1));
    }

    static String convert(String s, int numRows) {
        if (s == null || s.isEmpty()) return "";
        char[] chars = s.toCharArray();
        int row = 0;
        boolean increase = true;
        List<List<Character>> l = new ArrayList<>();
        for (char c : chars) {
            if (l.size() <= row) {
                l.add(row, new ArrayList<>());
            }
            List<Character> innerList = l.get(row);
            innerList.add(c);
            if (numRows == 1) {
                continue;
            }
            if (increase) {
                if (row == numRows - 1) {
                    row--;
                    increase = false;
                } else {
                    row++;
                }
            } else {
                if (row == 0) {
                    row++;
                    increase = true;
                } else {
                    row--;
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        for (List<Character> charList : l) {
            for (Character c : charList) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * P         H
     * A      S  I
     * Y   I     R
     * P L       I G
     * A         N
     * 0 8 1 7 9 2 6 10 3 5 11 13 4 12
     * length = 14, numRows = 3
     * P A Y P A L I S H I R  I  N  G
     * 0 1 2 3 4 5 6 7 8 9 10 11 12 13
     * result:
     * P   A   H   N row*numRows + idx (row = 0, numRows = 3, idx = 0) idx++;
     * A P L S I I G
     * Y   I   R
     * P A H N  A P L S I I  G  Y I R
     * 0 4 8 12 1 3 5 7 9 11 13 2 6 10
     * 0 1 2 3  4 5 6 7 8 9  10 11 12 13
     * row = 0, idx = 0, numRows = 3 row*numRow + idx
     *
     * P     I     N
     * A   L S   I G
     * Y A   H R
     * P     I
     * 0 6 12 1 5 7 11 13 2 4 8 10 3 9
     * 6
     * row = 0;
     * col = 0;
     *
     * row++;
     */
}
