package com.jaenyeong.chapter_06_sort;

import java.util.Arrays;

public class Question03 {
    /*
    [Question]
    퀵 정렬

    [Input]
    >
    [Output]
    >

     */

    public static void main(String[] args) {
        final int[] arr = {7, 5, 9, 0, 3, 1, 6, 2, 4, 8};

        quickSort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(final int[] arr, final int startIdx, final int endIdx) {
        // 원소가 1개인 경우
        if (startIdx >= endIdx) {
            return;
        }

        // 피벗은 첫번째 원소
        int pivot = startIdx;
        int leftIdx = pivot + 1;
        int rightIdx = endIdx;

        while (leftIdx <= rightIdx) {
            // 왼쪽에서부터 피벗보다 큰 데이터를 찾음
            while ((leftIdx <= endIdx) && (arr[leftIdx] <= arr[pivot])) {
                leftIdx++;
            }

            // 오른쪽에서부터 피벗보다 작은 데이터를 찾음
            while ((startIdx < rightIdx) && (arr[pivot] <= arr[rightIdx])) {
                rightIdx--;
            }

            // left, right가 엇갈렸다면 작은 데이터와 교환
            if (rightIdx < leftIdx) {
                int temp = arr[pivot];
                arr[pivot] = arr[rightIdx];
                arr[rightIdx] = temp;
                continue;
            }

            // 엇갈리지 않은 경우 데이터 맞교환
            int temp = arr[leftIdx];
            arr[leftIdx] = arr[rightIdx];
            arr[rightIdx] = temp;
        }

        quickSort(arr, startIdx, rightIdx - 1);
        quickSort(arr, rightIdx + 1, endIdx);
    }
}
