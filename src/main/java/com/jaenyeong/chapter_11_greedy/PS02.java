package com.jaenyeong.chapter_11_greedy;

import java.util.Scanner;

public class PS02 {
    /*
    [Question]
    곱하기 혹은 더하기

    [Input]
    > 02984
    [Output]
    > 576

    [Input]
    > 567
    [Output]
    > 210

    [입력 조건]
    > 여러 개의 숫자(0 ~ 9)로 구성된 하나의 문자열 (1 <= s.length <= 20)

     */

    private final static Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("숫자로 이루어진 연산 문자열을 입력하세요");
        final String input = SC.next();

        System.out.println(myPS(input));
        System.out.println(bookPS(input));
    }

    private static long myPS(final String input) {
        final String[] inputs = input.split("");

        // 입력 받은 문자열을 숫자 배열로 변환
        final int[] numbers = new int[inputs.length];
        final int numbersSize = numbers.length;
        for (int i = 0; i < numbersSize; i++) {
            numbers[i] = Integer.parseInt(inputs[i]);
        }

        long result = 0;

        for (final int number : numbers) {

            // 해당 숫자 및 기존 연산 합계가 2 이상인 경우 곱하기
            if ((number > 1) && (result > 1)) {
                result *= number;
                continue;
            }

            // 그 외 더하기
            result += number;
        }

        return result;
    }

    private static long bookPS(final String input) {

        // 첫 번째 문자를 숫자로 변경한 값 대입
        long result = input.charAt(0) - '0';

        for (int i = 1; i < input.length(); i++) {
            int num = input.charAt(i) - '0';
            if (num <= 1 || result <= 1) {
                result += num;
            } else {
                result *= num;
            }
        }

        return result;
    }
}
