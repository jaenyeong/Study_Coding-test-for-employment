package com.jaenyeong.chapter_10_graph;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

public class Question01 {
    /*
    [Question]
    서로소 집합 알고리즘

    [Input]
    1 line > 6 4
    2 line > 1 4
    3 line > 2 3
    4 line > 2 4
    5 line > 5 6
    [Output]
    1 line > 1 1 1 1 5 5
    2 line > 1 1 2 1 5 5

     */

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("노드의 개수, 간선의 개수를 입력하세요");
        final int v = scanner.nextInt();
        final int e = scanner.nextInt();
        scanner.nextLine();

        // 부모 노드 테이블 초기화
        final int[] parent = new int[v + 1];
        for (int i = 1; i <= v; i++) {
            parent[i] = i;
        }

        System.out.println("유니온 연산을 입력하세요");
        for (int i = 0; i < e; i++) {
            System.out.println((i + 1) + "번째 연산 입력");
            final int first = scanner.nextInt();
            final int second = scanner.nextInt();
            scanner.nextLine();

            unionParent(parent, first, second);
        }

        // 각 원소가 속한 집합 출력
        StringJoiner unionStr = new StringJoiner(" ");
        Arrays.stream(parent).skip(1).forEach(el -> unionStr.add(Integer.toString(findParent(parent, el))));
        System.out.println(unionStr);

        // 부모 테이블 내용 출력
        StringJoiner parentStr = new StringJoiner(" ");
        Arrays.stream(parent).skip(1).forEach(el -> parentStr.add(Integer.toString(el)));
        System.out.println(parentStr);
    }

    // 두 원소가 속한 집합 합치기
    private static void unionParent(final int[] parent, final int first, final int second) {
        final int firstParent = findParent(parent, first);
        final int secondParent = findParent(parent, second);

        if (firstParent < secondParent) {
            parent[secondParent] = firstParent;
        } else {
            parent[firstParent] = secondParent;
        }
    }

    // 특정 원소가 속한 집합 찾기
    private static int findParent(final int[] parent, final int element) {
        // 해당 노드의 루트 노드가 자신인지 확인
        if (element == parent[element]) return element;

        // 루트 노드를 찾을 때까지 재귀 호출
        return findParent(parent, parent[element]);
    }
}
