package com.usunified;

/**
 * https://algorithmsandme.com/contiguous-subarray-with-largest-sum/
 */
public class MaximumSubArraySum {
    public static void main(String[] args) {
        int[] arr1 = {8, -1, 3, 4}; //  15
        print(arr1, arr1.length, 6);
        System.out.println(maxSumSubarraycircular(arr1));
        System.out.println(maxSumOfSubArray(arr1));
        System.out.println(maxCircularSum(arr1));
        System.out.println(maxSubArraySum(arr1));
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
        System.out.println(maxSumOfSubArray(arr4));
        System.out.println(maxCircularSum(arr4));
        System.out.println();

        int[] arr5 = {-2, -3, 4, -1, -2, 1, 5, -3};
        System.out.println(maxSumOfSubArray(arr5));
        System.out.println(maxSumOfSubArrayII(arr5));
        System.out.println(maxSubArraySum(arr5));
        System.out.println();

        int[] arr6 = {-1, -4, -5, -2, -1};
        System.out.println(maxSubArraySum(arr6));
        System.out.println(maxSumSubarraycircular(arr6));
    }

    static int maxSumOfSubArrayII(int[] arr) {
        System.out.print("maxSumOfSubArrayII: ");
        int maxSum = 0;
        for (int i = 0; i < arr.length; i++) {
            int sum = maxSum + arr[i];
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }

    static int maxSumOfSubArray(int[] a) {
        System.out.print("maxSumOfSubArray: ");
        int maxSum = a[0];
        int currentSum = a[0];

        for(int i=1; i<a.length; i++) {
            currentSum = Integer.max(a[i], currentSum + a[i]);
            if (maxSum < currentSum) {
                maxSum = currentSum;
            }
        }
        return maxSum;
    }

    static void print(int[] a, int n, int ind) {
        // print from ind-th index to (n+i)th index.
        /*for (int i = ind; i < n + ind; i++) {
            System.out.print(a[(i % n)] + " ");
        }
        System.out.println();*/
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
        return (max_wrap > max_kadane) ? max_wrap : max_kadane;
    }

    // Standard Kadane's algorithm to find maximum subarray sum
    // See https://www.geeksforgeeks.org/archives/576 for details
    static int kadane(int a[]) {
        int n = a.length;
        int max_so_far = 0, max_ending_here = 0;
        for (int i = 0; i < n; i++) {
            max_ending_here = max_ending_here + a[i];
            if (max_ending_here < 0) max_ending_here = 0;
            if (max_so_far < max_ending_here) max_so_far = max_ending_here;
        }
        return max_so_far;
    }

    static int maxSubArraySum(int a[]) {
        System.out.print("maxSubArraySum: ");
        int size = a.length;
        int max_so_far = 0, max_ending_here = 0;

        for (int i = 0; i < size; i++) {
            max_ending_here = max_ending_here + a[i];
            if (max_ending_here < 0) {
                max_ending_here = 0;
            }
        /* Do not compare for all
           elements. Compare only
           when max_ending_here > 0 */
            else if (max_so_far < max_ending_here) {
                max_so_far = max_ending_here;
            }

        }
        return max_so_far;
    }

    public static int maxSumSubarraycircular(int[] A) {
        System.out.print("maxSumSubarraycircular: ");
        /*
         * Iterate two times to complete the circle
         */
        int max = 0;
        int k = 0;
        int t_max = 0;
        for (int i = 0; i < 2 * A.length; i++) {
            k = i % A.length;
            t_max += A[k];

            if (t_max < 0) {
                t_max = 0;
            } else if (t_max > max) {
                max = t_max;
            }

        }
        return max;
    }
}
