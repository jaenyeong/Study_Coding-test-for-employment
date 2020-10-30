package com.jaenyeong.chapter_16_dynamic_programming;

import java.util.Scanner;

public class PS05 {
    /*
    [Question]
    못생긴 수

    [Input]
    > 10
    [Output]
    > 12

    [Input]
    > 4
    [Output]
    > 4

    [입력 조건]
    > 찾을 못생긴 수의 순번 (1 <= n <= 1,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int PRIME_FACTOR_2 = 2;
    private static final int PRIME_FACTOR_3 = 3;
    private static final int PRIME_FACTOR_5 = 5;

    public static void main(String[] args) {
        System.out.println("찾을 못생긴 수의 순번을 입력하세요");
        final int n = SC.nextInt();

        System.out.println(bookPS(n));
    }

    private static int bookPS(final int targetIdx) {
        // * 못생긴 수는 2,3,5를 곱한 수 또한 못생긴 수에 해당함

        // 2, 3, 5배 연산의 결과 저장시 사용할 인덱스 초기화
        int idx2 = 0, idx3 = 0, idx5 = 0;

        // 2, 3, 5배 연산을 위한 변수 초기화
        int nextMulOf2 = PRIME_FACTOR_2;
        int nextMulOf3 = PRIME_FACTOR_3;
        int nextMulOf5 = PRIME_FACTOR_5;

        // 못생긴 수 배열 초기화
        final int[] uglyNumbers = new int[1000];
        uglyNumbers[0] = 1;

        // 1번 인덱스부터 주어진 타겟 인덱스까지 반복
        for (int i = 1; i < targetIdx; i++) {
            // 가능한 곱셈 결과 중에서 가장 작은 수를 선택
            final int minNumber = Math.min(nextMulOf2, (Math.min(nextMulOf3, nextMulOf5)));
            uglyNumbers[i] = minNumber;

            // 인덱스에 따라서 곱셈 결과를 증가
            if (minNumber == nextMulOf2) {
                idx2++;
                nextMulOf2 = uglyNumbers[idx2] * PRIME_FACTOR_2;
            }

            if (minNumber == nextMulOf3) {
                idx3++;
                nextMulOf3 = uglyNumbers[idx3] * PRIME_FACTOR_3;
            }

            if (minNumber == nextMulOf5) {
                idx5++;
                nextMulOf5 = uglyNumbers[idx5] * PRIME_FACTOR_5;
            }
        }

        // 타겟 인덱스번째 못생긴 수 반환
        return uglyNumbers[targetIdx - 1];
    }
}
