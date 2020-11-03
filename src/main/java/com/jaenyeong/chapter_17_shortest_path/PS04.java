package com.jaenyeong.chapter_17_shortest_path;

import java.util.*;

public class PS04 {
    /*
    [Question]
    숨바꼭질

    [Input]
    1 line > 6 7
    2 line > 3 6
    3 line > 4 3
    4 line > 3 2
    5 line > 1 3
    6 line > 1 2
    7 line > 2 4
    8 line > 5 2
    [Output]
    > 4 2 3

    [입력 조건]
    1 line > 헛간의 개수 (2 <= n <= 20,000), 헛간을 연결하는 통로의 개수 (1 <= m <= 50,000)
    2 ~ (1 + m) line > 통로로 연결된 두 헛간 A, B (1 <= a <= n), (1 <= b <= n)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int INF = (int) 1e9;
    private static final int START = 1;

    public static void main(String[] args) {
        System.out.println("헛간의 개수와 헛간을 연결하는 통로의 개수를 입력하세요");
        final int n = SC.nextInt();
        final int m = SC.nextInt();

        // 입력된 헛간 데이터 초기화
        final List<List<Barn>> barns = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            barns.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            System.out.println((i + 1) + "번째 헛간의 연결 정보를 입력하세요");
            final int a = SC.nextInt();
            final int b = SC.nextInt();
            // 양방향통로이기 때문에 두번 삽입 처리
            barns.get(a).add(new Barn(b, 1));
            barns.get(b).add(new Barn(a, 1));
        }
        
        bookPS(barns, n);
    }

    private static void bookPS(final List<List<Barn>> barns, final int n) {
        // 최단 거리 테이블 초기화
        final int[] distances = new int[n + 1];
        Arrays.fill(distances, INF);

        // 데이크스트라 알고리즘 수행
        dijkstra(barns, distances);

        // 1번 헛간으로부터 최장 거리에 있는 헛간 찾기
        System.out.println(findLongestDistanceBarn(distances, n));
    }

    private static void dijkstra(final List<List<Barn>> barns, final int[] distances) {
        // 최장거리를 출발 헛간을 기준으로 순서대로 확인할 때 사용할 헛간 대기열 (우선순위 큐) 초기화
        final Queue<Barn> barnsQ = new PriorityQueue<>(Comparator.comparingInt(Barn::getDistance));
        // 출발할 1번 헛간 삽입
        barnsQ.offer(new Barn(START, 0));
        // 거리 테이블에 출발 헛간 거리 정보 초기화
        distances[START] = 0;

        while (!barnsQ.isEmpty()) {
            if (barnsQ.peek() == null) break;

            final Barn barn = barnsQ.poll();
            final int curIdx = barn.getIdx();
            final int curDistance = barn.getDistance();

            // 거리 테이블의 거리 정보와 데이터 비교
            if (distances[curIdx] < curDistance) {
                continue;
            }

            // 현재 헛간과 인접해 있는 헛간들을 순회하며 확인
            final List<Barn> linkedBarns = barns.get(curIdx);
            for (Barn link : linkedBarns) {
                final int linkIdx = link.getIdx();
                final int linkDistance = curDistance + link.getDistance();

                // 현재 인접한 헛간 정보가 거리 테이블에 이미 갱신되어 있는 경우
                if (linkDistance >= distances[linkIdx]) {
                    continue;
                }

                // 거리 테이블에 헛간 정보를 갱신
                distances[linkIdx] = linkDistance;
                // 헛간 대기열에 현재 인접한 헛간 추가
                barnsQ.offer(new Barn(linkIdx, linkDistance));
            }
        }
    }

    private static String findLongestDistanceBarn(final int[] distances, final int n) {
        // 최장 거리 리스트 초기화
        final List<Integer> longestDistances = new ArrayList<>();
        // 현재까지 확인한 헛간 중 최장 거리에 있는 헛간 순번
        int maxBarnIdx = 0;
        // 현재까지 확인한 헛간 중 최장 거리
        int maxDistance = 0;

        // 모든 헛간을 순회
        for (int i = 1; i <= n; i++) {
            // 캐싱한 최장 거리보다 거리 테이블에 현재 헛간 거리가 큰 경우
            if (maxDistance < distances[i]) {
                maxBarnIdx = i;
                maxDistance = distances[i];
                // 최장 거리 리스트 초기화 후 추가
                longestDistances.clear();
                longestDistances.add(maxBarnIdx);
                continue;
            }

            // 캐싱한 최장 거리와 거리 테이블에 현재 헛간 거리가 같은 경우 (리스트에 추가)
            if (maxDistance == distances[i]) longestDistances.add(i);
        }

        // 결과 반환 (숨을 헛간 (최소)번호, 숨을 헛간까지의 거리, 같은 거리를 같은 헛간 개수)
        StringJoiner result = new StringJoiner(" ");
        result.add(Integer.toString(maxBarnIdx))
            .add(Integer.toString(maxDistance))
            .add(Integer.toString(longestDistances.size()));

        return result.toString();
    }
}

class Barn {
    private final int idx;
    private final int distance;

    public Barn(int idx, int distance) {
        this.idx = idx;
        this.distance = distance;
    }

    public int getIdx() {
        return idx;
    }

    public int getDistance() {
        return distance;
    }
}
