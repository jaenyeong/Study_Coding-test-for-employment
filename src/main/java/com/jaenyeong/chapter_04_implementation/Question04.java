package com.jaenyeong.chapter_04_implementation;

import java.util.Arrays;
import java.util.Scanner;

public class Question04 {
    /*
    [Question]
    게임 개발

    [Input]
    1 line > 4 4
    2 line > 1 1 0
    3 line > 1 1 1 1
    4 line > 1 0 0 1
    5 line > 1 1 0 1
    6 line > 1 1 1 1
    [Output]
    > 3

     */

    private static final int LAND = 0;
    private static final int[][] MOVE_STEP = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private static final int CHECK = 2;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 행-세로크기 (3 <= n), 열-가로크기 (m <= 50)
        // 2번째 라인 : 캐릭터 시작 행 좌표 (1 <= x <= n), 캐릭터 시작 열 좌표 (1 <= y <= m)
        //            : 방향 (0-북, 1-동, 2-남, 3-서)
        //            : 캐릭터의 시작 좌표는 바다가 아닌 육지에서 시작
        // 3번째 라인 이후 : 육지 또는 바다 표시 (육지-0, 바다-1)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("맵의 크기를 입력하세요");
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        scanner.nextLine();

        System.out.println("현재 좌표와 방향을 입력하세요");
        int[] curr = new int[]{scanner.nextInt(), scanner.nextInt()};
        int d = scanner.nextInt();
        scanner.nextLine();

        System.out.println("맵은 육지(0)와 바다(1)로 이루어져 있습니다");
        final int[][] mapGrid = new int[n][m];
        for (int i = 0; i < n; i++) {
            System.out.println("맵의 " + (i + 1) + "번째 라인을 입력하세요");
            mapGrid[i] = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        }

        int count = 1;
        int stayCount = 0;

        // 시작 지역 방문 처리
        mapGrid[curr[0]][curr[1]] = CHECK;

        while (true) {
            // 방향 전환
            d = changeDirection(d);

            // 현재 방향을 기준으로 이동할 지역
            int nextRow = curr[0] + MOVE_STEP[d][0];
            int nextCol = curr[1] + MOVE_STEP[d][1];

            // 이동할 다음 지역이 육지인 경우 이동
            if (mapGrid[nextRow][nextCol] == LAND) {

                // 이동 지역 방문 처리
                mapGrid[nextRow][nextCol] = CHECK;

                curr[0] = nextRow;
                curr[1] = nextCol;

                // 정지 횟수 초기화
                stayCount = 0;
                // 방문 횟수 증가
                count++;
                continue;
            }

            // 이동하지 못한 경우 카운트
            stayCount++;

            // 현재 위치에서 네 방향 모두 이동하지 못했을 때 (바다 또는 이동했던 곳인 경우)
            if (stayCount == 4) {

                // 바라보는 방향을 기준으로 뒷칸으로 이동가능 여부 확인
                nextRow = curr[0] - MOVE_STEP[d][0];
                nextCol = curr[1] - MOVE_STEP[d][1];
                if (mapGrid[nextRow][nextCol] != LAND) {
                    // 이동하지 못할 경우 탈출
                    break;
                }

                curr[0] = nextRow;
                curr[1] = nextCol;
                // 정지 횟수 초기화
                stayCount = 0;
                // 방문 횟수 증가
                count++;
            }
        }

        System.out.println(count);
    }

    // 해당 방향을 기준으로 왼쪽(반 시계방향 90도)으로 전환
    private static int changeDirection(final int d) {
        if (d == 0) {
            return 3;
        }
        return d - 1;
    }
}
