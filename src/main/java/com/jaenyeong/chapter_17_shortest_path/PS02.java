package com.jaenyeong.chapter_17_shortest_path;

import java.util.Scanner;

public class PS02 {
    /*
    [Question]
    정확한 순위

    [Input]
    1 line > 6 6
    2 line > 1 5
    3 line > 3 4
    4 line > 4 2
    5 line > 4 6
    6 line > 5 2
    7 line > 5 4
    [Output]
    > 1

    [입력 조건]
    1 line > 학생들의 수 (2 <= n <= 500), 학생간 성적을 비교한 횟수 (2 <= m <= 10,000)
    2 ~ (1 + m) line > 두 학생의 성적을 비교한 결과 (a < b)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int INF = (int) 1e9;
    private static final int CHECK_SCORE = 1;

    public static void main(String[] args) {
        System.out.println("학생들의 수와 학생간 성적을 비교한 횟수를 입력하세요");
        final int n = SC.nextInt();
        final int m = SC.nextInt();

        // DP 테이블 초기화
        final int[][] scores = new int[n + 1][n + 1];
        for (int winner = 1; winner <= n; winner++) {
            for (int loser = 1; loser <= n; loser++) {
                // 타인과 비교하는 경우
                if (winner != loser) {
                    // 무한대로 초기화 (자기 자신인 경우 0으로 초기화)
                    scores[winner][loser] = INF;
                }
            }
        }

        // 성적 비교 입력 처리
        for (int i = 0; i < m; i++) {
            System.out.println((i + 1) + "번째 성적 비교 결과를 입력하세요");
            final int loser = SC.nextInt();
            final int winner = SC.nextInt();

            scores[winner][loser] = CHECK_SCORE;
        }

        System.out.println(bookPS(scores, n));
    }

    private static int bookPS(final int[][] scores, final int n) {
        // 플로이드 워셜 알고리즘 수행
        for (int mid = 1; mid <= n; mid++) {
            for (int winner = 1; winner <= n; winner++) {
                for (int loser = 1; loser <= n; loser++) {
                    scores[winner][loser] = Math.min(scores[winner][loser], scores[winner][mid] + scores[mid][loser]);
                }
            }
        }

        int isNotCompare = 0;
        for (int winner = 1; winner <= n; winner++) {
            int students = 0;
            for (int loser = 1; loser <= n; loser++) {
                // 점수 비교를 한 경우 (무한대가 아닌 경우)
                if (scores[winner][loser] != INF || scores[loser][winner] != INF) {
                    students++;
                }
            }

            if (students == n) isNotCompare++;
        }

        return isNotCompare;
    }
}
