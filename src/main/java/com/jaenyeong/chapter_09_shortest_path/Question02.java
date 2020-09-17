package com.jaenyeong.chapter_09_shortest_path;

import java.util.*;

public class Question02 {
    /*
    [Question]
    개선된 다익스트라(데이크스트라)

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
        List<List<Node02>> graph = initializeGraph(scanner, n, m);

        // 최단 거리 기록 테이블 초기화 (인덱스를 값과 동일하게 사용하기 위해 + 1)
        final int[] distanceHistory = new int[n + 1];
        Arrays.fill(distanceHistory, INF);

        // 다익스트라 알고리즘으로 최단 거리 계산하여 최단 거리 기록 테이블에 저장
        dijkstra(graph, start, distanceHistory);

        // 모든 노드의 최단 거리 출력
        printShortestDistance(distanceHistory);
    }

    private static List<List<Node02>> initializeGraph(final Scanner scanner, final int n, final int m) {
        final List<List<Node02>> graph = new ArrayList<>();
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
            graph.get(aNode).add(new Node02(bNode, cost));
        }

        return graph;
    }

    private static void dijkstra(final List<List<Node02>> graph, final int start,
                                 final int[] distanceHistory) {
        // 우선순위 큐 초기화
        final Queue<Node02> queue = new PriorityQueue<>(Comparator.comparingInt(Node02::getDistance));
        // 시작 노드 삽입
        queue.offer(new Node02(start, 0));
        distanceHistory[start] = 0;

        // 우선순위 큐가 빌 때까지 반복
        while (!queue.isEmpty()) {
            Node02 node = queue.poll();

            final int current = node.getIndex();
            final int currDistance = node.getDistance();

            // 이미 처리된 적이 있는 경우 통과
            if (distanceHistory[current] < currDistance) {
                continue;
            }

            // 해당 노드에 인접한 노드를 반복하며 처리
            final List<Node02> popNode = graph.get(current);
            for (final Node02 linkedNode : popNode) {
                // 현재 노드까지의 거리 + 연결된 노드의 거리
                final int distance = distanceHistory[current] + linkedNode.getDistance();

                // 최단 거리 기록 테이블의 값과 현재 계산된 거리를 비교하여 더 작은 값 삽입
                if (distance < distanceHistory[linkedNode.getIndex()]) {
                    distanceHistory[linkedNode.getIndex()] = distance;
                    queue.offer(new Node02(linkedNode.getIndex(), distance));
                }
            }
        }
    }

    private static void printShortestDistance(final int[] distanceHistory) {
        Arrays.stream(distanceHistory).skip(1).forEach((d) -> System.out.println(d == INF ? "INFINITY" : d));
    }
}

class Node02 {
    private final int index;
    private final int distance;

    public Node02(int index, int distance) {
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
