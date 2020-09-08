package com.jaenyeong.chapter_06_sort;

import java.util.Arrays;

public class Question04 {
    /*
    [Question]
    계수 정렬

    [Input]
    >
    [Output]
    >

     */

    public static void main(String[] args) {
        final int[] arr = {7, 5, 9, 0, 3, 1, 6, 2, 9, 1, 4, 8, 0, 5, 2};

        // 배열의 원소 중 최솟값, 최댓값 확인 (새로 생성할 배열 크기에 사용)
        final int min = Arrays.stream(arr).min().getAsInt();
        final int max = Arrays.stream(arr).max().getAsInt();

        // 배열의 원소 중 최댓값과 최솟값의 차에 1을 더하여 계수 정렬에 사용할 배열 크기로 사용
        final int countingArrLength = (max - min) + 1;

        // 계수 정렬에 사용할 배열 생성
        final int[] countingArr = new int[countingArrLength];

        // 기존 배열 원소를 카운트하여 새 배열에 삽입
        for (int element : arr) {
            countingArr[element]++;
        }

        // 새 배열에 삽입된 원소 출력
        for (int i = 0; i < countingArrLength; i++) {
            for (int j = 0; j < countingArr[i]; j++) {
                System.out.print(i + " ");
            }
        }
    }
}
