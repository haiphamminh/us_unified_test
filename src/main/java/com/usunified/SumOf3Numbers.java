package com.usunified;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SumOf3Numbers {
    public static void main(String[] args) {
        int[] A = {-1, 2, 3, 4, 5, -3, -7, -5};
        printResult(solution1(A, A.length), 1);
        printResult(solution2(A, A.length), 2);
        printResult(solution3(A, A.length), 3);
    }

    static List<List<Integer>> solution1(int[] A, int N) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < N - 2; i++) {
            for (int j = i + 1; j < N - 1; j++) {
                for (int k = j + 1; k < N; k++) {
                    if ((A[i] + A[j] + A[k]) == 0) {
                        result.add(Arrays.asList(A[i], A[j], A[k]));
                    }
                }
            }
        }
        return result;
    }

    static List<List<Integer>> solution2(int A[], int n) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            // Find all pairs with sum equals to
            // "-arr[i]"
            Set<Integer> s = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                int x = -(A[i] + A[j]);
                if (s.contains(x)) {
                    result.add(Arrays.asList(A[i], x, A[j]));
                } else {
                    s.add(A[j]);
                }
            }
        }
        return result;
    }

    static List<List<Integer>> solution3(int arr[], int n) {
        List<List<Integer>> result = new ArrayList<>();
        // sort array elements
        Arrays.sort(arr);

        for (int i = 0; i < n - 1; i++) {
            // initialize left and right
            int l = i + 1;
            int r = n - 1;
            int x = arr[i];
            while (l < r) {
                int sum = x + arr[l] + arr[r];
                if (sum == 0) {
                    // print elements if it's sum is zero
                    result.add(Arrays.asList(arr[l], x, arr[r]));

                    l++;
                    r--;
                }
                // If sum of three elements is less than zero then increment in left
                else if (sum < 0) {
                    l++;
                } else {
                    r--; // if sum is greater than zero than decrement in right side
                }
            }
        }
        return result;
    }

    static void printResult(List<List<Integer>> result, int solutionNo) {
        System.out.println(String.format("There are %s triplets for solution %d", result.size(), solutionNo));
        result.forEach(System.out::println);
        System.out.println("--------------------------");
    }
}
