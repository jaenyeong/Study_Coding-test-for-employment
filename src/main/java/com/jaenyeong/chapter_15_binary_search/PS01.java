package com.jaenyeong.chapter_15_binary_search;

import java.util.Scanner;

public class PS01 {
    /*
    [Question]
    정렬된 배열에서 특정 수의 개수 구하기

    [Input]
    1 line > 7 2
    2 line > 1 1 2 2 2 2 3
    [Output]
    > 4

    [Input]
    1 line > 7 4
    2 line > 1 1 2 2 2 2 3
    [Output]
    > -1

    [입력 조건]
    1 line > 수열의 원소 개수 (1 <= n <= 1,000,000), 찾아야 할 값 (-10^9 <= x <= 10^9)
    2 line > 수열의 각 원소 (-10^9 <= e <= 10^9)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int FAIL = -1;

    public static void main(String[] args) {
        System.out.println("수열의 원소 수와 찾을 값을 입력하세요");
        final int n = SC.nextInt();
        final int target = SC.nextInt();

        System.out.println("수열의 각 원소를 입력하세요");
        final int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = SC.nextInt();
        }

        System.out.println(bookPS(arr, target));
    }

    private static int bookPS(final int[] arr, final int target) {
        // 선형 탐색은 시간 복잡도가 크기 때문에 이진 탐색을 통하여 해결
        final int result = binarySearch(arr, target);
        return result == 0 ? FAIL : result;
    }

    private static int binarySearch(final int[] arr, final int target) {
        // 따라서 이진 탐색으로 해당 값에 최대 인덱스와 최소 인덱스를 구해 타겟 값의 수를 구함
        return getLastIdx(arr, target) - getFirstIdx(arr, target);
    }

    private static int getFirstIdx(final int[] arr, final int target) {
        int start = 0;
        int end = arr.length;

        while (start < end) {
            final int mid = (start + end) / 2;
            // 배열의 해당 값이 타겟 값보다 크거나 같다면 마지막 인덱스 축소
            if (target <= arr[mid]) {
                end = mid;
                continue;
            }
            start = mid + 1;
        }
        return end;
    }

    private static int getLastIdx(final int[] arr, final int target) {
        int start = 0;
        int end = arr.length;

        while (start < end) {
            final int mid = (start + end) / 2;
            // 배열의 해당 값이 타겟 값보다
            if (target < arr[mid]) {
                end = mid;
                continue;
            }
            start = mid + 1;
        }
        return end;
    }
}
