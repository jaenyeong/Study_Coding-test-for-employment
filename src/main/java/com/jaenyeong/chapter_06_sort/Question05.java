package com.jaenyeong.chapter_06_sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Question05 {
    /*
    [Question]
    위에서 아래로

    [Input]
    1 line > 3
    2 line > 15
    3 line > 27
    4 line > 12
    [Output]
    > 27 15 12

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 수열에 속해 있는 수의 개수 (1 <= n <= 500)
        // 2번째 라인 이후 : 1 이상 100,000 이하의 자연수 (n개의 자연수)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("수열에 크기를 입력하세요");
        final int n = scanner.nextInt();
        scanner.nextLine();

        // 라이브러리에서 지원하는 내림차순 정렬을 사용하기 위해 Integer 배열 사용
        final Integer[] sequence = new Integer[n];
        for (int i = 0; i < n; i++) {
            System.out.println(i + "번째 자연수를 입력하세요");
            sequence[i] = scanner.nextInt();
        }

        // 내림차순 정렬
        Arrays.sort(sequence, Collections.reverseOrder());

        System.out.println(Arrays.toString(sequence));
    }
}
