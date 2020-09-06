package com.jaenyeong.chapter_04_implementation;

import java.util.Scanner;

public class Question03 {
    /*
    [Question]
    왕실의 나이트

    [Input]
    > a1
    [Output]
    > 2

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 각각 열과 행 (a <= col <= h), (1 <= row <= 8)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("나이트의 위치를 입력하세요");
        final String[] startingPoint = scanner.nextLine().split("");

        // 연산에 필요한 데이터 초기화
        final int row = Integer.parseInt(startingPoint[1]);
        final int col = startingPoint[0].charAt(0) - 'a' + 1;
        final int chessGridLimit = 8;
        final int[][] movePoint = {
            {-2, -1}, {-2, 1}, // 상
            {2, -1}, {2, 1},   // 하
            {-1, -2}, {1, -2}, // 좌
            {-1, 2}, {1, 2}    // 우
        };

        int count = 0;

        // 이동 가능한 좌표들을 반복하면서 현재 좌표 값에 더하여 이동
        for (int[] move : movePoint) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];

            // 이동한 좌표값이 유효한지 판단하여 횟수 증가
            if ((0 < nextRow) && (nextRow <= chessGridLimit)
                && (0 < nextCol) && (nextCol <= chessGridLimit)) {
                count++;
            }
        }

        System.out.println(count);
    }
}
