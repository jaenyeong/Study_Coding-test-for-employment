package com.jaenyeong.chapter_16_dynamic_programming;

import java.util.Scanner;

public class PS06 {
    /*
    [Question]
    편집 거리

    [Input]
    1 line > cat
    2 line > cut
    [Output]
    > 1

    [Input]
    1 line > sunday
    2 line > saturday
    [Output]
    > 3

    [입력 조건]
    1 line > 편집을 시작할 문자열 (1 <= s1.length <= 5,000)
    2 line > 편집 완료후 최종 문자열 (1 <= s2.length <= 5,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("편집을 시작할 문자열을 입력하세요");
        final String s1 = SC.nextLine().trim();

        System.out.println("편집 완료 후 최종 문자열을 입력하세요");
        final String s2 = SC.nextLine().trim();

        System.out.println(bookPS(s1, s2));
    }

    private static int bookPS(final String startStr, final String endStr) {
        final int startSize = startStr.length();
        final int endSize = endStr.length();
        final int[][] dp = new int[startSize + 1][endSize + 1];

        // DP 테이블 초기화
        initDPTable(startSize, endSize, dp);

        // 최소 편집 거리 계산 (두번째 인덱스부터 반복)
        for (int row = 1; row <= startSize; row++) {
            for (int col = 1; col <= endSize; col++) {

                // 해당 위치에 시작 문자열과 최종 문자열의 값이 같은 경우
                if (startStr.charAt(row - 1) == endStr.charAt(col - 1)) {
                    dp[row][col] = dp[row - 1][col - 1];
                    continue;
                }

                // 해당 위치에 시작 문자열과 최종 문자열의 값이 다른 경우
                // 삽입의 경우 (왼쪽)
                final int insert = dp[row][col - 1];
                // 삭제의 경우 (위쪽)
                final int delete = dp[row - 1][col];
                // 교체의 경우 (왼쪽 위)
                final int replace = dp[row - 1][col - 1];

                // 위 세 가지 경우 중 가장 작은 편집 거리의 1을 더한 값을 DP 테이블에 삽입
                dp[row][col] = 1 + Math.min(insert, Math.min(delete, replace));
            }
        }

        return dp[startSize][endSize];
    }

    private static void initDPTable(final int startSize, final int endSize, final int[][] dp) {
        for (int row = 0; row <= startSize; row++) {
            dp[row][0] = row;
        }
        for (int col = 0; col <= endSize; col++) {
            dp[0][col] = col;
        }
    }
}
