package com.jaenyeong.chapter_12_implementation;

import java.util.Scanner;

public class PS01 {
    /*
    [Question]
    럭키 스트레이트

    [Input]
    > 123402
    [Output]
    > LUCKY

    [Input]
    > 7755
    [Output]
    > READY

    [입력 조건]
    > 항상 짝수 형태 자릿수의 점수 n (10 <= n <= 99,999,999)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final String LUCKY = "LUCKY";
    private static final String READY = "READY";

    public static void main(String[] args) {
        System.out.println("짝수 형태의 자릿수를 가진 점수를 입력하세요");
        final String score = SC.nextLine();

        myPS(score);
        bookPS(score);
    }

    private static void myPS(final String score) {
        final int mid = score.length() / 2;

        // 왼쪽 점수
        final String leftScore = score.substring(0, mid);
        // 오른쪽 점수
        final String rightScore = score.substring(mid);

        // 각각 점수 합계를 구해 비교
        System.out.println(sum(leftScore) == sum(rightScore) ? LUCKY : READY);
    }

    private static int sum(final String score) {
        int result = 0;
        char[] charArr = score.toCharArray();

        for (char c : charArr) {
            result += c - '0';
        }

        return result;
    }

    private static void bookPS(final String score) {
        int summary = 0;

        // 왼쪽 부분의 자릿수의 합 더하기
        for (int i = 0; i < score.length() / 2; i++) {
            summary += score.charAt(i) - '0';
        }

        // 오른쪽 부분의 자릿수의 합 빼기
        for (int i = score.length() / 2; i < score.length(); i++) {
            summary -= score.charAt(i) - '0';
        }

        // 왼쪽 부분과 오른쪽 부분의 자릿수 합이 동일한지 검사
        if (summary == 0) System.out.println("LUCKY");

        else System.out.println("READY");
    }
}
