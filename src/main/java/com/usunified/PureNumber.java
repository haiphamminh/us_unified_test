package com.usunified;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class PureNumber {
    public static void main(String[] args) {
        System.out.println(calculate(1));
        LongStream.of(1, 2, 3, 4, 10, 1_000_000_000_000_000_000L)
                  .forEach(n -> System.out.println(calculate(n - 1)));
    }

    static String solution1(int N) {
        Queue<String> q = new LinkedList();
        q.add("44");
        q.add("55");
        Queue<String> ans = new LinkedList<>();
        while (ans.size() < N) {
            String temp = q.poll();
            ans.add(temp);
            q.add("4" + temp + "4");
            q.add("5" + temp + "5");
        }
        return ans.stream()
                  .sorted((o1, o2) -> {
                      if (o1.length() == o2.length()) {
                          return o1.compareTo(o2);
                      } else {
                          return o1.length() < o2.length() ? -1 : 1;
                      }
                  })
                  .collect(Collectors.joining(","));
    }

    static String calculate(long n) {
        long begin = 0;
        long exp = 2;
        long k = 1;
        while (begin + exp <= n) {
            k++;
            begin += exp;
            exp *= 2;
        }
        String result;
        long no;
        if (n < begin + exp / 2) {
            result = "0";
            no = n - begin;
        } else {
            result = "1";
            no = n - (begin + exp / 2);
        }
        String binary = Long.toBinaryString(no);
        String fill = fillZero(binary, k - 1);
        result += fill;

        result = result.replace("0", "4")
                       .replace("1", "5");
        result += new StringBuilder(result).reverse()
                                           .toString();
        return result;
    }

    static String fillZero(String binary, long length) {
        while (binary.length() < length) {
            binary = "0" + binary;
        }
        return binary;
    }
}
