package com.jaenyeong.chapter_10_graph;

import java.util.*;

public class Question04 {
    /*
    [Question]
    크루스칼 알고리즘

    [Input]
    01 line > 7 9
    02 line > 1 2 29
    03 line > 1 5 75
    04 line > 2 3 35
    05 line > 2 6 34
    06 line > 3 4 7
    07 line > 4 6 23
    08 line > 4 7 13
    09 line > 5 6 53
    10 line > 6 7 25
    [Output]
    > 159

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 노드의 개수, 간선의 개수
        // 2번째 라인 이후 : a, b, c (a번 노드에서 b번 노드로 가는 비용이 c)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("노드의 개수와 간선의 개수를 입력하세요");
        final int v = scanner.nextInt();
        final int e = scanner.nextInt();
        scanner.nextLine();

        final int[] rootTable = new int[v + 1];
        for (int i = 1; i <= v; i++) {
            rootTable[i] = i;
        }

        final List<Edge> edges = initializeEdges(scanner, e);

        // 간선 기준으로 오름차순 정렬
        edges.sort(Comparator.comparingInt(Edge::getDistance));

        // 최소 신장 트리를 만드는데 필요한 최소 비용
        int result = 0;

        // 간선 반복
        for (Edge edge : edges) {
            final int nodeA = edge.getNodeA();
            final int nodeB = edge.getNodeB();
            final int distance = edge.getDistance();

            final int nodeARoot = findRoot(rootTable, nodeA);
            final int nodeBRoot = findRoot(rootTable, nodeB);

            if (nodeARoot != nodeBRoot) {
                unionRoot(rootTable, nodeARoot, nodeBRoot);
                result += distance;
            }
        }

        System.out.println(result);
    }

    private static List<Edge> initializeEdges(final Scanner scanner, final int e) {
        System.out.println("모든 간선 정보를 입력하세요");
        final List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < e; i++) {
            System.out.println((i + 1) + "번째 간선 정보 입력");
            final int a = scanner.nextInt();
            final int b = scanner.nextInt();
            final int distance = scanner.nextInt();
            scanner.nextLine();

            edges.add(new Edge(a, b, distance));
        }

        return edges;
    }

    private static int findRoot(final int[] rootTable, final int element) {
        if (rootTable[element] != element) {
            rootTable[element] = findRoot(rootTable, rootTable[element]);
        }

        return rootTable[element];
    }

    private static void unionRoot(final int[] rootTable, final int aRoot, final int bRoot) {
        if (aRoot < bRoot) {
            rootTable[bRoot] = aRoot;
        } else {
            rootTable[aRoot] = bRoot;
        }
    }
}

class Edge {
    private final int nodeA;
    private final int nodeB;
    private final int distance;

    public Edge(int nodeA, int nodeB, int distance) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.distance = distance;
    }

    public int getNodeA() {
        return nodeA;
    }

    public int getNodeB() {
        return nodeB;
    }

    public int getDistance() {
        return distance;
    }
}
