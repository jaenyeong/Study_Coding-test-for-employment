package com.jaenyeong.chapter_06_sort;

import java.util.Arrays;

public class Question02 {
    /*
    [Question]
    삽입 정렬

    [Input]
    >
    [Output]
    >

     */

    public static void main(String[] args) {
        final int[] arr = {7, 5, 9, 0, 3, 1, 6, 2, 4, 8};

        final int arrLength = arr.length;
        for (int i = 1; i < arrLength; i++) {
            for (int j = i; j > 0; j--) {
                // 현재 원소와 그 전 인덱스에 있는 원소 대소 비교
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    continue;
                }
                break;
            }
        }

        System.out.println(Arrays.toString(arr));
    }
}
