package com.jaenyeong.chapter_05_dfs_bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Question02 {
    /*
    [Question]
    BFS

    [Input]
    >
    [Output]
    >

     */

    public static void main(String[] args) {
        // 인접 리스트 선언
        List<List<Integer>> graphs = new ArrayList<>();

        // 초기화
        initGraphs(graphs);

        // 방문 처리 배열 선언
        boolean[] visited = new boolean[graphs.size()];

        bfs(graphs, 0, visited);
    }

    private static void initGraphs(List<List<Integer>> graphs) {
        // Node 1
        List<Integer> node1 = new ArrayList<>();
        node1.add(2);
        node1.add(3);
        node1.add(8);
        graphs.add(node1);

        // Node 2
        List<Integer> node2 = new ArrayList<>();
        node2.add(1);
        node2.add(7);
        graphs.add(node2);

        // Node 3
        List<Integer> node3 = new ArrayList<>();
        node3.add(4);
        node3.add(5);
        graphs.add(node3);

        // Node 4
        List<Integer> node4 = new ArrayList<>();
        node4.add(3);
        node4.add(5);
        graphs.add(node4);

        // Node 5
        List<Integer> node5 = new ArrayList<>();
        node5.add(3);
        node5.add(4);
        graphs.add(node5);

        // Node 6
        List<Integer> node6 = new ArrayList<>();
        node6.add(7);
        graphs.add(node6);

        // Node 7
        List<Integer> node7 = new ArrayList<>();
        node7.add(2);
        node7.add(6);
        node7.add(8);
        graphs.add(node7);

        // Node 8
        List<Integer> node8 = new ArrayList<>();
        node8.add(1);
        node8.add(7);
        graphs.add(node8);
    }

    private static void bfs(final List<List<Integer>> graphs, final int currIdx, final boolean[] visited) {
        // 인접한 노드를 순서대로 처리하기 위해 큐(링크드 리스트)를 사용
        Queue<Integer> bfsQueue = new LinkedList<>();
        // 인덱스 + 1을 값으로 삽입
        bfsQueue.offer(currIdx + 1);

        // 방문 처리
        visited[currIdx] = true;

        // 큐가 빌 때까지 반복
        while (!bfsQueue.isEmpty()) {
            int bfsValue = bfsQueue.poll();
            System.out.println("visit : " + bfsValue);

            // 큐에 삽입된 순서대로 꺼내 처리
            List<Integer> popNode = graphs.get(bfsValue - 1);

            // 꺼낸 노드에 인접 노드를 모두 반복하며 큐에 삽입 처리
            for (int popValue : popNode) {
                final int nextIdx = popValue - 1;
                if (!visited[nextIdx]) {
                    bfsQueue.offer(popValue);
                    // 인접 노드의 중복 삽입을 막기 위해 방문 처리
                    visited[nextIdx] = true;
                }
            }
        }
    }
}
