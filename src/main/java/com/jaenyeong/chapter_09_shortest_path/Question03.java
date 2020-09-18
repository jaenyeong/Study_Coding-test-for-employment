package com.jaenyeong.chapter_09_shortest_path;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

public class Question03 {
    /*
    [Question]
    플로이드 워셜 알고리즘

    [Input]
    1 line > 4
    2 line > 7
    3 line > 1 2 4
    4 line > 1 4 6
    5 line > 2 1 3
    6 line > 2 3 7
    7 line > 3 1 5
    8 line > 3 4 4
    9 line > 4 3 2
    [Output]
    > 0 4 8 6
    > 3 0 7 9
    > 5 9 0 4
    > 7 11 2 0

     */

    // 10억을 무한대 변수값으로 설정
    private static final int INF = (int) 1e9;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 노드의 개수
        // 2번째 라인 : 간선의 개수
        // 3번째 라인 이후 : a, b, c (a번 노드에서 b번 노드로 가는 비용이 c)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("노드의 개수를 입력하세요");
        final int n = scanner.nextInt();
        scanner.nextLine();
        System.out.println("간선의 개수를 입력하세요");
        final int m = scanner.nextInt();
        scanner.nextLine();

        // 그래프 초기화
        final int[][] graph = initializeGraph(scanner, n, m);

        // 플로이드 워셜 알고리즘
        floydWarshall(graph, n);

        // 출력
        printShortestDistance(graph);
    }

    private static int[][] initializeGraph(final Scanner scanner, final int n, final int m) {
        final int size = n + 1;
        final int[][] graph = new int[size][size];

        // 최단 거리 테이블을 무한 값으로 초기화
        Arrays.stream(graph).forEach((g) -> Arrays.fill(g, INF));

        // 자신에게 이동하는 비용은 0으로 초기화
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                }
            }
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
            graph[aNode][bNode] = cost;
        }

        return graph;
    }

    private static void floydWarshall(final int[][] graph, final int n) {
        // 3중 반복문으로 플로이드 워셜 알고리즘 수행
        // k번(노드의 수만큼) 반복 (각 노드를 거치는 경우의 수)
        for (int k = 1; k <= n; k++) {
            // a 노드는 출발 노드
            for (int a = 1; a <= n; a++) {
                // b 노드는 도착 노드
                for (int b = 1; b <= n; b++) {
                    // a 노드에서 b 노드로 이동하는 최소 비용과
                    // a 노드에서 b 노드를 k 노드를 거쳐 이동하는 비용을 비교
                    graph[a][b] = Math.min(graph[a][b], (graph[a][k] + graph[k][b]));
                }
            }
        }
    }

    private static void printShortestDistance(final int[][] graph) {
        // 0번째 행과 열을 제외하고 최단거리 출력
        Arrays.stream(graph).skip(1).forEach(
            row -> {
                StringJoiner sj = new StringJoiner(" ");
                Arrays.stream(row).skip(1).forEach(
                    col -> sj.add(col == INF ? "INFINITY" : Integer.toString(col)));
                System.out.println(sj);
            });
    }
}
