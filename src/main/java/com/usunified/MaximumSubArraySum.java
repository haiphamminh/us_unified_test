package com.usunified;

public class MaximumSubArraySum {
    public static void main(String[] args) {
        int[] arr1 = {8, -1, 3, 4}; //  15
        System.out.println(maxSumOfSubArray(arr1));

        int[] arr2 = {-4, 5, 1, 0}; // 6
        System.out.println(maxSumOfSubArray(arr2));

        int[] arr3 = {-4, -5, -1, 0}; // 0
        System.out.println(maxSumOfSubArray(arr3));

        int[] arr4 = {-4, -5, 1, 10}; // 11
        System.out.println(maxSumOfSubArray(arr4));
    }

    static int maxSumOfSubArray(int[] arr) {
        int maxSum = 0;
        for (int i = 0; i < arr.length; i++) {
            int sum = maxSum + arr[i];
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }
}
