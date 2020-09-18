package com.jaenyeong.chapter_09_shortest_path;

import java.util.Arrays;
import java.util.Scanner;

public class Question04 {
    /*
    [Question]
    미래 도시

    [Input]
    1 line > 5 7
    2 line > 1 2
    3 line > 1 3
    4 line > 1 4
    5 line > 2 4
    6 line > 3 4
    7 line > 3 5
    8 line > 4 5
    9 line > 4 5
    [Output]
    > 3

    [Input]
    1 line > 4 2
    2 line > 1 3
    3 line > 2 4
    4 line > 3 4
    [Output]
    > -1

     */

    // 10억을 무한대 변수값으로 설정
    private static final int INF = (int) 1e9;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 전체 회사의 개수 (1 <= n <= 100), 경로의 개수 (1 <= m <= 100)
        // 2번째 라인부터 m + 1번째 라인 : 연결된 두 회사의 번호
        // m + 2번째 라인 : 최종 도착지 , 선행 경유지 (1 <= k <= 100)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("전체 회사의 개수, 경로의 개수를 입력하세요");
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        scanner.nextLine();

        final int[][] graph = initializeGraph(scanner, n, m);

        System.out.println("최종 도착지와 선행 경유지를 입력하세요");
        final int x = scanner.nextInt();
        final int k = scanner.nextInt();

        floydWarshall(graph, n);

        printShortestDistance(graph, k, x);
    }

    private static int[][] initializeGraph(final Scanner scanner, final int n, final int m) {
        final int size = n + 1;
        final int[][] graph = new int[size][size];

        Arrays.stream(graph).forEach(g -> Arrays.fill(g, INF));

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
            final int aNode = scanner.nextInt();
            final int bNode = scanner.nextInt();
            scanner.nextLine();

            // 간선 존재시 양방향 이동 가능
            graph[aNode][bNode] = 1;
            graph[bNode][aNode] = 1;
        }

        return graph;
    }

    private static void floydWarshall(final int[][] graph, final int n) {
        for (int k = 1; k <= n; k++) {
            for (int a = 1; a <= n; a++) {
                for (int b = 1; b <= n; b++) {
                    graph[a][b] = Math.min(graph[a][b], (graph[a][k] + graph[k][b]));
                }
            }
        }
    }

    private static void printShortestDistance(final int[][] graph, final int route, final int destination) {
        final int distance = graph[1][route] + graph[route][destination];
        System.out.println(distance >= INF ? -1 : distance);
    }
}
