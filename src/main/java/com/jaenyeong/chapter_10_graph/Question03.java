package com.jaenyeong.chapter_10_graph;

import java.util.Scanner;

public class Question03 {
    /*
    [Question]
    서로소 집합을 활용한 사이클 판별 알고리즘

    [Input]
    1 line > 3 3
    2 line > 1 2
    3 line > 1 3
    4 line > 2 3
    [Output]
    > 사이클이 발생했습니다.

     */

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("노드의 개수, 간선의 개수를 입력하세요");
        final int v = scanner.nextInt();
        final int e = scanner.nextInt();
        scanner.nextLine();

        // 루트 노드 테이블 초기화
        final int[] rootTable = new int[v + 1];
        for (int i = 1; i <= v; i++) {
            rootTable[i] = i;
        }

        // 사이클 발생 여부
        boolean cycle = false;

        System.out.println("유니온 연산을 입력하세요");
        for (int i = 0; i < e; i++) {
            System.out.println((i + 1) + "번째 연산 입력");
            final int first = scanner.nextInt();
            final int second = scanner.nextInt();
            scanner.nextLine();

            final int firstRoot = findRoot(rootTable, first);
            final int secondRoot = findRoot(rootTable, second);

            if (firstRoot == secondRoot) {
                cycle = true;
                break;
            }

            // 사이클이 발생하지 않았다면 연산 수행
            unionRoot(rootTable, firstRoot, secondRoot);
        }

        System.out.println(cycle ? "사이클이 발생했습니다." : "사이클이 발생하지 않았습니다.");
    }

    private static void unionRoot(final int[] rootTable, final int firstRoot, final int secondRoot) {
        if (firstRoot < secondRoot) {
            rootTable[secondRoot] = firstRoot;
        } else {
            rootTable[firstRoot] = secondRoot;
        }
    }

    private static int findRoot(final int[] rootTable, final int element) {
        if (rootTable[element] != element) {
            rootTable[element] = findRoot(rootTable, rootTable[element]);
        }

        return rootTable[element];
    }
}
