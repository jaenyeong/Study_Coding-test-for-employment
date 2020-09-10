package com.jaenyeong.chapter_08_dynamic_programming;

import java.util.Scanner;

public class Question07 {
    /*
    [Question]
    바닥 공사

    [Input]
    1 line > 3
    [Output]
    > 5

     */

    private static final int DIVISOR = 796_796;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 바닥 열-가로크기 (1 <= n <= 1,000)
        // 결과 출력 : 바닥을 채우는 방법 경우의 수를 796,796으로 나눈 나머지 출력
        final Scanner scanner = new Scanner(System.in);

        System.out.println("바닥 타일의 가로크기를 입력하세요");
        final int n = scanner.nextInt();

        final int[] dpTable = new int[n];

        // 주어진 타일의 조건으로 경우의 수 미리 입력
        dpTable[0] = 1;
        dpTable[1] = 3;

        for (int i = 2; i < n; i++) {
            // (i - 2)까지 타일이 채워져 있는 경우엔 타일을 채우는 방법은 2가지
            // 기존 (i - 1) 경우의 수와 합산
            dpTable[i] = (dpTable[i - 1] + (dpTable[i - 2] * 2)) % DIVISOR;
        }

        System.out.println(dpTable[n - 1]);
    }
}
