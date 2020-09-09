package com.jaenyeong.chapter_07_binary_search;

import java.util.Scanner;

public class Question02 {
    /*
    [Question]
    이진 탐색

    [Input]
    >
    [Output]
    >

     */

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("찾을 값을 입력하세요");
        final int target = scanner.nextInt();

        System.out.println("배열의 크기를 입력하세요");
        final int inputsSize = scanner.nextInt();

        System.out.println("배열의 원소를 입력하세요");
        final int[] inputs = new int[inputsSize];
        for (int i = 0; i < inputsSize; i++) {
            inputs[i] = scanner.nextInt();
        }

        final int recursive = recursiveBinarySearch(inputs, 0, inputsSize - 1, target);
        System.out.println(recursive);
        final int loop = loopBinarySearch(inputs, 0, inputsSize - 1, target);
        System.out.println(loop);
    }

    private static int recursiveBinarySearch(final int[] inputs, final int start, final int end, final int target) {
        if ((inputs == null) || (inputs.length <= 0)) return -1;

        if (start > end) return -1;

        final int midIdx = (start + end) / 2;
        final int midValue = inputs[midIdx];

        // 해당 값을 찾으면 인덱스 반환하고 종료
        if (midValue == target) return midIdx;

        // 중간 값이 타겟보다 큰 경우
        if (midValue > target) {
            return recursiveBinarySearch(inputs, start, midIdx - 1, target);
        }

        // 중간 값이 타겟보다 작은 경우
        return recursiveBinarySearch(inputs, midIdx + 1, end, target);
    }

    private static int loopBinarySearch(final int[] inputs, int start, int end, final int target) {
        if ((inputs == null) || (inputs.length <= 0)) return -1;

        while (start <= end) {
            final int midIdx = (start + end) / 2;
            final int midValue = inputs[midIdx];

            // 해당 값을 찾으면 인덱스 반환하고 종료
            if (midValue == target) {
                return midIdx;
            }

            // 중간 값이 타겟보다 큰 경우
            if (midValue > target) {
                end = midIdx - 1;
                continue;
            }

            // 중간 값이 타겟보다 작은 경우
            start = midIdx + 1;
        }

        return -1;
    }
}
