package com.jaenyeong.chapter_11_greedy;

import java.util.Scanner;

public class PS05 {
    /*
    [Question]
    볼링공 고르기

    [Input]
    1 line > 5 3
    2 line > 1 3 2 3 2
    [Output]
    > 8

    [Input]
    1 line > 8 5
    2 line > 1 5 4 3 2 4 5 2
    [Output]
    > 25

    [입력 조건]
    1 line > 공의 개수 (1 <= n <= 1,000), 주어진 볼링공의 최대 무게 (1 <= m <= 10)
    2 line > 각 볼링공의 무게 n개 (1 <= k <= m)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("공의 개수와 공의 최대 무게를 입력하세요");
        final int n = SC.nextInt();
        final int m = SC.nextInt();
        SC.nextLine();

        System.out.println("각 볼링공의 무게를 입력하세요");
        final int[] myBalls = new int[n];           // my
        final int[] bookBalls = new int[m + 1]; // book

        for (int i = 0; i < n; i++) {
            final int x = SC.nextInt();

            myBalls[i] = x;
            bookBalls[x]++;
        }

        System.out.println(myPS(myBalls, n));
        System.out.println(bookPS(bookBalls, n, m));
    }

    private static int myPS(final int[] balls, final int n) {
        int count = 0;
        // A가 하나의 공을 선택
        for (int i = 0; i < n; i++) {
            // B는 A가 선택한 공을 제외하고 다른 공 선택
            for (int j = i + 1; j < n; j++) {
                int first = balls[i];
                int second = balls[j];

                // 무게가 다른 경우 카운트
                if (first != second) count++;
            }
        }

        return count;
    }

    private static int bookPS(final int[] balls, final int n, final int m) {
        // 남은 공의 수
        int remaining = n;
        // 결과
        int result = 0;

        for (int i = 1; i <= m; i++) {
            // A가 선택할 수 있는 개수
            int aChoice = balls[i];
            // 무게가 i인 볼링공의 개수(A가 선택할 수 있는 개수)를 제외
            remaining -= aChoice;
            // B가 선택하는 경우의 수와 곱해주기
            // -> A가 선택하는 경우의 수와 B가 선택할 수 있는 경우의 수인 남은 공의 수를 곱해주기
            result += aChoice * remaining;
        }

        return result;
    }
}
