package com.jaenyeong.chapter_05_dfs_bfs;

import java.util.Arrays;
import java.util.Scanner;

public class Question03 {
    /*
    [Question]
    음료수 얼려 먹기

    [Input]
    1 line > 4 5
    2 line > 00100
    3 line > 00011
    4 line > 11111
    5 line > 00000
    [Output]
    > 3

    [Input]
    1  line > 15 14
    2  line > 00000111100000
    3  line > 11111101111110
    4  line > 11011101101110
    5  line > 11011101100000
    6  line > 11011111111111
    7  line > 11011111111100
    8  line > 11000000011111
    9  line > 01111111111111
    10 line > 00000000011111
    11 line > 01111111111000
    12 line > 00011111111000
    13 line > 00000001111000
    14 line > 11111111110011
    15 line > 11100011111111
    16 line > 11100011111111
    [Output]
    > 8

     */
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static int n = 0;
    private static int m = 0;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 행-세로크기 (1 <= n <= 1,000), 열-가로크기 (1 <= m <= 1,000)
        // 2번째 라인 이후 : 얼음 틀의 형태 표시 (빈공간-0, 칸막이-1)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("얼음 틀의 크기(n, m)를 입력하세요");
        n = scanner.nextInt();
        m = scanner.nextInt();
        scanner.nextLine();

        final int[][] iceGrid = new int[n][m];
        for (int i = 0; i < n; i++) {
            System.out.println("얼음틀의 " + i + "번째 행을 입력하세요");

            iceGrid[i] = Arrays.stream(scanner.nextLine().split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        }

        int count = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (dfs(iceGrid, row, col)) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    private static boolean dfs(final int[][] iceGrid, final int row, final int col) {
        // 유효 범위가 아닌 경우
        if ((0 > row) || (row >= n) || (0 > col) || (col >= m)) {
            return false;
        }

        // 육지가 아닌 경우 (바다이거나 이미 방문한 경우)
        if (iceGrid[row][col] != 0) {
            return false;
        }

        // 방문 처리
        iceGrid[row][col] = 2;

        // 해당 위치를 중심으로 주변 탐색
        for (int[] direction : DIRECTIONS) {
            dfs(iceGrid, row + direction[0], col + direction[1]);
        }

        return true;
    }
}
