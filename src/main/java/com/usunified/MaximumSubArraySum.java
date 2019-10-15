package com.usunified;

import java.util.Arrays;

/**
 * https://algorithmsandme.com/contiguous-subarray-with-largest-sum/
 */
public class MaximumSubArraySum {
    public static void main(String[] args) {
        int[] arr1 = {8, -1, 3, 4}; //  15
        print(arr1, arr1.length, 0);
        System.out.println(maxSumOfSubArray(arr1));
        System.out.println(maxCircularSum(arr1));
        System.out.println();

        int[] arr2 = {-4, 5, 1, 0}; // 6
        print(arr2, arr2.length, 0);
        System.out.println(maxSumOfSubArray(arr2));
        System.out.println(maxCircularSum(arr2));
        System.out.println();

        int[] arr3 = {-4, -5, -1, 0}; // 0
        print(arr3, arr3.length, 0);
        System.out.println(maxSumOfSubArray(arr3));
        System.out.println(maxCircularSum(arr3));
        System.out.println();

        int[] arr4 = {-4, -5, 1, 10}; // 11
        print(arr4, arr4.length, 0);
        System.out.println(maxSumOfSubArray(arr4));
        System.out.println(maxCircularSum(arr4));
        System.out.println();

        int[] arr5 = {-2, -3, 4, -1, -2, 1, 5, -3};
        print(arr5, arr5.length, 0);
        System.out.println(maxSumOfSubArray(arr5));
        System.out.println(maxCircularSum(arr5));
        System.out.println();

        int[] arr6 = {-1, -4, -5, -2, -1};
        print(arr6, arr6.length, 0);
        System.out.println(maxSumOfSubArray(arr6));
        System.out.println(maxCircularSum(arr6));
        System.out.println();

        int[] arr7 = {2, 1, -5, 4, -3, 1, -3, 4, -1};
        print(arr7, arr7.length, 0);
        System.out.println(maxSumOfSubArray(arr7));
        System.out.println(maxCircularSum(arr7));
        System.out.println();

        int[] arr8 = {-8, -3, -6, -2, -5, -4};
        print(arr8, arr8.length, 0);
        System.out.println(maxSumOfSubArray(arr8));
        System.out.println(maxCircularSum(arr8));
    }

    static int maxSumOfSubArray(int[] A) {
        System.out.print("maxSumOfSubArray: ");

        // negate all elements of the array
        for (int i = 0; i < A.length; i++) {
            A[i] = -A[i];
        }

        // run Kadane's algorithm on modified array
        int negMaxSum = kadane(A);

        // restore the array
        for (int i = 0; i < A.length; i++) {
            A[i] = -A[i];
        }

        /**  return maximum of
         * 1. sum returned by Kadane's algorithm on original array.
         * 2. sum returned by Kadane's algorithm on modified array + sum of all elements of the array.
         */
        return Integer.max(kadane(A), Arrays.stream(A)
                                            .sum() + negMaxSum);
    }

    static void print(int[] a, int n, int ind) {
        // print from ind-th index to (n+i)th index.
        for (int i = ind; i < n + ind; i++) {
            System.out.print(a[(i % n)] + " ");
        }
        System.out.println();
    }

    static int maxCircularSum(int a[]) {
        System.out.print("maxCircularSum: ");
        int n = a.length;

        // Case 1: get the maximum sum using standard kadane'
        // s algorithm
        int max_kadane = kadane(a);

        // Case 2: Now find the maximum sum that includes
        // corner elements.
        int max_wrap = 0;
        for (int i = 0; i < n; i++) {
            max_wrap += a[i]; // Calculate array-sum
            a[i] = -a[i];  // invert the array (change sign)
        }

        // max sum with corner elements will be:
        // array-sum - (-max subarray sum of inverted array)
        max_wrap = max_wrap + kadane(a);

        // The maximum circular sum will be maximum of two sums
        return Integer.max(max_kadane, max_wrap);
    }

    // Standard Kadane's algorithm to find maximum subarray sum
    // See https://www.geeksforgeeks.org/archives/576 for details
    static int kadane(int a[]) {
        // find maximum element present in given array
        int max = Arrays.stream(a)
                        .max()
                        .getAsInt();

        // if array contains all negative values, return maximum element
        if (max < 0) {
            return max;
        }

        // stores maximum sum sub-array found so far
        int max_so_far = 0;

        // stores maximum sum of sub-array ending at current position
        int max_ending_here = 0;

        // traverse the given array
        for (int i = 0; i < a.length; i++) {
            // update maximum sum of sub-array "ending" at index i (by adding
            // current element to maximum sum ending at previous index i-1)
            max_ending_here = max_ending_here + a[i];

            // if maximum sum is negative, set it to 0 (which represents
            // an empty sub-array)
            max_ending_here = Integer.max(max_ending_here, 0);

            // update result if current sub-array sum is found to be greater
            max_so_far = Integer.max(max_so_far, max_ending_here);
        }

        return max_so_far;
    }
}
