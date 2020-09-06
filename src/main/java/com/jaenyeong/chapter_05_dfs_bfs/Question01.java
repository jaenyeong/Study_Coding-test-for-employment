package com.jaenyeong.chapter_05_dfs_bfs;

import java.util.ArrayList;
import java.util.List;

public class Question01 {
    /*
    [Question]
    DFS

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

        dfs(graphs, 0, visited);
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

    private static void dfs(final List<List<Integer>> graphs, final int currIdx, final boolean[] visited) {
        // 방문 처리
        visited[currIdx] = true;
        System.out.println("visit : " + (currIdx + 1));

        // 방문한 노드의 인접 노드 확인
        List<Integer> findNode = graphs.get(currIdx);
        for (int nextNode : findNode) {
            // 인접 리스트와 방문 배열 인덱스 처리를 위해 -1
            int nextIdx = nextNode - 1;

            if (!visited[nextIdx]) {
                // 인접한 노드를 재귀 호출로 방문
                dfs(graphs, nextIdx, visited);
            }
        }
    }
}
