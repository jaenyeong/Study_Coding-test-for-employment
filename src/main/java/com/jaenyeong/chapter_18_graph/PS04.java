package com.jaenyeong.chapter_18_graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PS04 {
    /*
    [Question]
    행성 터널

    [Input]
    1 line > 5
    2 line > 11 -15 -15
    3 line > 14 -5 -15
    4 line > -1 -1 -5
    5 line > 10 -4 -1
    6 line > 19 -4 19
    [Output]
    > 4

    [입력 조건]
    1 line > 행성의 개수 (1 <= n <= 100,000)
    2 ~ (1 + n) line > 각 행성의 x, y, z 좌표 (-10^9 <= x,y,z <= 10^9)
                       행성은 중복된 위치에 존재할 수 없음

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("행성의 개수를 입력하세요");
        final int n = SC.nextInt();

        // 행성의 각 좌표 별 리스트 초기화
        final List<Planet> xList = new ArrayList<>();
        final List<Planet> yList = new ArrayList<>();
        final List<Planet> zList = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            System.out.println((i) + "번째 행성의 좌표 정보를 입력하세요");
            final int x = SC.nextInt();
            final int y = SC.nextInt();
            final int z = SC.nextInt();

            xList.add(new Planet(x, i));
            yList.add(new Planet(y, i));
            zList.add(new Planet(z, i));
        }

        System.out.println(bookPS(n, xList, yList, zList));
    }

    private static int bookPS(final int n, final List<Planet> xList, final List<Planet> yList, final List<Planet> zList) {
        // 각 좌표 리스트 정렬
        xList.sort(Comparator.comparingInt(Planet::getX));
        yList.sort(Comparator.comparingInt(Planet::getX));
        zList.sort(Comparator.comparingInt(Planet::getX));

        // 모든 간선을 담을 리스트 초기화
        final List<Edge> edges = new ArrayList<>();

        // 인접한 행성들로부터 간선 정보를 추출하여 처리
        for (int i = 0; i < n - 1; i++) {
            inputEdge(i, xList, edges);
            inputEdge(i, yList, edges);
            inputEdge(i, zList, edges);
        }

        // 간선 리스트 정렬
        edges.sort(Comparator.comparingInt(Edge::getDistance));

        // 루트 테이블 초기화
        final int[] planetsRoot = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            planetsRoot[i] = i;
        }

        // 최종 터널 비용
        int resultCost = 0;

        // 모든 간선 확인
        for (Edge e : edges) {
            final int currCost = e.getDistance();
            final int first = e.getFirstPlanet();
            final int second = e.getSecondPlanet();

            final int firstRoot = findRoot(first, planetsRoot);
            final int secondRoot = findRoot(second, planetsRoot);
            if (firstRoot == secondRoot) continue;

            // 사이클이 발생하지 않는 경우에만 집합에 포함
            unionPlanet(firstRoot, secondRoot, planetsRoot);
            resultCost += currCost;
        }

        return resultCost;
    }

    private static void inputEdge(final int idx, final List<Planet> positionList, final List<Edge> edges) {
        final Planet nextPlanet = positionList.get(idx + 1);
        final Planet currPlanet = positionList.get(idx);

        final int distance = nextPlanet.getX() - currPlanet.getX();
        final int currY = currPlanet.getY();
        final int nextY = nextPlanet.getY();

        edges.add(new Edge(distance, currY, nextY));
    }

    private static int findRoot(final int target, final int[] planetsRoot) {
        planetsRoot[target] = (planetsRoot[target] == target) ? target : findRoot(planetsRoot[target], planetsRoot);

        return planetsRoot[target];
    }

    private static void unionPlanet(final int firstRoot, final int secondRoot, final int[] planetsRoot) {
        if (firstRoot < secondRoot) {
            planetsRoot[secondRoot] = firstRoot;
            return;
        }

        planetsRoot[firstRoot] = secondRoot;
    }
}

class Planet {
    private final int x;
    private final int y;

    public Planet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Edge {
    private final int distance;
    private final int firstPlanet;
    private final int secondPlanet;

    public Edge(int distance, int firstPlanet, int secondPlanet) {
        this.distance = distance;
        this.firstPlanet = firstPlanet;
        this.secondPlanet = secondPlanet;
    }

    public int getDistance() {
        return distance;
    }

    public int getFirstPlanet() {
        return firstPlanet;
    }

    public int getSecondPlanet() {
        return secondPlanet;
    }
}
