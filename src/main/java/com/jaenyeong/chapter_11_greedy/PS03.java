package com.jaenyeong.chapter_11_greedy;

import java.util.Scanner;

public class PS03 {
    /*
    [Question]
    문자열 뒤집기

    [Input]
    > 0001100
    [Output]
    > 1

    [입력 조건]
    > 0과 1로만 이루어진 문자열 s (1 <= s.length <= 1,000,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("0과 1로 이루어진 문자열을 입력하세요");
        final String input = SC.nextLine();

        System.out.println(bookPS(input));
    }

    private static int bookPS(final String input) {
        int zero = 0;
        int one = 0;

        if (input.charAt(0) == '0') {
            zero++;
        } else {
            one++;
        }

        final int size = input.length() - 1;
        for (int i = 0; i < size; i++) {
            char current = input.charAt(i);
            char next = input.charAt(i + 1);

            // 현재 숫자와 다음 숫자가 일치하면 통과
            if (current == next) {
                continue;
            }

            if (next == '0') {
                zero++;
            } else {
                one++;
            }
        }

        return Math.min(zero, one);
    }
}
