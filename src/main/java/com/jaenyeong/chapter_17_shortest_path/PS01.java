package com.jaenyeong.chapter_17_shortest_path;

import java.util.*;

public class PS01 {
    /*
    [Question]
    플로이드

    [Input]
    01 line > 5
    02 line > 14
    03 line > 1 2 2
    04 line > 1 3 3
    05 line > 1 4 1
    06 line > 1 5 10
    07 line > 2 4 2
    08 line > 3 4 1
    09 line > 3 5 1
    10 line > 4 5 3
    11 line > 3 5 10
    12 line > 3 1 8
    13 line > 1 4 2
    14 line > 5 1 7
    15 line > 3 4 2
    16 line > 5 2 4
    [Output]
    1 line > 0 2 3 1 4
    2 line > 12 0 15 2 5
    3 line > 8 5 0 1 1
    4 line > 10 7 13 0 3
    5 line > 7 4 10 6 0

    [입력 조건]
    1 line > 도시의 개수 (1 <= n <= 100)
    2 line > 버스의 개수 (1 <= m <= 100,000)
    3 ~ (2 + m) line > 도시간 버스(노선) 정보 (a=starting b=destination c=cost)
                       이동 비용 (1 <= cost <= 100,000)
                       시작 도시와 도착 도시를 연결하는 버스(노선)은 하나가 아닐 수 있음

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int INF = (int) 1e9;

    public static void main(String[] args) {
        System.out.println("도시의 개수와 버스의 개수를 입력하세요");
        final int n = SC.nextInt();
        final int m = SC.nextInt();

        final List<List<Destination>> traffics = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            traffics.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            System.out.println((i + 1) + "번째 버스 정보를 입력하세요");
            final int start = SC.nextInt();
            final int destination = SC.nextInt();
            final int cost = SC.nextInt();

            traffics.get(start).add(new Destination(destination, cost));
        }

        myPS(traffics, n, m);
    }

    private static void myPS(final List<List<Destination>> traffics, final int n, final int m) {
        // DP 테이블 초기화 (초깃값 무한대)
        final int[][] dp = new int[n + 1][n + 1];
        Arrays.stream(dp).forEach(t -> Arrays.fill(t, INF));

        // 출발도시와 도착도시가 같은 경우는 버스 비용을 0으로 초기화
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == j) dp[i][j] = 0;
            }
        }

        // 모든 출발도시를 순회
        for (int start = 1; start <= n; start++) {
            final List<Destination> startCity = traffics.get(start);

            for (Destination dest : startCity) {
                final int destination = dest.getCityNo();
                final int cost = dest.getCost();

                // 버스 최소 비용
                dp[start][destination] = Math.min(dp[start][destination], cost);
            }
        }

        // 플로이드 워셜 알고리즘
        for (int path = 0; path <= n; path++) {
            for (int start = 0; start <= n; start++) {
                for (int dest = 0; dest <= n; dest++) {
                    dp[start][dest] = Math.min(dp[start][dest], dp[start][path] + dp[path][dest]);
                }
            }
        }

        // 도달할 수 없는 (INF) 영역을 0으로 채우기
        for (int start = 1; start <= n; start++) {
            for (int dest = 1; dest <= n; dest++) {
                if (dp[start][dest] == INF) dp[start][dest] = 0;
            }
        }

        // 결과 출력
        for (int start = 1; start <= n; start++) {
            StringJoiner sj = new StringJoiner(" ");
            for (int dest = 1; dest <= n; dest++) {
                sj.add(Integer.toString(dp[start][dest]));
            }
            System.out.println(sj);
        }
    }
}

class Destination {
    private final int cityNo;
    private final int cost;

    public Destination(int cityNo, int cost) {
        this.cityNo = cityNo;
        this.cost = cost;
    }

    public int getCityNo() {
        return cityNo;
    }

    public int getCost() {
        return cost;
    }
}
