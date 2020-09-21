package com.jaenyeong.chapter_10_graph;

import java.util.*;

public class Question05 {
    /*
    [Question]
    위상 정렬

    [Input]
    1 line > 7 8
    2 line > 1 2
    3 line > 1 5
    4 line > 2 3
    5 line > 2 6
    6 line > 3 4
    7 line > 4 7
    8 line > 5 6
    9 line > 6 4
    [Output]
    > 1 2 5 3 6 4 7

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 노드의 개수, 간선의 개수
        // 2번째 라인 이후 : a, b 간 간선 여부 (a번 노드에서 b번 노드가 간선으로 연결)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("노드의 개수와 간선의 개수를 입력하세요");
        final int v = scanner.nextInt();
        final int e = scanner.nextInt();
        scanner.nextLine();

        // 각 노드에 대한 진입차수 테이블
        final int[] inDegree = new int[v + 1];

        // 간선 그래프 초기화
        final List<List<Integer>> edges = initializeEdges(scanner, inDegree, v, e);

        // 위상 정렬
        final List<Integer> result = topologySort(inDegree, edges);

        // 결과 출력
        print(result);
    }

    private static List<List<Integer>> initializeEdges(final Scanner scanner, final int[] inDegree,
                                                       final int v, final int e) {
        final List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i <= v; i++) {
            edges.add(new ArrayList<>());
        }

        System.out.println("모든 간선 정보를 입력하세요");
        for (int i = 0; i < e; i++) {
            System.out.println((i + 1) + "번째 간선 정보 입력");
            final int a = scanner.nextInt();
            final int b = scanner.nextInt();

            // 간선 테이블에 추가
            edges.get(a).add(b);
            // 진입차수 테이블에 추가
            inDegree[b]++;
        }

        return edges;
    }

    private static List<Integer> topologySort(final int[] inDegree, final List<List<Integer>> edges) {
        // 위상 정렬 알고리즘 수행 결과를 담는 객체
        final List<Integer> result = new ArrayList<>();
        final Queue<Integer> plans = new LinkedList<>();

        // 진입 차수가 0인 노드를 큐에 삽입
        final int size = inDegree.length;
        for (int i = 1; i < size; i++) {
            if (inDegree[i] == 0) plans.offer(i);
        }

        // 정렬 알고리즘을 실행할 큐가 빌 때까지 반복
        while (!plans.isEmpty()) {
            // 실행 큐에서 노드를 순서대로 꺼내기
            final int popNode = plans.poll();

            // 결과에 삽입
            result.add(popNode);

            // 해당 노드에 연결 노드 리스트 가져오기
            List<Integer> linkedNode = edges.get(popNode);

            // 해당 노드에 연결된 노드를 반복하며 수행
            for (int link : linkedNode) {
                // 진입차수 테이블 값 빼기
                inDegree[link]--;
                // 진입차수 테이블 값이 0이 된 경우 실행 큐에 추가
                if (inDegree[link] == 0) {
                    plans.offer(link);
                }
            }
        }

        return result;
    }

    private static void print(final List<Integer> result) {
        StringJoiner sj = new StringJoiner(" ");
        result.forEach(i -> sj.add(String.valueOf(i)));
        System.out.println(sj);
    }
}
