package com.jaenyeong.chapter_15_binary_search;

import java.util.Scanner;

public class PS02 {
    /*
    [Question]
    고정점 찾기

    [Input]
    1 line > 5
    2 line > -15 -6 1 3 7
    [Output]
    > 3

    [Input]
    1 line > 7
    2 line > -15 -4 2 8 9 13 15
    [Output]
    > 2

    [Input]
    1 line > 7
    2 line > -15 -4 3 8 9 13 15
    [Output]
    > -1

    [입력 조건]
    1 line > 수열 원소의 개수 (1 <= n <= 1,000,000)
    2 line > 수열의 각 원소 값 (-10^9 <= element <= 10^9)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int NOT_FOUND = -1;

    public static void main(String[] args) {
        System.out.println("수열 원소의 개수를 입력하세요");
        final int n = SC.nextInt();

        System.out.println("수열의 각 원소를 입력하세요");
        final int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = SC.nextInt();
        }

        System.out.println(myPS(arr));
        System.out.println(bookPS(arr, n));
    }

    private static int myPS(final int[] arr) {
        return foundFixedPoint(arr);
    }

    private static int foundFixedPoint(final int[] arr) {
        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            final int mid = (start + end) / 2;
            final int target = arr[mid];

            // 타겟이 인덱스와 동일한 경우
            if (target == mid) {
                return mid;
            }

            // 타겟이 인덱스보다 작은 경우
            if (target < mid) {
                start = mid + 1;
                continue;
            }

            // 타겟이 인덱스보다 큰 경우
            end = mid - 1;
        }

        // 타겟을 찾지 못한 경우
        return NOT_FOUND;
    }

    private static int bookPS(final int[] arr, final int n) {
        return binarySearch(arr, 0, n - 1);
    }

    public static int binarySearch(int[] arr, int start, int end) {
        if (start > end) return -1;
        int mid = (start + end) / 2;

        // 고정점을 찾은 경우 중간점 인덱스 반환
        if (arr[mid] == mid) return mid;

        // 중간점의 값보다 중간점이 작은 경우 왼쪽 확인
        if (arr[mid] > mid) return binarySearch(arr, start, mid - 1);
            // 중간점의 값보다 중간점이 큰 경우 오른쪽 확인
        else return binarySearch(arr, mid + 1, end);
    }
}
