package com.jaenyeong.chapter_08_dynamic_programming;

import java.util.Arrays;
import java.util.Scanner;

public class Question08 {
    /*
    [Question]
    효율적인 화폐 구성

    [Input]
    1 line > 2 15
    2 line > 2
    3 line > 3
    [Output]
    > 5

    [Input]
    1 line > 3 4
    2 line > 3
    3 line > 5
    4 line > 7
    [Output]
    > -1

     */

    private static final int FAIL_LIMIT = 10_000;
    private static final int FAIL = -1;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 화폐의 종류 수 (1 <= n <= 100), 목표 금액 (1 <= m <= 10,000)
        // 2번째 라인 이후 : 각 화폐 단위 (10,000 이하의 자연수)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("화폐의 종류 수와 목표 금액을 입력하세요");
        final int n = scanner.nextInt(); // 3
        final int m = scanner.nextInt(); // 15

        // 주어진 화폐 단위
        final int[] money = new int[n];
        System.out.println("각 화폐 단위를 입력하세요");
        for (int i = 0; i < n; i++) {
            money[i] = scanner.nextInt(); // 2, 3, 5
        }

        // 목표 금액 대비 최소 동전 사용 횟수를 구하기 위해 사용할 배열 선언
        // 인덱스를 목표 금액으로 사용하기 위해 화폐 종류 수 + 1 크기로 생성
        final int[] counts = new int[m + 1];

        // 배열 초기화
        Arrays.fill(counts, FAIL_LIMIT + 1);
        counts[0] = 0;

        // 각 화폐 별로 목표금액까지 최소 구성 경우의 수 연산
        for (int coin : money) {
            for (int i = coin; i <= m; i++) {
                counts[i] = Math.min(counts[i], counts[i - coin] + 1);
            }
        }

        // 보유한 화폐로 목표 금액을 구성할 수 없는 경우 -1 출력
        System.out.println((counts[m] > FAIL_LIMIT) ? FAIL : counts[m]);
    }
}
