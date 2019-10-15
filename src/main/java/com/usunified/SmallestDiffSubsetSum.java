package com.usunified;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SmallestDiffSubsetSum {
    public static void main(String[] args) {
//        int[] arr = {1, 2, 3, 4, 5};  // {5 8 25}=38 {23 15}=38 diff = 0
//        int[] arr = {2, 4, 5, 6, 13};  // {5 8 25}=38 {23 15}=38 diff = 0
//        int[] arr = {2, 4, 5, 6, 12};  // {5 8 25}=38 {23 15}=38 diff = 0
//        int[] arr = {2, 3, 5, 6, 8};  // {5 8 25}=38 {23 15}=38 diff = 0
//        int[] arr = {1, 3, 5, 6, 8};  // {5 8 25}=38 {23 15}=38 diff = 0
//        int[] arr = {5, 8, 15, 23, 25};  // {5 8 25}=38 {23 15}=38 diff = 0
//        int[] arr = {50, 17, 9, 14, 12, 23, 19, 76, 54, 72, 67};  // {5 8 25}=38 {23 15}=38 diff = 0
        int n = 1000000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i * 2 + 1;
        }
        Map<String, Integer> lookup = new HashMap<>();
        System.out.println(minPartition(arr, arr.length - 1, 0, 0, lookup));
//        System.out.println(findMin(arr, arr.length));
        System.out.println(findBalancePartition(arr));
        printSubsets(findSmallestDiffOfSubsets(arr));

        int[] arr4 = {5, 15, 25, 35, 45};
//        int[] arr4 = {5, 10, 15, 20, 25};
        System.out.println(findMin(arr4, arr4.length));
        System.out.println(findBalancePartition(arr4));
        List<List<Integer>> subsets4 = findSmallestDiffOfSubsets(arr4);
        printSubsets(subsets4);

        int[] arr3 = {5, 8, 15, 23, 25, 35, 52};
        // {5 8 15 23}=51 {25 35 52}=163 diff=112
        // {5 8 23 52}=88 {15 25 35}=75 diff=13
        // {5 8 15 52}=80 {23 25 35}=83 diff=3
        // [35, 25, 15, 5]=80 [52, 23, 8]=83 diff=3
        // {5 8 25 52}=90 {15 23 35}=73 diff=17
        // {5 15 23 52}=95 {8 25 35}=68 diff=27
        // {5 8 35 52}=100 {15 25 35}=75 diff=25
        // {5 15 52}=72 {8 23 25 35}=91 diff=19
        // {5 25 52}=82 {8 15 23 35}=81 diff=1
        System.out.println(findMin(arr3, arr3.length));
        System.out.println(findBalancePartition(arr3));
        List<List<Integer>> subsets3 = findSmallestDiffOfSubsets(arr3);
        printSubsets(subsets3);

        int[] arr1 = {3, 6, 10, 19, 30, 45};
        // {3 6 45} {19 10 30}  diff = 4 avg = 19
        // {3 10 30} {6 19 45} diff = 27 avg = 19
        // {3 10 45}=58 {6 19 30}=55 diff = 3 avg = 19
        // {3 19 45} {6 10 30} diff = 21 avg = 19
        // {3 19 30} {6 10 45} diff = 9 avg = 19
        // {10 6 30}=46 {19 3 45}=67 diff=21
        System.out.println(findMin(arr1, arr1.length));
        System.out.println(findBalancePartition(arr1));
        List<List<Integer>> subsets1 = findSmallestDiffOfSubsets(arr1);
        printSubsets(subsets1);
//        printSubsets(findSmallestDiffOfSubsetsII(arr1));

        int[] arr2 = {4, 9, 10, 19, 30, 45};
        // {4 10 45}=59 {9 19 30}=58 diff = 1 avg = 23.4
        // {4 9 45}=58 {10 19 30}=59 diff = 1 avg = 23.4
        // {4 19 30}=53 {9 10 45}=64 diff = 11 avg = 23.4
        // {4 9 10}=23 {19 30 45}=94 diff = 71 avg = 23.4
        System.out.println(findMin(arr2, arr2.length));
        System.out.println(findBalancePartition(arr2));
        List<List<Integer>> subsets2 = findSmallestDiffOfSubsets(arr2);
        printSubsets(subsets2);

        int[] arr5 = {9, 10, 14, 20, 25}; // [14,25] [9,10,20] diff = 0
        System.out.println(findMin(arr5, arr5.length));
        System.out.println(findBalancePartition(arr5));
        List<List<Integer>> subsets5 = findSmallestDiffOfSubsets(arr5);
        printSubsets(subsets5);

        int[] arr6 = {9, 10, 16, 20, 25};
        System.out.println(findMin(arr6, arr6.length));
        System.out.println(findBalancePartition(arr6));
        List<List<Integer>> subsets6 = findSmallestDiffOfSubsets(arr6);
        printSubsets(subsets6);

        int[] arr7 = {12, 15, 18, 20, 25};
        System.out.println(findMin(arr7, arr7.length));
        System.out.println(findBalancePartition(arr7));
        List<List<Integer>> subsets7 = findSmallestDiffOfSubsets(arr7);
        printSubsets(subsets7);
    }

    // Partition the set S into two subsets S1, S2 such that the
    // difference between the sum of elements in S1 and the sum
    // of elements in S2 is minimized
    static int minPartition(int[] S, int n, int S1, int S2, Map<String, Integer> lookup) {
        // base case: if list becomes empty, return the absolute
        // difference between two sets
        if (n < 0) {
            return Math.abs(S1 - S2);
        }

        // construct a unique map key from dynamic elements of the input
        // Note that can uniquely identify the subproblem with n & S1 only,
        // as S2 is nothing but S - S1 where S is sum of all elements
        String key = n + "|" + S1;

        // if sub-problem is seen for the first time, solve it and
        // store its result in a map
        if (!lookup.containsKey(key)) {
            // Case 1. include current item in the subset S1 and recur
            // for remaining items (n - 1)
            int inc = minPartition(S, n - 1, S1 + S[n], S2, lookup);

            // Case 2. exclude current item from subset S1 and recur for
            // remaining items (n - 1)
            int exc = minPartition(S, n - 1, S1, S2 + S[n], lookup);

            lookup.put(key, Integer.min(inc, exc));
        }
        return lookup.get(key);
    }

    static List<List<Integer>> findSmallestDiffOfSubsets(int[] arr) {
        Arrays.sort(arr);

        List<Integer> s1 = new ArrayList<>();
        int n = arr.length;
        int sum = IntStream.of(arr)
                           .sum();
        int half = sum / 2;
        boolean[][] T = new boolean[n + 1][half + 1];

        for (int i = 0; i <= n; i++) {
            T[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= half; j++) {
                T[i][j] = T[i - 1][j];
                if (arr[i - 1] <= j) {
                    T[i][j] |= T[i - 1][j - arr[i - 1]];
                }
            }
        }

        int i = n, j = half;
        while (!T[i][j]) j--;

        while (T[i][j] && i >= 1 && j >= 1) {
            if (!T[i - 1][j]) {
                s1.add(arr[i - 1]);
                j -= arr[i - 1];
            }
            i--;
        }

        List<Integer> s2 = IntStream.of(arr)
                                    .boxed()
                                    .filter(e -> !s1.contains(e))
                                    .collect(Collectors.toList());

        int sum1 = s1.stream()
                     .reduce(0, Integer::sum);
        int sum2 = s2.stream()
                     .reduce(0, Integer::sum);
        System.out.println("sum= " + sum + "; half= " + half);
        System.out.println("sum1= " + sum1 + "; sum2= " + sum2 + "; diff= " + Math.abs(sum2 - sum1));
        return Arrays.asList(s1, s2);
    }

    static void printSubsets(List<List<Integer>> subsets) {
        subsets.forEach(System.out::println);
        System.out.println("------------------");
    }

    // Function to find the minimum sum
    public static int findMinRec(int arr[], int i, int sumCalculated, int sumTotal) {
        // If we have reached last element.
        // Sum of one subset is sumCalculated,
        // sum of other subset is sumTotal-
        // sumCalculated.  Return absolute
        // difference of two sums.
        if (i == 0) {
            int min = Math.abs((sumTotal - sumCalculated) - sumCalculated);
//            System.out.println("min = " + min);
            return min;
        }


        // For every item arr[i], we have two choices
        // (1) We do not include it first set
        // (2) We include it in first set
        // We return minimum of two choices
//        System.out.println("i = " + i);
        int minLeft = findMinRec(arr, i - 1, sumCalculated + arr[i - 1], sumTotal);
        int minRight = findMinRec(arr, i - 1, sumCalculated, sumTotal);
//        System.out.println("minLeft = " + minLeft + ", minRight = " + minRight);
        return Math.min(minLeft, minRight);
    }

    // Returns minimum possible difference between
// sums of two subsets
    public static int findMin(int arr[], int n) {
        // Compute total sum of elements
        int sumTotal = 0;
        for (int i = 0; i < n; i++)
            sumTotal += arr[i];

        // Compute result using recursive function
        return findMinRec(arr, n, 0, sumTotal);
    }

    public static int findBalancePartition(int[] a) {

        // Calculate sum of all the elements in set
        int S = 0;
        for (int i = 0; i < a.length; i++)
            S += a[i];

        boolean T[][] = new boolean[a.length + 1][S + 1];

        /* Initialize first column as true.
            0 sum is possible with all elements.
        */
        for (int i = 0; i <= a.length; i++)
            T[i][0] = true;

        /*  Initialize top row, except dp[0][0],
            as false. With 0 elements, no other
            sum except 0 is possible
        */
        for (int i = 1; i <= S; i++)
            T[0][i] = false;


        for (int i = 1; i <= a.length; i++) {
            for (int j = 1; j <= S; j++) {
                // If ith element is excluded
                T[i][j] = T[i - 1][j];

                // If ith element is included
                if (a[i - 1] <= j) T[i][j] |= T[i - 1][j - a[i - 1]];
            }
        }

        // Initialize difference of two sums.
        int diff = Integer.MAX_VALUE;

        for (int j = S / 2; j >= 0; j--) {
            // Find the
            if (T[a.length][j] == true) {
                diff = S - 2 * j;
                break;
            }
        }
        return diff;
    }
}
