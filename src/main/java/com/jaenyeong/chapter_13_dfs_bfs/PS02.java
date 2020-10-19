package com.jaenyeong.chapter_13_dfs_bfs;

import java.util.Arrays;
import java.util.Scanner;

public class PS02 {
    /*
    [Question]
    연구소

    [Input]
    1 line > 7 7
    2 line > 2 0 0 0 1 1 0
    3 line > 0 0 1 0 1 2 0
    4 line > 0 1 1 0 1 0 0
    5 line > 0 1 0 0 0 0 0
    6 line > 0 0 0 0 0 1 1
    7 line > 0 1 0 0 0 0 0
    8 line > 0 1 0 0 0 0 0
    [Output]
    > 27

    [Input]
    1 line > 4 6
    2 line > 0 0 0 0 0 0
    3 line > 1 0 0 0 0 2
    4 line > 1 1 1 0 0 2
    5 line > 0 0 0 0 0 2
    [Output]
    > 9

    [Input]
    1 line > 7 7
    2 line > 2 0 0 0 1 1 0
    3 line > 0 0 1 0 1 2 0
    4 line > 0 1 1 0 1 0 0
    5 line > 0 1 0 0 0 0 0
    6 line > 0 0 0 0 0 1 1
    7 line > 0 1 0 0 0 0 0
    8 line > 0 1 0 0 0 0 0
    [Output]
    > 27

    [입력 조건]
    1 line > 지도 세로 크기(3 <= n <= 8), 지도 가로의 크기 (3 <= m <= 8)
    2 ~ (1 + n) line > 지도의 각 행의 모양 (0 = 빈칸, 1 = 벽, 2 = 바이러스)
                       빈 칸의 개수 (3 <= empty)
                       바이러스의 개수 (2 <= virus <= 10)

     */

    private static final Scanner SC = new Scanner(System.in);

    // 연구소 구성 요소
    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int VIRUS = 2;

    // 상하좌우
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int ROW = 0;
    private static final int COL = 1;

    // 기둥을 세울 수 있는 개수
    private static final int LIMIT = 3;

    // 결과
    private static int result = 0;

    public static void main(String[] args) {
        System.out.println("연구소의 세로 크기, 가로 크기를 입력하세요");
        final int n = SC.nextInt();
        final int m = SC.nextInt();
        SC.nextLine();

        final int[][] lab = new int[n][m];

        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 연구소 지도를 입력하세요");
            lab[i] = Arrays.stream(SC.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        System.out.println(bookPS(lab, n, m));
    }

    private static int bookPS(final int[][] lab, final int n, final int m) {
        dfsSolve(lab, n, m, 0);
        return result;
    }

    private static void dfsSolve(final int[][] lab, final int n, final int m, int numberOfWall) {
        // 3개의 기둥을 모두 설치한 경우 바이러스 전염 확인
        if (numberOfWall == LIMIT) {
            // 바이러스 감염 확인을 위한 임시 배열 생성
            final int[][] virusLab = new int[n][m];

            // 바이러스 연구소 배열에 값을 복사
            for (int i = 0; i < n; i++) {
                System.arraycopy(lab[i], 0, virusLab[i], 0, m);
            }

            // 바이러스 연구소를 바이러스에 감염시키기
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < m; col++) {

                    // 해당 영역에 바이러스가 있는 경우
                    if (virusLab[row][col] == VIRUS) {
                        infectVirus(virusLab, row, col);
                    }
                }
            }

            // 바이러스에 감염된 공간의 영역 수를 확인
            result = Math.max(result, countEmptySpace(virusLab));

            return;
        }

        // 그 외 기둥 설치
        installWall(lab, numberOfWall);
    }

    private static void infectVirus(final int[][] virusLab, final int row, final int col) {
        final int n = virusLab.length;
        final int m = virusLab[0].length;

        // 상하좌우 순으로 방향별 바이러스 감염 처리
        for (int[] dir : DIRECTIONS) {
            final int nextRow = row + dir[ROW];
            final int nextCol = col + dir[COL];

            // 영역을 벗어나지 않고 빈 공간인 경우 감염
            if ((0 <= nextRow) && (nextRow < n)
                && (0 <= nextCol) && (nextCol < m)
                && (virusLab[nextRow][nextCol] == EMPTY)) {

                // 해당 영역 감염
                virusLab[nextRow][nextCol] = VIRUS;

                // 감염 후 감염된 영역을 기준으로 다시 바이러스 감염 처리
                infectVirus(virusLab, nextRow, nextCol);
            }
        }
    }

    private static void installWall(final int[][] lab, int numberOfWall) {
        final int n = lab.length;
        final int m = lab[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 빈 공간인 경우
                if (lab[i][j] == EMPTY) {
                    // 해당 영역에 칸막이 설치
                    lab[i][j] = WALL;
                    // 칸막이 설치 개수 증가후 칸막이 설치 또는 바이러스 체크를 위해 재귀호출
                    numberOfWall++;
                    dfsSolve(lab, n, m, numberOfWall);

                    // 되돌리기
                    lab[i][j] = EMPTY;
                    numberOfWall--;
                }
            }
        }
    }

    private static int countEmptySpace(final int[][] virusLab) {
        final int m = virusLab[0].length;

        int countEmptySpace = 0;
        for (int[] ints : virusLab) {
            for (int j = 0; j < m; j++) {
                // 빈 영역의 공간 수 확인
                if (ints[j] == EMPTY) {
                    countEmptySpace++;
                }
            }
        }

        return countEmptySpace;
    }
}
