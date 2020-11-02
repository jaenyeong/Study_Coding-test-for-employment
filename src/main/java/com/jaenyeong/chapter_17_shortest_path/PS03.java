package com.jaenyeong.chapter_17_shortest_path;

import java.util.*;

public class PS03 {
    /*
    [Question]
    화성 탐사

    [Input]
    01 line > 3
    02 line > 3
    03 line > 5 5 4
    04 line > 3 9 1
    05 line > 3 2 7
    06 line > 5
    07 line > 3 7 2 0 1
    08 line > 2 8 0 9 1
    09 line > 1 2 1 8 1
    10 line > 9 8 9 2 0
    11 line > 3 6 5 1 5
    12 line > 7
    13 line > 9 0 5 1 1 5 3
    14 line > 4 1 2 1 6 5 3
    15 line > 0 7 6 1 6 8 5
    16 line > 1 1 7 8 3 2 3
    17 line > 9 4 0 7 6 4 1
    18 line > 5 8 3 2 4 8 3
    19 line > 7 4 8 4 8 3 4
    [Output]
    1 line > 20
    2 line > 19
    3 line > 36

    [입력 조건]
    1 line > 테스트 케이스 수 (1 <= t <= 10)
    2 line > 탐사 공간의 크기 (2 <= n <= 125)
    3 ~ (2 + n) line > 탐사 공간의 행별 비용 정보 (0 <= cost <= 9)
    after line > 위와 동일하게 탐사 공간의 크기와 탐사 공간의 행별 비용 정보

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int INF = (int) 1e9;

    // 상하좌우
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) {
        System.out.println("테스트 수를 입력하세요");
        final int t = SC.nextInt();

        final int[] result = new int[t];

        for (int testCase = 0; testCase < t; testCase++) {
            System.out.println((testCase + 1) + "번째 탐사 공간 크기를 입력하세요");
            final int marsSize = SC.nextInt();

            final int[][] mars = new int[marsSize][marsSize];

            for (int x = 0; x < marsSize; x++) {
                System.out.println((x + 1) + "번째 행별 비용 정보를 입력하세요");
                for (int y = 0; y < marsSize; y++) {
                    mars[x][y] = SC.nextInt();
                }
            }

            result[testCase] = bookPS(mars, marsSize);
        }

        // 결과 출력
        for (int i = 0; i < t; i++) {
            System.out.println(result[i]);
        }
    }

    private static int bookPS(final int[][] mars, final int marsSize) {
        // 화성 최단 거리 테이블 초기화
        final int[][] marsDP = new int[marsSize][marsSize];
        Arrays.stream(marsDP).forEach(m -> Arrays.fill(m, INF));

        // 거리를 기준으로 오름차순 정렬된 큐 초기화 (BFS)
        final Queue<Space> marsQ = new PriorityQueue<>(Comparator.comparingInt(Space::getDistance));

        // 시작 좌표 초기화
        int x = 0, y = 0;
        marsQ.offer(new Space(x, y, mars[x][y]));

        marsDP[x][y] = mars[x][y];

        // 데이크스트라 알고리즘 수행 Dijkstra
        while (!marsQ.isEmpty()) {
            if (marsQ.peek() == null) break;

            Space space = marsQ.poll();

            int dist = space.getDistance();
            x = space.getX();
            y = space.getY();

            // 이미 처리된 적 있는 경우
            if (marsDP[x][y] < dist) continue;

            // 해당 영역을 기준으로 상하좌우 방향으로 이동 거리 연산
            for (int[] dir : DIRECTIONS) {
                int nextX = x + dir[0];
                int nextY = y + dir[1];

                // 다음 영역의 유효범위 확인
                if ((marsSize <= nextX) || (nextX < 0) || (marsSize <= nextY) || (nextY < 0)) continue;

                final int sumDist = dist + mars[nextX][nextY];

                // 현재 영역을 거쳐 다른 영역으로 이동하는 거리가 더 짧은 경우
                if (sumDist < marsDP[nextX][nextY]) {
                    marsDP[nextX][nextY] = sumDist;
                    marsQ.offer(new Space(nextX, nextY, sumDist));
                }
            }
        }

        return marsDP[marsSize - 1][marsSize - 1];
    }
}

class Space {
    private final int x;
    private final int y;
    private final int distance;

    public Space(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }
}
