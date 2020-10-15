package com.jaenyeong.chapter_13_dfs_bfs;

import java.util.*;

public class PS01 {
    /*
    [Question]
    특정 거리의 도시 찾기

    [Input]
    1 line > 4 4 2 1
    2 line > 1 2
    3 line > 1 3
    4 line > 2 3
    5 line > 2 4
    [Output]
    > 4

    [Input]
    1 line > 4 3 2 1
    2 line > 1 2
    3 line > 1 3
    4 line > 1 4
    [Output]
    > -1

    [Input]
    1 line > 4 4 1 1
    2 line > 1 2
    3 line > 1 3
    4 line > 2 3
    5 line > 2 4
    [Output]
    1 line > 2
    2 line > 3

    [입력 조건]
    1 line > 도시의 개수 (2 <= n <= 300,000), 도로의 개수 (1 <= m <= 1,000,000)
             거리 정보 (1 <= k <= 300,000), 출발 도시의 번호 (1 <= x <= n)
    2 ~ (1 + m) line > a -> b를 의미하는 도시 간 단방향 도로 정보 (1 <= a, b <= n)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int FAIL = -1;
    private static final int INF = (int) 1e9;

    public static void main(String[] args) {
        System.out.println("도시의 개수, 도로의 개수, 거리 정보, 출발 도시의 번호를 차례대로 입력하세요");
        // 도시의 개수
        final int n = SC.nextInt();
        // 도로의 개수
        final int m = SC.nextInt();
        // 거리 정보
        final int k = SC.nextInt();
        // 출발 도시의 번호
        final int x = SC.nextInt();

        // 각 도시간 도로 연결 정보 초기화
        final List<List<Integer>> linkLoadInfos = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            linkLoadInfos.add(new ArrayList<>());
        }

        System.out.println("도시 간 연결된 단방향 도로 정보를 입력하세요");
        for (int i = 0; i < m; i++) {
            final int start = SC.nextInt();
            final int destination = SC.nextInt();
            SC.nextLine();

            // 해당 도시의 도로 연결 정보 삽입
            linkLoadInfos.get(start).add(destination);
        }

        myPS(n, m, k, x, linkLoadInfos);
    }

    private static void myPS(final int n, final int m, final int goalDistance, final int targetCity, final List<List<Integer>> linkLoadInfos) {
        // 각 도시간 연결 거리 테이블 초기화 (1번부터 n번 까지의 도시가 존재)
        final int[] distanceTable = new int[n + 1];

        // 최초에 모든 도시간 연결 도로가 없다고 가정 (무한대로 마킹)
//        Arrays.fill(distanceTable, INF);

        // 위 메서드 시간 초과로 인하여 for 문 사용
        for (int i = 1; i <= n; i++) {
            distanceTable[i] = INF;
        }

        // 각 도시는 자신의 도시로의 이동 거리는 0이라고 설정
        distanceTable[targetCity] = 0;

        // 주어진 도로 연결 정보를 통해 이동계획 생성
        Queue<Integer> movePlans = new LinkedList<>();
        movePlans.offer(targetCity);

        // 이동 계획으로 이동 거리 테이블에 연결된 각 도시간 이동 거리 마킹
        while (!movePlans.isEmpty()) {
            int startCity = movePlans.poll();

            final List<Integer> linkCities = linkLoadInfos.get(startCity);
            for (int destination : linkCities) {
                final int distance = distanceTable[destination];

                // 해당 목적지로 이동한 적이 없던 경우
                if (distance == INF) {
                    // 최초 출발 도시에서 현재 출발 도시까지의 거리 + 1 값을 목적지 도시 이동 거리에 삽입
                    distanceTable[destination] = distanceTable[startCity] + 1;
                    // 도착한 도시를 이동계획에 추가
                    movePlans.offer(destination);
                }
            }
        }

        // 조건에 맞는 도로 정보 리스트 초기화
        final List<Integer> validLoadInfos = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            final int distance = distanceTable[i];

            if (distance == goalDistance) {
                validLoadInfos.add(i);
            }
        }

        // 일치하는 이동 거리의 도시가 없다면 FAIL 출력
        if (validLoadInfos.size() == 0) {
            System.out.println(FAIL);
        } else {
            validLoadInfos.forEach(System.out::println);
        }
    }
}
