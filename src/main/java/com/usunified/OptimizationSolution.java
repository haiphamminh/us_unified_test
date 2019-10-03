package com.usunified;

import java.util.Random;

public class OptimizationSolution {
    public static void main(String[] args) {
       /* int a = -1000000000, b = 1000000000;

        long startT = System.currentTimeMillis();
        boolean flag = ((a^b) != 0);
        long endT = System.currentTimeMillis();
        System.out.println(String.format("flag: %s; time consuming: %sms", flag, (endT - startT)));

        startT = System.currentTimeMillis();
        flag = (a != b);
        endT = System.currentTimeMillis();
        System.out.println(String.format("flag: %s; time consuming: %sms", flag, (endT - startT)));*/

//        System.out.println(a ^ b);

//        int[] A = {4, 6, 2, 2, 6, 6, 4};
//        int[] A = {4, 6, 4, 4, 4, 4, 4};
        int[] A = {4, 5, 4, 4, 3, 4, 4};
//        int[] A = {4, 4, 4, 4, 4, 4, 5};
        System.out.println(solution(A));
        System.out.println(optimizeSln1(A));


        int N = 1000000;
        Random r = new Random();
        int[] B = new int[N + 1];
        for (int i = N; i >= 0; i--) {
            B[i] = 0;//r.nextInt(100000000);
        }
        B[500000] = 1;
        long start = System.currentTimeMillis();
        int result = 0;//solution(B);
        long end = System.currentTimeMillis();
        System.out.println(String.format("result: %s; time consuming: %sms", result, (end - start)));

        start = System.currentTimeMillis();
        result = optimizeSln1(B);
        end = System.currentTimeMillis();
        System.out.println(String.format("[optimized] result: %s; time consuming: %sms", result, (end - start)));
    }

    static int solution(int[] A) {
        int N = A.length;
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                if (A[i] != A[j]) {
                    result = Math.max(result, j - i);
                }
            }
        }
        return result;
    }

    static int optimizeSln(int[] A) {
        int N = A.length;
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = result + i; j < N; j++) {
                if (A[i] != A[j]) {
                    result = Math.max(result, j - i);
                }
            }
        }
        return result;
    }

    static int optimizeSln1(int[] A) {
        int N = A.length;
        int result = 0;

        int firstResult = A[0];
        for (int i = 0; i < N; i++) {
            if (firstResult != A[i]) {
                result = Math.max(result, i);
            }
        }

        int lastValue = A[N - 1];
        for (int i = 0; i < N; i++) {
            if (lastValue != A[i]) {
                result = Math.max(result, N - 1 - i);
            }
        }
        return result;
    }
}
