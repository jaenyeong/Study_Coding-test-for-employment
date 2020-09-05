package com.jaenyeong.chapter_04_implementation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Question01 {
    /*
    [Question]
    상하좌우

    [Input]
    1 line > 5
    2 line > R R R U D D
    [Output]
    > 3 4

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 정사각형 공간 한 면의 크기 (1 <= n <= 100)
        // 2번째 라인 : 이동 계획서 (원소 L, R, U, D로 이루어진 배열)
        //            : 이동 횟수 (1 <= walk <= 100)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("정사각형 지도의 크기를 입력하세요");
        int mapSize = scanner.nextInt();
        scanner.nextLine();

        System.out.println("여행 계획서를 입력하세요 (상-U, 하-D, 좌-L, 우-R)");
        String[] planSteps = scanner.nextLine().toUpperCase().split(" ");

        System.out.println("mapSize: " + mapSize);
        System.out.println("walkPlan: " + Arrays.toString(planSteps));

        getFinalCoordinate(mapSize, planSteps);
    }

    private static void getFinalCoordinate(final int mapSize, final String[] planSteps) {
        final Map<String, int[]> directions = new HashMap<>();
        directions.put("U", new int[]{-1, 0});
        directions.put("D", new int[]{1, 0});
        directions.put("L", new int[]{0, -1});
        directions.put("R", new int[]{0, 1});

        // 첫 시작 포지션은 {1, 1}
        int x = 1; // 행
        int y = 1; // 열

        for (String step : planSteps) {
            // 좌표값이 유효한지 확인할 이동 후 x, y 값
            int nextX = x;
            int nextY = y;

            // plan에 있는 방향과 일치하는 좌표 값을 더함
            for (Map.Entry<String, int[]> direction : directions.entrySet()) {
                if (direction.getKey().equals(step)) {
                    nextX = x + direction.getValue()[0];
                    nextY = y + direction.getValue()[1];
                }
            }

            // 주어진 공간 범위를 벗어나는 경우 이동하지 않음
            if ((1 > nextX) || (nextX > mapSize) || (1 > nextY) || (nextY > mapSize)) {
                continue;
            }

            x = nextX;
            y = nextY;
        }

        System.out.printf("%d %d \n", x, y);
    }
}
