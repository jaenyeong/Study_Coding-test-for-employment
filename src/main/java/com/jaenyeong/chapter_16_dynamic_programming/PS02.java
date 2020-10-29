package com.jaenyeong.chapter_16_dynamic_programming;

import java.util.Arrays;
import java.util.Scanner;

public class PS02 {
    /*
    [Question]
    정수 삼각형

    [Input]
    1 line > 5
    2 line > 7
    3 line > 3 8
    4 line > 8 1 0
    5 line > 2 7 4 4
    6 line > 4 5 2 6 5
    [Output]
    > 30

    [입력 조건]
    1 line > 삼각형의 크기 (1 <= n <= 500)
    2 ~ (1 + n) line > 정수 삼각형의 각 행

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int NONE = -1;

    public static void main(String[] args) {
        System.out.println("정수 삼각형의 크기를 입력하세요");
        final int n = SC.nextInt();

        // 정수 삼각형 테이블
        final int[][] triangleTable1 = new int[n][n];
        final int[][] triangleTable2 = new int[n][n];

        // 데이터 0과 구분하기 위해 초기값을 -1로 설정
        Arrays.stream(triangleTable1).parallel().forEach(t -> Arrays.fill(t, NONE));

        // 입력받은 정수 삼각형의 행 데이터를 정수 삼각형 테이블에 삽입
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 정수 삼각형 행 데이터를 입력하세요");
            for (int j = 0; j < i + 1; j++) {
                int input = SC.nextInt();
                triangleTable1[i][j] = input;
                triangleTable2[i][j] = input;
            }
        }

        System.out.println(myPS(triangleTable1, n));
        System.out.println(bookPS(triangleTable2, n));
    }

    private static int myPS(final int[][] triTable, final int n) {
        // 삼각형 테이블의 각 정수를 순회
        for (int row = 1; row < n; row++) {
            for (int col = 0; col <= row; col++) {
                final int prevRow = row - 1;
                final int prevCol = col - 1;

                // 전 위치에 열이 유효한 범위가 아닌 경우
                final int leftUpValue = (0 > prevCol) ? 0 : triTable[prevRow][prevCol];
                final int upValue = triTable[prevRow][col];

                final int curValue = triTable[row][col];

                // 큰 값을 골라 합산
                triTable[row][col] = Math.max(leftUpValue, upValue) + curValue;
            }
        }

        return getMaxSum(triTable[n - 1], n);
    }

    private static int getMaxSum(final int[] lastLine, final int n) {
        int sum = 0;
        for (int col = 0; col < n; col++) {
            sum = Math.max(lastLine[col], sum);
        }

        return sum;
    }

    private static int bookPS(final int[][] dp, final int n) {
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                int upLeft, up;

                // 왼쪽 위에서 내려오는 경우
                upLeft = (j == 0) ? 0 : dp[i - 1][j - 1];

                // 바로 위에서 내려오는 경우
                up = (j == i) ? 0 : dp[i - 1][j];

                // 최대 합 저장
                dp[i][j] = dp[i][j] + Math.max(upLeft, up);
            }
        }

        // 결과 테이블에서 가장 큰 값 추출
        int result = 0;
        for (int i = 0; i < n; i++) {
            result = Math.max(result, dp[n - 1][i]);
        }

        return result;
    }
}
