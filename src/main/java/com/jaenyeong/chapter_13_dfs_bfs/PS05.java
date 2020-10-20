package com.jaenyeong.chapter_13_dfs_bfs;

import java.util.Scanner;

public class PS05 {
    /*
    [Question]
    연산자 끼워 넣기

    [Input]
    1 line > 2
    2 line > 5 6
    3 line > 0 0 1 0
    [Output]
    1 line > 30
    2 line > 30

    [Input]
    1 line > 3
    2 line > 3 4 5
    3 line > 1 0 1 0
    [Output]
    1 line > 35
    2 line > 17

    [Input]
    1 line > 6
    2 line > 1 2 3 4 5 6
    3 line > 2 1 1 1
    [Output]
    1 line > 54
    2 line > -24

    [입력 조건]
    1 line > 수열을 이루는 수의 개수 (2 <= n <= 11)
    2 line > 수열의 각 원소 (1 <= a <= 100)
    3 line > 순서대로 덧셈, 뺄셈, 곱셈, 나눗셈의 개수 (operator_sum = (n - 1))

    [출력 조건]
    1 line > 최댓값 (max <= 1,000,000,000)
    2 line > 최솟값 (min <= 1,000,000,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static int minValue = (int) 1e9;
    private static int maxValue = (int) -1e9;

    public static void main(String[] args) {
        System.out.println("수열의 원소 개수를 입력하세요");
        final int n = SC.nextInt();

        SC.nextLine();

        System.out.println("수열을 구성하는 각 원소 값을 입력하세요");
        final int[] progression = new int[n];
        for (int i = 0; i < n; i++) {
            progression[i] = SC.nextInt();
        }

        System.out.println("순서대로 덧셈, 뺄셈, 곱셈, 나눗셈 연산 횟수를 입력하세요");
        final int plus = SC.nextInt();
        final int minus = SC.nextInt();
        final int times = SC.nextInt();
        final int dividedBy = SC.nextInt();

        bookPS(progression, n, 1, progression[0], plus, minus, times, dividedBy);

        System.out.println(maxValue);
        System.out.println(minValue);
    }

    private static void bookPS(final int[] progression, final int operandsSize, final int idx, final int currentNumber,
                               int plus, int minus, int times, int dividedBy) {
        // 피연산자 수열의 모든 원소에 대해 연산을 수행한 경우
        if (idx == operandsSize) {
            minValue = Math.min(minValue, currentNumber);
            maxValue = Math.max(maxValue, currentNumber);
            return;
        }

        // 연산을 재귀호출을 통하여 수행
        if (plus > 0) {
            plus--;
            bookPS(progression, operandsSize, idx + 1, (currentNumber + progression[idx]),
                plus, minus, times, dividedBy);
            plus++;
        }

        if (minus > 0) {
            minus--;
            bookPS(progression, operandsSize, idx + 1, (currentNumber - progression[idx]),
                plus, minus, times, dividedBy);
            minus++;
        }

        if (times > 0) {
            times--;
            bookPS(progression, operandsSize, idx + 1, (currentNumber * progression[idx]),
                plus, minus, times, dividedBy);
            times++;
        }

        if (dividedBy > 0) {
            dividedBy--;
            bookPS(progression, operandsSize, idx + 1, (currentNumber / progression[idx]),
                plus, minus, times, dividedBy);
        }
    }
}
