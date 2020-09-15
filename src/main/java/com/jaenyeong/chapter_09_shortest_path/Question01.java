package com.jaenyeong.chapter_09_shortest_path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Question01 {
    /*
    [Question]
    다익스트라(데이크스트라)

    [Input]
    01 line > 6 11
    02 line > 1
    03 line > 1 2 2
    04 line > 1 3 5
    05 line > 1 4 1
    06 line > 2 3 3
    07 line > 2 4 2
    08 line > 3 2 3
    09 line > 3 6 5
    10 line > 4 3 3
    11 line > 4 5 1
    12 line > 5 3 1
    13 line > 5 6 2

    [Output]
    > 0
      2
      3
      1
      2
      4

     */

    // 10억을 무한대 변수값으로 설정
    private static final int INF = (int) 1e9;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 노드의 개수 (1 <= n <= 100,000), 간선의 개수 (0 <= m <= 9,999,900,000)
        //            : 방향 그래프의 최대 간선 개수 = (n * (n - 1))
        //            : 무방향 그래프의 최대 간선 개수 = ((n * (n - 1)) / 2)
        // 2번째 라인 : 시작 노드 번호
        // 3번째 라인 이후 : a, b, c (a번 노드에서 b번 노드로 가는 비용이 c)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("노드의 개수와 간선의 개수를 입력하세요");
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        scanner.nextLine();

        System.out.println("시작 노드를 입력하세요");
        final int start = scanner.nextInt();
        scanner.nextLine();

        // 그래프(노드, 간선) 초기화
        List<List<Node01>> graph = initializeGraph(scanner, n, m);

        // 최단 거리 기록 테이블 초기화 (인덱스를 값과 동일하게 사용하기 위해 + 1)
        final int[] distanceHistory = new int[n + 1];
        Arrays.fill(distanceHistory, INF);

        // 방문 여부 테이블 초기화
        final boolean[] isVisited = new boolean[n + 1];

        // 다익스트라 알고리즘으로 최단 거리 계산하여 최단 거리 기록 테이블에 저장
        dijkstra(graph, start, distanceHistory, isVisited);

        // 모든 노드의 최단 거리 출력
        printShortestDistance(distanceHistory);
    }

    private static List<List<Node01>> initializeGraph(Scanner scanner, int n, int m) {
        final List<List<Node01>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        System.out.println("모든 간선 정보를 입력하세요");
        for (int i = 0; i < m; i++) {
            System.out.println("간선 " + m + "개 중 " + (i + 1) + "번째 간선 정보 입력");
            // a 노드 > b 노드 이동시 간선 비용 c
            final int aNode = scanner.nextInt();
            final int bNode = scanner.nextInt();
            final int cost = scanner.nextInt();
            scanner.nextLine();
            // 그래프 간선 정보 입력
            graph.get(aNode).add(new Node01(bNode, cost));
        }

        return graph;
    }

    private static void dijkstra(final List<List<Node01>> graph, final int start,
                                 final int[] distanceHistory, final boolean[] isVisited) {
        // 시작 노드의 최단거리, 방문 기록 초기화
        distanceHistory[start] = 0;
        isVisited[start] = true;

        // 시작 노드의 연결된 모든 노드를 돌며 최단 거리 초기화
        final List<Node01> linkedNodes = graph.get(start);
        for (final Node01 link : linkedNodes) {
            distanceHistory[link.getIndex()] = link.getDistance();
        }

        // 시작 노드를 제외한 전체 n - 1 개 노드의 대해 반복
        final int graphSize = graph.size();
        for (int i = 0; i < graphSize - 1; i++) {

            // 현재 최단 거리가 가/장 짧은 노드를 꺼내 방문 처리
            int popIndex = getSmallestNode(distanceHistory, isVisited, graphSize);
            isVisited[popIndex] = true;

            // 꺼낸 노드와 연결된 다른 노드 확인
            final List<Node01> popNode = graph.get(popIndex);
            for (final Node01 link : popNode) {
                // 현재 노드 거리 + 연결된 노드의 거리
                final int distance = distanceHistory[popIndex] + link.getDistance();

                // 최단 거리 기록 테이블의 값과 현재 계산된 거리를 비교하여 더 작은 값 삽입
                if (distance < distanceHistory[link.getIndex()]) {
                    distanceHistory[link.getIndex()] = distance;
                }
            }
        }
    }

    private static int getSmallestNode(final int[] distanceHistory, final boolean[] isVisited, final int graphSize) {
        int min = INF;
        int index = 0;

        // 그래프의 모든 노드를 돌며 확인
        for (int i = 0; i < graphSize; i++) {
            // 방문하지 않은 노드 중 최단 거리가 제일 짧은 노드의 번호를 반환
            if (!(isVisited[i]) && (distanceHistory[i] < min)) {
                min = distanceHistory[i];
                index = i;
            }
        }

        return index;
    }

    private static void printShortestDistance(final int[] distanceHistory) {
        Arrays.stream(distanceHistory).skip(1).forEach((d) -> System.out.println(d == INF ? "INFINITY" : d));
    }
}

class Node01 {
    private final int index;
    private final int distance;

    public Node01(int index, int distance) {
        this.index = index;
        this.distance = distance;
    }

    public int getIndex() {
        return index;
    }

    public int getDistance() {
        return distance;
    }
}
