package com.jaenyeong.chapter_10_graph;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class Question02 {
    /*
    [Question]
    경로 압축(Path Compression) 기법을 사용한 서로소 집합 알고리즘

    [Input]
    1 line > 6 4
    2 line > 1 4
    3 line > 2 3
    4 line > 2 4
    5 line > 5 6
    [Output]
    1 line > 1 1 1 1 5 5
    2 line > 1 1 1 1 5 5

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
        // 해당 원소들을 한번씩 호출하여 루트 노드를 찾아 부모 테이블을 바꿔주는 동작을 수행
        IntStream.range(1, parent.length).forEach(i -> unionStr.add(String.valueOf(findParent(parent, i))));
        System.out.println(unionStr);

        // 부모 테이블 내용 출력
        StringJoiner parentStr = new StringJoiner(" ");
        Arrays.stream(parent).skip(1).forEach(el -> parentStr.add(Integer.toString(el)));
        System.out.println(parentStr);
    }

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
        // 해당 노드의 부모 노드가 해당 노드가 아닌 경우
        if (parent[element] != element) {
            // 루트 노드를 찾아 부모 테이블에 삽입할 때까지 재귀 호출
            parent[element] = findParent(parent, parent[element]);
        }

        return parent[element];
    }
}
