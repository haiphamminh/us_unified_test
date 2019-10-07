package com.usunified;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmallestDiffSubsetSum {
    public static void main(String[] args) {
//        int[] arr = {5, 8, 15, 23, 25};
        int[] arr = {25, 8, 5, 23, 15};
        // {10,25} {5,15,20} diff = 5 avg = 15
        // {5 25}=30 {8 23 15}=46 diff = 16
        // {5 15 23}=43 {8 25}=33 diff = 10
        // {5 8 23}=36 {15 25}=40 diff = 4
        // {5 8 15}=28 {23 25}=46 diff = 18
        // {5 8 25}=38 {23 15}=38 diff = 0
        List<List<Integer>> subsets = findSmallestDiffOfSubsets(arr);
        printSubsets(subsets);

        int[] arr4 = {5, 10, 15, 20, 25};
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
        List<List<Integer>> subsets3 = findSmallestDiffOfSubsets(arr3);
        printSubsets(subsets3);

        int[] arr1 = {3, 6, 10, 19, 30, 45};
        // {3 6 45} {19 10 30}  diff = 4 avg = 19
        // {3 10 30} {6 19 45} diff = 27 avg = 19
        // {3 10 45}=58 {6 19 30}=55 diff = 3 avg = 19
        // {3 19 45} {6 10 30} diff = 21 avg = 19
        // {3 19 30} {6 10 45} diff = 9 avg = 19
        // {10 6 30}=46 {19 3 45}=67 diff=21
        List<List<Integer>> subsets1 = findSmallestDiffOfSubsets(arr1);
        printSubsets(subsets1);

        int[] arr2 = {4, 9, 10, 19, 30, 45};
        // {4 10 45}=59 {9 19 30}=58 diff = 1 avg = 23.4
        // {4 9 45}=58 {10 19 30}=59 diff = 1 avg = 23.4
        // {4 19 30}=53 {9 10 45}=64 diff = 11 avg = 23.4
        // {4 9 10}=23 {19 30 45}=94 diff = 71 avg = 23.4
        List<List<Integer>> subsets2 = findSmallestDiffOfSubsets(arr2);
        printSubsets(subsets2);
    }

    /*static void printSumOfAllPossibleSubsets(int[] arr) {
        int noItems = arr.length / 2;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; i < arr.length; i++) {
                for (int i = 0; i < arr.length; i++) {

                }
            }
        }
    }*/

    static List<List<Integer>> findSmallestDiffOfSubsets(int[] arr) {
        Arrays.sort(arr);
        List<Integer> subset1 = new ArrayList<>();
        List<Integer> subset2 = new ArrayList<>();
        int sumLeft = 0, sumRight = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
//        for (int i = 0; i < arr.length; i++) {
            if (sumLeft < sumRight) {
                subset1.add(arr[i]);
                sumLeft += arr[i];
            } else {
                subset2.add(arr[i]);
                sumRight += arr[i];
            }
        }
        System.out.println(String.format("sumLeft = %d, sumRight = %d, diff = %d", sumLeft, sumRight,
                                         Math.abs(sumLeft - sumRight)));
        return Arrays.asList(subset1, subset2);
    }

    static void printSubsets(List<List<Integer>> subsets) {
        subsets.forEach(System.out::println);
        System.out.println("------------------");
    }
}
