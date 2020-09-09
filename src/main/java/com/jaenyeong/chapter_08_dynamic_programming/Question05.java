package com.jaenyeong.chapter_08_dynamic_programming;

import java.util.Scanner;
import java.util.function.Predicate;

public class Question05 {
    /*
    [Question]
    1로 만들기

    [Input]
    1 line > 26
    [Output]
    > 3

     */

    private static final int INPUT_LIMIT = 30_000;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 정수 (1 <= x <= 30,000)
        // 사용 가능한 연산 : 5의 배수일 때 5로 나누기
        //                  : 3의 배수일 때 3으로 나누기
        //                  : 2의 배수일 때 2로 나누기
        //                  : 1 빼기
        final Scanner scanner = new Scanner(System.in);

        System.out.println("정수를 입력하세요");
        final int x = scanner.nextInt();

        final int[] dpTable = new int[INPUT_LIMIT + 1];

        for (int i = 2; i <= x; i++) {
            // 1을 빼는 경우
            dpTable[i] = dpTable[i - 1] + 1;

            // 2의 배수인 경우
            getMin(((idx) -> idx % 2 == 0), dpTable, i, 2);

            // 3의 배수인 경우
            getMin(((idx) -> idx % 3 == 0), dpTable, i, 3);

            // 5의 배수인 경우
            getMin(((idx) -> idx % 5 == 0), dpTable, i, 5);
        }

        System.out.println(dpTable[x]);
    }

    private static void getMin(Predicate<Integer> exp, final int[] dp, final int idx, final int divisor) {
        if (exp.test(idx)) {
            dp[idx] = Math.min(dp[idx], dp[idx / divisor] + 1);
        }
    }
}
