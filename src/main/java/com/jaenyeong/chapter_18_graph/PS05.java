package com.jaenyeong.chapter_18_graph;

import java.util.*;

public class PS05 {
    /*
    [Question]
    최종 순위

    [Input]
    01 line > 3
    02 line > 5
    03 line > 5 4 3 2 1
    04 line > 2
    05 line > 2 4
    06 line > 3 4
    07 line > 3
    08 line > 2 3 1
    09 line > 0
    10 line > 4
    11 line > 1 2 3 4
    12 line > 3
    13 line > 1 2
    14 line > 3 4
    15 line > 2 3
    [Output]
    1 line > 5 3 2 4 1
    2 line > 2 3 1
    3 line > IMPOSSIBLE

    [입력 조건]
    1 line > 테스트 케이스의 수 (t <= 100)
    2 line > 팀의 수 (2 <= n <= 500)
    3 line > 각 팀의 작년도 성적 순위 (1 <= ti <= n)
    4 line > 상대적인 등수가 바뀐 쌍의 수 (0 <= m <= 25000)
    5 ~ (4 + m) line > 상대적인 등수가 바뀐 두 팀 (1 <= a < b <= n)
    after line > 위와 동일하게 반복

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final String NOT_FOUND = "?";
    private static final String IMPOSSIBLE = "IMPOSSIBLE";

    public static void main(String[] args) {
        System.out.println("테스트 케이스 수를 입력하세요");
        final int t = SC.nextInt();

        // 주어진 테스트 케이스 수만큼 반복
        for (int testCase = 0; testCase < t; testCase++) {
            System.out.println((testCase + 1) + "번째 테스트 케이스 팀의 수를 입력하세요");
            final int n = SC.nextInt();

            System.out.println("해당 팀의 작년도 성적 순위를 차례대로 입력하세요");
            final int[] ranking = new int[n];
            for (int i = 0; i < n; i++) {
                ranking[i] = SC.nextInt();
            }

            // 간선 정보를 담을 그래프 배열 초기화
            final boolean[][] edgeGraph = new boolean[n + 1][n + 1];
            // 진입 차수 테이블 초기화 (순위가 낮은 팀이 진입 차수가 높음)
            final int[] inDegrees = new int[n + 1];

            // 방향 그래프 간선 정보 초기화
            // row = higherTeam, col = lowerTeam
            for (int higher = 0; higher < (n - 1); higher++) {
                for (int lower = (higher + 1); lower < n; lower++) {
                    // row(higherTeam)를 기준으로 col(lowerTeam)이 더 낮은 순위인 경우
                    edgeGraph[ranking[higher]][ranking[lower]] = true;
                    // 순위가 낮은 팀의 진입 차수 증가
                    inDegrees[ranking[lower]]++;
                }
            }

            // 팀간 작년도 순위를 기준으로 올해 변경된 순위 정보와 비교하여 순위 (간선의 방향) 뒤집기
            System.out.println("상대적인 등수가 바뀐 팀 쌍의 수를 입력하세요");
            final int m = SC.nextInt();
            for (int i = 0; i < m; i++) {
                System.out.println((i + 1) + "번째 등수가 바뀐 팀들을 입력하세요");
                final int first = SC.nextInt();
                final int second = SC.nextInt();

                // 첫 번째 팀이 순위가 더 높았던 경우 두 번째 팀의 순위를 높임
                if (edgeGraph[first][second]) {
                    edgeGraph[first][second] = false;
                    edgeGraph[second][first] = true;
                    inDegrees[first]++;
                    inDegrees[second]--;
                    continue;
                }

                // 두 번째 팀이 순위가 더 높았던 경우 첫 번째 팀의 순위를 높임
                edgeGraph[first][second] = true;
                edgeGraph[second][first] = false;
                inDegrees[first]--;
                inDegrees[second]++;
            }

            // 위상 정렬(Topology Sort) 시작
            bookPS(ranking, edgeGraph, inDegrees);
        }
    }

    private static void bookPS(final int[] ranking, final boolean[][] edgeGraph, final int[] inDegrees) {
        final int n = ranking.length;

        // 알고리즘 수행 결과를 담을 리스트 초기화
        final List<Integer> result = new ArrayList<>();
        final Queue<Integer> rankQ = new LinkedList<>();

        // 처음 시작시 진입 차수가 0인 노드를 큐에 삽입
        for (int i = 1; i <= n; i++) {
            if (inDegrees[i] == 0) rankQ.offer(i);
        }

        // 위상 정렬 결과가 오직 하나인지 여부
        boolean check = true;
        // 그래프 내 사이클 존재 여부
        boolean cycle = false;

        // 정확히 팀의 수만큼 반복 (팀의 수와 반복 횟수가 다른 경우 사이클이 발생했다고 판단)
        for (int teamCnt = 0; teamCnt < n; teamCnt++) {
            // 큐가 비어 있는 경우 사이클이 발생했다고 판단
            if (rankQ.size() == 0) {
                cycle = true;
                break;
            }

            // 큐의 원소가 2개 이상인 경우 > 가능한 정렬 결과가 여러 개라고 판단
            if (rankQ.size() >= 2) {
                check = false;
                break;
            }

            // 큐에서 원소 꺼내기
            final int currTeam = rankQ.poll();
            result.add(currTeam);

            // 해당 팀과 연결된 팀들의 진입차수 감소 시키기 (1 빼기)
            for (int anotherTeam = 1; anotherTeam <= n; anotherTeam++) {
                // 연결 여부 확인
                if (edgeGraph[currTeam][anotherTeam]) {
                    inDegrees[anotherTeam]--;

                    // 새롭게 진입차수가 0이 되는 팀을 큐에 삽입
                    if (inDegrees[anotherTeam] == 0) {
                        rankQ.offer(anotherTeam);
                    }
                }
            }
        }

        // 사이클이 발생하는 경우 (일관성이 없는 경우)
        if (cycle) {
            System.out.println(IMPOSSIBLE);
            return;
        }

        // 위상 정렬 결과가 여러 개인 경우
        if (!check) {
            System.out.println(NOT_FOUND);
            return;
        }

        // 위상 정렬을 수행한 결과 출력
        StringJoiner sj = new StringJoiner(" ");
        for (int team : result) {
            sj.add(Integer.toString(team));
        }

        System.out.println(sj.toString());
    }
}
