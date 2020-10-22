package com.jaenyeong.chapter_13_dfs_bfs;

import java.util.*;

public class PS07 {
    /*
    [Question]
    인구 이동

    [Input]
    1 line > 2 20 50
    2 line > 50 30
    3 line > 20 40
    [Output]
    > 1

    [Input]
    1 line > 2 40 50
    2 line > 50 30
    3 line > 20 40
    [Output]
    > 0

    [Input]
    1 line > 2 20 50
    2 line > 50 30
    3 line > 30 40
    [Output]
    > 1

    [Input]
    1 line > 3 5 10
    2 line > 10 15 20
    3 line > 20 30 25
    4 line > 40 22 10
    [Output]
    > 2

    [Input]
    1 line > 4 10 50
    2 line > 10 100 20 90
    3 line > 80 100 60 70
    4 line > 70 20 30 40
    5 line > 50 20 100 10
    [Output]
    > 3

    [입력 조건]
    1 line > 정사각형 형태의 땅의 크기 (1 <= n <= 50)
             도시간 연합의 기준이 될 인구 차이의 범위 최솟값 (1 <= l <= 100), 최댓값 (1 <= r <= 100)
    2 ~ (1 + n) line > 각 나라의 인구 수 (0 <= a[r][c] <= 100)

    (인구 이동 횟수 <= 2,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    // 상하좌우
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // 국가 상태 초기화
    private static final int INIT_STATUS = -1;

    private static final int ROW = 0;
    private static final int COL = 1;

    public static void main(String[] args) {
        System.out.println("정사각형 형태의 땅의 크기와 도시간 연합의 기준이 될 인구 차이의 범위를 입력하세요");
        final int n = SC.nextInt();
        final int min = SC.nextInt();
        final int max = SC.nextInt();

        final int[][] countries = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 도시 인구 수 정보를 입력하세요");
            for (int j = 0; j < n; j++) {
                countries[i][j] = SC.nextInt();
            }
        }

        System.out.println(bookPS(countries, n, min, max));
    }

    private static int bookPS(final int[][] countries, final int n, final int min, final int max) {
        // 최종 이동 횟수
        int totalMoveCount = 0;

        // 연합 선언
        final int[][] union = new int[n][n];

        // 연합을 생성하지 못할 때까지 (인구 이동을 못할 때까지)
        while (true) {
            // 연합 초기화
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    union[i][j] = INIT_STATUS;
                }
            }

            // 모든 나라의 연합 (인구 이동)을 확인하기 위해 사용할 인덱스 초기화
            int unionIdx = 0;

            // 각 나라를 순회
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {

                    // 해당 나라가 연합되지 않은 경우
                    if (union[row][col] == INIT_STATUS) {
                        // 인구 이동
                        movePopulation(countries, row, col, union, unionIdx, n, min, max);
                        unionIdx++;
                    }
                }
            }

            // 모든 나라를 순회하게 된 경우 > 인구 이동을 더이상 못하게 되는 경우 > 모든 나라의 인구 이동이 끝난 경우 탈출
            if (unionIdx == n * n) break;

            // 최종 이동 횟수 증가
            totalMoveCount++;
        }

        return totalMoveCount;
    }

    private static void movePopulation(final int[][] countries, final int startingRow, final int startingCol,
                                       final int[][] union, final int unionIdx, final int n, final int min, final int max) {
        // 해당 시작점 나라를 기준으로 연합 국가를 담는 리스트 초기화
        final List<Country> currentUnited = new ArrayList<>();
        currentUnited.add(new Country(startingRow, startingCol));

        // BFS를 위한 큐(링크드 리스트) 사용하여 인구 이동 계획 초기화
        final Queue<Country> movePlans = new LinkedList<>();
        movePlans.offer(new Country(startingRow, startingCol));

        // 시작점 나라의 연합 번호 부여
        union[startingRow][startingCol] = unionIdx;

        // 현재 연합의 전체 인구 수
        int sumPopulation = countries[startingRow][startingCol];

        // 인구 이동이 끝날 때까지
        while (!movePlans.isEmpty()) {
            final Country current = movePlans.poll();
            final int curRow = current.getRow();
            final int curCol = current.getCol();

            // 해당 국가를 기준으로 상하좌우의 있는 나라를 순회
            for (int[] direction : DIRECTIONS) {
                int nextRow = curRow + direction[ROW];
                int nextCol = curCol + direction[COL];

                // 다음 위치에 유효성(땅의 포함되는 좌표인지) 확인
                if ((0 > nextRow) || (nextRow >= n)
                    || (0 > nextCol) || (nextCol >= n)
                    // 해당 나라가 연합에 속한 나라인지 확인
                    || (union[nextRow][nextCol] != INIT_STATUS)) {
                    continue;
                }

                // 현재 나라와 다음 순번 나라간 인구 수 차이
                final int gap = Math.abs(countries[curRow][curCol] - countries[nextRow][nextCol]);

                // 인구 수 차이가 주어진 범위를 벗어나는 경우
                if ((min > gap) || (gap > max)) {
                    continue;
                }

                // 인구 이동 계획에 추가
                movePlans.offer(new Country(nextRow, nextCol));
                // 해당 나라에 연합 번호 부여
                union[nextRow][nextCol] = unionIdx;
                // 연합을 구성하는 나라의 인구 수 합산
                sumPopulation += countries[nextRow][nextCol];
                // 연합에 해당 국가 포함
                currentUnited.add(new Country(nextRow, nextCol));
            }
        }

        // 연합 국가끼리 인구 수 분배
        final int dividedPopulation = sumPopulation / currentUnited.size();
        for (Country country : currentUnited) {
            final int countryRow = country.getRow();
            final int countryCol = country.getCol();
            countries[countryRow][countryCol] = dividedPopulation;
        }
    }
}

class Country {
    private final int row;
    private final int col;

    public Country(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
