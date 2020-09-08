package com.jaenyeong.chapter_06_sort;

import java.util.Arrays;

public class Question01 {
    /*
    [Question]
    선택 정렬

    [Input]
    >
    [Output]
    >
    
     */

    public static void main(String[] args) {
        final int[] arr = {7, 5, 9, 0, 3, 1, 6, 2, 4, 8};

        final int arrLength = arr.length;
        for (int i = 0; i < arrLength - 1; i++) {
            int minValueIdx = i;
            for (int j = i + 1; j < arrLength; j++) {
                if (arr[minValueIdx] > arr[j]) {
                    minValueIdx = j;
                }
            }

            // swap
            int tempValue = arr[minValueIdx];
            arr[minValueIdx] = arr[i];
            arr[i] = tempValue;
        }

        System.out.println(Arrays.toString(arr));
    }
}
