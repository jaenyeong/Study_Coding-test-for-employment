package com.jaenyeong.chapter_18_graph;

import java.util.*;

public class PS03 {
    /*
    [Question]
    어두운 길

    [Input]
    01 line > 7 11
    02 line > 0 1 7
    03 line > 0 3 5
    04 line > 1 2 8
    05 line > 1 3 9
    06 line > 1 4 7
    07 line > 2 4 5
    08 line > 3 4 15
    09 line > 3 5 6
    10 line > 4 5 8
    11 line > 4 6 9
    12 line > 5 6 11
    [Output]
    > 51

    [입력 조건]
    1 line > 집의 수 (1 <= n <= 200,000), 도로의 수 ((n - 1) <= m <= 200,000)
    2 ~ (1 + m) line > 각 도로(양방향)에 대한 정보 (X=첫 번째 집 번호, Y=두 번째 집 번호, Z=거리) (0 <= x, y <= n)
                       (sumAll(m.length) <= 2^31)
     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("집의 수, 도로의 수를 입력하세요");
        final int n = SC.nextInt();
        final int m = SC.nextInt();

        // 도로 정보 초기화
        final List<Load> darkLoads = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            System.out.println((i + 1) + "번째 도로 연결 정보를 입력하세요");
            final int from = SC.nextInt();
            final int to = SC.nextInt();
            final int cost = SC.nextInt();

            darkLoads.add(new Load(from, to, cost));
        }

        System.out.println(myPS(darkLoads, n));
    }

    private static int myPS(final List<Load> darkLoads, final int n) {
        final int[] costs = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            costs[i] = i;
        }

        // 최소 신장트리를 구현하기 위해 비용을 기준으로 오름차순 정렬
        final Queue<Load> loadsQ = new PriorityQueue<>(Comparator.comparingInt(Load::getCost));

        // 모든 도로의 가로등 비용 합계
        int sumCosts = 0;
        for (Load current : darkLoads) {
            final int from = current.getFrom();
            final int to = current.getTo();
            final int cost = current.getCost();

            // 우선순위 큐에 삽입
            loadsQ.offer(new Load(from, to, cost));

            sumCosts += cost;
        }

        // 큐에서 도로 정보를 하나씩 뽑아 연결 처리
        while (!loadsQ.isEmpty()) {
            if (loadsQ.peek() == null) break;

            final Load current = loadsQ.poll();

            final int from = current.getFrom();
            final int to = current.getTo();
            final int cost = current.getCost();

            final int fromRoot = findRoot(from, costs);
            final int toRoot = findRoot(to, costs);

            if (fromRoot == toRoot) {
                continue;
            }

            union(fromRoot, toRoot, costs);
            sumCosts -= cost;
        }

        return sumCosts;
    }

    private static int findRoot(final int target, final int[] costs) {
        costs[target] = (costs[target] == target) ? target : findRoot(costs[target], costs);

        return costs[target];
    }

    private static void union(final int fromRoot, final int toRoot, final int[] costs) {
        if (fromRoot < toRoot) {
            costs[toRoot] = fromRoot;
            return;
        }

        costs[fromRoot] = toRoot;
    }
}

class Load {
    private final int from;
    private final int to;
    private final int cost;

    public Load(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getCost() {
        return cost;
    }
}
