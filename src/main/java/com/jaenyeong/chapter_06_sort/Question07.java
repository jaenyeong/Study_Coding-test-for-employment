package com.jaenyeong.chapter_06_sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Question07 {
    /*
    [Question]
    두 배열의 원소 교체

    [Input]
    1 line > 5 3
    2 line > 1 2 5 4 3
    3 line > 5 5 6 6 5
    [Output]
    > 26

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 두 배열의 크기 (1 <= n <= 100,000), 바꿔치기 연산 수 (0 <= k <= n)
        // 2번째 라인 : 배열 a (공백으로 구분되며 모든 원소는 10,000,000 보다 작은 자연수)
        // 3번째 라인 : 배열 b (공백으로 구분되며 모든 원소는 10,000,000 보다 작은 자연수)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("배열의 크기와 바꿔치기 연산 수를 입력하세요");
        final int n = scanner.nextInt();
        final int k = scanner.nextInt();
        scanner.nextLine();

        final int[] a = new int[n];
        System.out.println("배열 A를 입력하세요");
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        scanner.nextLine();

        final Integer[] b = new Integer[n];
        System.out.println("배열 B를 입력하세요");
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        scanner.nextLine();

        // 정렬
        Arrays.sort(a);
        Arrays.sort(b, Collections.reverseOrder());

        // 두 배열의 원소를 k번 바꿔치기
        for (int i = 0; i < k; i++) {
            int temp = a[i];
            a[i] = b[i];
            b[i] = temp;
        }

        // 합계 출력
        int sum = Arrays.stream(a).sum();
        System.out.println(sum);
    }
}
