package com.jaenyeong.chapter_16_dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PS01 {
    /*
    [Question]
    금광

    [Input]
    1 line > 2
    2 line > 3 4
    3 line > 1 3 3 2 2 1 4 1 0 6 4 7
    4 line > 4 4
    5 line > 1 3 1 5 2 2 4 1 5 0 2 3 0 6 1 2
    [Output]
    1 line > 19
    2 line > 16

    [입력 조건]
    1 line > 테스트 케이스 수 (1 <= t <= 1,000)
    2 ~ (1 + 2) line > 금광의 가로, 세로 크기 (1 <= n <= 20), (1 <= m <= 20)
                       금광의 각 영역에 매장된 금의 개수 (1 <= g <= 100)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int NONE = -1;

    private static final int[][] PREVIOUS_POINT = {{-1, -1}, {0, -1}, {1, -1}};
    private static final int ROW = 0;
    private static final int COL = 1;

    public static void main(String[] args) {
        System.out.println("테스트 케이스 수를 입력하세요");
        final int t = SC.nextInt();

        final List<int[][]> goldMines = new ArrayList<>();

        for (int i = 0; i < t; i++) {
            System.out.println((i + 1) + "번째 금광의 크기를 입력하세요");
            final int n = SC.nextInt();
            final int m = SC.nextInt();

            final int[][] goldMine = new int[n][m];

            System.out.println("금광의 각 영역에 매장된 금의 개수를 입력하세요");
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < m; col++) {
                    goldMine[row][col] = SC.nextInt();
                }
            }
            goldMines.add(goldMine);
        }

        myPS(goldMines);
        bookPS(goldMines);
    }

    private static void myPS(final List<int[][]> goldMines) {
        // 주어진 금광 테스트 케이스를 반복하며 처리
        for (int[][] goldMine : goldMines) {
            dpSolve(goldMine);
        }
    }

    private static void dpSolve(final int[][] goldMine) {
        final int rowSize = goldMine.length;
        final int colSize = goldMine[0].length;

        final int[][] dpTable = new int[rowSize][colSize];
        // DP 테이블의 영역 초기화 여부 판단을 위해 각 영역의 값을 -1로 초기화
        Arrays.stream(dpTable).forEach((arr) -> Arrays.fill(arr, NONE));

        // 첫번째 열의 모든 행의 값 초기화
        for (int i = 0; i < rowSize; i++) {
            dpTable[i][0] = goldMine[i][0];
        }

        // 금광의 각 위치를 순회하며 처리
        for (int col = 1; col < colSize; col++) {
            for (int row = 0; row < rowSize; row++) {
                // 현재 위치의 금의 개수
                final int curGold = goldMine[row][col];

                // 누적된 금의 개수 중 가장 큰 값
                int maxSumGold = getCurPointMaxGold(rowSize, dpTable, col, row, curGold);

                // DP 테이블에 누적된 금의 개수 중 가장 큰 값을 기록
                dpTable[row][col] = maxSumGold;
            }
        }

        printResultMaxGold(rowSize, colSize, dpTable);
    }

    private static int getCurPointMaxGold(final int rowSize, final int[][] dpTable, final int col, final int row,
                                          final int curGold) {
        int maxSumGold = 0;

        // 총 3개의 이전 위치 순회
        for (int[] prev : PREVIOUS_POINT) {
            final int curRow = row + prev[ROW];
            final int curCol = col + prev[COL];

            // 이전 위치가 이동 가능한 지역인지 확인
            if ((0 <= curRow) && (curRow < rowSize)) {
                // 이전 위치까지의 금의 누적 개수와 현재 위치의 금의 개수를 합산
                final int sumGold = dpTable[curRow][curCol] + curGold;

                // 이전 위치들 중에서 가장 큰 값을 삽입
                maxSumGold = Math.max(maxSumGold, sumGold);
            }
        }
        return maxSumGold;
    }

    private static void printResultMaxGold(final int rowSize, final int colSize, final int[][] dpTable) {
        // 마지막 열에서 가장 큰 금의 크기를 출력
        int maxValue = 0;
        for (int row = 0; row < rowSize; row++) {
            maxValue = Math.max(maxValue, dpTable[row][colSize - 1]);
        }

        System.out.println(maxValue);
    }

    private static void bookPS(final List<int[][]> goldMines) {
        // 주어진 금광 테스트 케이스를 반복하며 처리
        for (int[][] goldMine : goldMines) {
            bookDPSolve(goldMine);
        }
    }

    private static void bookDPSolve(final int[][] goldMine) {
        final int rowSize = goldMine.length;
        final int colSize = goldMine[0].length;

        final int[][] dpTable = new int[rowSize][colSize];

        // DP 테이블에 금광 테이블 값을 모두 복사
        for (int i = 0; i < rowSize; i++) {
            System.arraycopy(goldMine[i], 0, dpTable[i], 0, colSize);
        }

        // 다이나믹 프로그래밍 진행
        for (int j = 1; j < colSize; j++) {
            for (int i = 0; i < rowSize; i++) {
                int leftUp, leftDown, left;

                // 왼쪽 위에서 오는 경우
                if (i == 0) leftUp = 0;
                else leftUp = dpTable[i - 1][j - 1];

                // 왼쪽 아래에서 오는 경우
                if (i == rowSize - 1) leftDown = 0;
                else leftDown = dpTable[i + 1][j - 1];

                // 왼쪽에서 오는 경우
                left = dpTable[i][j - 1];

                // DP 테이블에 기록
                dpTable[i][j] = dpTable[i][j] + Math.max(leftUp, Math.max(leftDown, left));
            }
        }

        int result = 0;
        for (int i = 0; i < rowSize; i++) {
            result = Math.max(result, dpTable[i][colSize - 1]);
        }

        System.out.println(result);
    }
}
