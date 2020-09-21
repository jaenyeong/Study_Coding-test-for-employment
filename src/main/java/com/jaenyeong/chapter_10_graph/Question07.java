package com.jaenyeong.chapter_10_graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Question07 {
    /*
    [Question]
    도시 분할 계획

    [Input]
    01 line > 7 12
    02 line > 1 2 3
    03 line > 1 3 2
    04 line > 3 2 1
    05 line > 2 5 2
    06 line > 3 4 4
    07 line > 7 3 6
    08 line > 5 1 5
    09 line > 1 6 2
    10 line > 6 4 1
    11 line > 6 5 3
    12 line > 4 5 3
    13 line > 6 7 4
    [Output]
    > 8

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 집의 개수 (2 <= n <= 100,000), 길의 개수 (1 <= m <= 1,000,000)
        // 2번째 라인 이후 : a, b, c (a번 집과 b번 집을 연결하는 길의 유지비 c)
        //                 : (1 <= c <= 1,000)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("집의 개수와 길의 개수를 입력하세요");
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        scanner.nextLine();

        // 도로 비용 테이블 초기화
        final int[] costTable = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            costTable[i] = i;
        }

        // 도로 정보 초기화
        final List<Load> loadInfo = initializeLoadInfo(scanner, m);

        int result = 0;
        int lastCost = 0;

        // 최소 비용 순서대로 사이클을 피하며 도로 연결
        for (Load load : loadInfo) {
            final int cost = load.getCost();

            final int firstRoot = findRootHouse(costTable, load.getFirstHouse());
            final int secondRoot = findRootHouse(costTable, load.getSecondHouse());

            if (firstRoot != secondRoot) {
                unionHouse(costTable, firstRoot, secondRoot);
                result += cost;
                lastCost = cost;
            }
        }

        System.out.println(result - lastCost);
    }

    private static List<Load> initializeLoadInfo(final Scanner scanner, final int m) {
        final List<Load> loadInfo = new ArrayList<>();

        System.out.println("두 집 사이 길 정보를 입력하세요");
        for (int i = 0; i < m; i++) {
            System.out.println((i + 1) + "번째 도로 정보 입력");
            final int firstHouse = scanner.nextInt();
            final int secondHouse = scanner.nextInt();
            final int loadCost = scanner.nextInt();
            scanner.nextLine();

            loadInfo.add(new Load(firstHouse, secondHouse, loadCost));
        }

        // 도로 비용을 기준으로 오름차순 정렬
        loadInfo.sort(Comparator.comparingInt(Load::getCost));

        return loadInfo;
    }

    private static int findRootHouse(final int[] costTable, final int element) {
        if (costTable[element] != element) {
            costTable[element] = findRootHouse(costTable, costTable[element]);
        }

        return costTable[element];
    }

    private static void unionHouse(final int[] costTable, final int first, final int second) {
        if (first > second) {
            costTable[first] = second;
        } else {
            costTable[second] = first;
        }
    }
}

class Load {
    private final int firstHouse;
    private final int secondHouse;
    private final int cost;

    public Load(int firstHouse, int secondHouse, int cost) {
        this.firstHouse = firstHouse;
        this.secondHouse = secondHouse;
        this.cost = cost;
    }

    public int getFirstHouse() {
        return firstHouse;
    }

    public int getSecondHouse() {
        return secondHouse;
    }

    public int getCost() {
        return cost;
    }
}
