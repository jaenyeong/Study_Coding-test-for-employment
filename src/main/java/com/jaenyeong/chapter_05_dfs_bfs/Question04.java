package com.jaenyeong.chapter_05_dfs_bfs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Question04 {
    /*
    [Question]
    미로 탈출

    [Input]
    1 line > 5 6
    2 line > 101010
    3 line > 111111
    4 line > 000001
    5 line > 111111
    6 line > 111111
    [Output]
    > 10

     */

    private static int[][] mazeGrid;
    private static int n;
    private static int m;
    private static final int IS_BEAST = 0;

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 행-세로크기 (4 <= n), 열-가로크기 (m <= 200)
        // 2번째 라인 이후 : 맵의 괴물 유무 표시 (있음-0, 없음-1)
        //                 : 캐릭터의 시작 좌표는 (1, 1)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("맵의 크기를 입력하세요");
        n = scanner.nextInt();
        m = scanner.nextInt();
        scanner.nextLine();

        mazeGrid = new int[n][m];

        for (int i = 0; i < n; i++) {
            System.out.println("미로의 " + i + "번째 행을 입력하세요");
            mazeGrid[i] = Arrays.stream(scanner.nextLine().split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        }

        bfs(0, 0);
    }

    private static void bfs(int currentRow, int currentCol) {
        // 순서대로 처리하기 위해 큐 사용
        Queue<Node> queue = new LinkedList<>();

        // 현재 입력 값을 큐에 삽입
        queue.offer(new Node(currentRow, currentCol));

        // 큐가 빌때까지 반복
        while (!queue.isEmpty()) {
            Node popNode = queue.poll();
            currentRow = popNode.getRow();
            currentCol = popNode.getCol();

            // 현재 지점에서 상,하,좌,우 방향 확인
            for (int[] direction : DIRECTIONS) {
                int nextRow = currentRow + direction[0];
                int nextCol = currentCol + direction[1];

                // 다음 지역의 유효성 확인
                if (validateNextArea(nextRow, nextCol)) {
                    continue;
                }

                // 이동 가능한 길인지 확인 (괴물이 있으면 해당 경로 통과)
                if (mazeGrid[nextRow][nextCol] == IS_BEAST) {
                    continue;
                }

                // 해당 포인트를 처음 방문한 경우에만 거리 기록
                if (mazeGrid[nextRow][nextCol] == 1) {
                    // 다음 포인트로 이동하면서 이동한 거리값 삽입
                    mazeGrid[nextRow][nextCol] = mazeGrid[currentRow][currentCol] + 1;
                    queue.offer(new Node(nextRow, nextCol));
                }
            }
        }

        System.out.println(mazeGrid[n - 1][m - 1]);
    }

    private static boolean validateNextArea(int nextRow, int nextCol) {
        return (0 > nextRow) || (nextRow >= n) || (0 > nextCol) || (nextCol >= m);
    }
}

class Node {
    private final int row;
    private final int col;

    public Node(int row, int col) {
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
