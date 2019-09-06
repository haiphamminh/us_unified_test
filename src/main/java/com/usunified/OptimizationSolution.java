package com.usunified;

import java.util.Random;

public class OptimizationSolution {
    public static void main(String[] args) {
        int[] A = {4, 6, 2, 2, 6, 6, 4};
        System.out.println(solution(A));
        System.out.println(optimizeSln(A));

        int N = 100000;
        Random r = new Random();
        int[] B = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            B[i] = r.nextInt(N);
        }
        long start = System.currentTimeMillis();
        int result = solution(B);
        long end = System.currentTimeMillis();
        System.out.println(String.format("result: %s; time consuming: %ss", result, (end - start) / 1000));

        start = System.currentTimeMillis();
        result = optimizeSln(B);
        end = System.currentTimeMillis();
        System.out.println(String.format("[optimized] result: %s; time consuming: %ss", result, (end - start) / 1000));
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
        int val = A[0];
        for (int i = 1; i < N; i++) {
            if (val != A[i]) {
                result = Math.max(result, i);
            }
        }
        return result;
    }
}
