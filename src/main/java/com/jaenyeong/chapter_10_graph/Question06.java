package com.jaenyeong.chapter_10_graph;

import java.util.Scanner;
import java.util.StringJoiner;

public class Question06 {
    /*
    [Question]
    팀 결성

    [Input]
    1 line > 7 8
    2 line > 0 1 3
    3 line > 1 1 7
    4 line > 0 7 6
    5 line > 1 7 1
    6 line > 0 3 7
    7 line > 0 4 2
    8 line > 0 1 1
    9 line > 1 1 1
    [Output]
    1 line > NO
    2 line > NO
    3 line > YES

     */

    private static final int OPERATOR = 0;
    private static final int FIRST_MEMBER = 1;
    private static final int SECOND_MEMBER = 2;

    private static final StringJoiner RESULT = new StringJoiner("\n");

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 마지막 학생의 번호 (n), 연산의 개수(m)
        // 2번째 라인 이후 : 각각의 연산 (연산의 종류, a, b)
        //                 : 0 or 1, a, b (a, b는 n 이하의 양의 정수)
        //                 : 0, a, b (a번 학생이 속한 팀과 b번 학생이 속한 팀을 합침)
        //                 : 1, a, b (a번 학생과 b번 학생이 같은 팀에 속해 있는지 확인)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("마지막 학생의 번호와 연산의 개수를 입력하세요");
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        scanner.nextLine();

        // 팀 테이블 초기화
        final int[] teamTable = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            teamTable[i] = i;
        }

        System.out.println("각 연산을 입력하세요");

        for (int i = 0; i < m; i++) {
            System.out.println((i + 1) + "번째 연산 입력");
            String[] input = scanner.nextLine().split(" ");

            // 입력 받은 데이터
            final int operator = Integer.parseInt(input[OPERATOR]);
            final int firstMember = Integer.parseInt(input[FIRST_MEMBER]);
            final int secondMember = Integer.parseInt(input[SECOND_MEMBER]);

            // 각 연산 수행
            execute(teamTable, operator, firstMember, secondMember);
        }

        // 결과 출력
        System.out.println(RESULT);
    }

    private static void execute(final int[] teamTable, final int operator, final int firstMember, final int secondMember) {
        // 해당 멤버들의 소속팀 찾기
        final int firstTeam = findTeam(teamTable, firstMember);
        final int secondTeam = findTeam(teamTable, secondMember);

        // 합치기 연산 수행
        if (operator == 0) {
            unionTeam(teamTable, firstTeam, secondTeam);
        }
        // 같은 팀 여부 확인
        else if (operator == 1) {
            // 여부 반환
            RESULT.add((firstTeam == secondTeam) ? "YES" : "NO");
        }
        // 그 외 명령은 프로그램 종료
        else {
            System.out.println("[연산 번호 오류] 프로그램을 종료합니다");
            System.exit(0);
        }
    }

    private static void unionTeam(final int[] teamTable, final int firstTeam, final int secondTeam) {
        if (firstTeam > secondTeam) {
            teamTable[firstTeam] = secondTeam;
        } else {
            teamTable[secondTeam] = firstTeam;
        }
    }

    private static int findTeam(final int[] teamTable, final int member) {
        if (teamTable[member] != member) {
            teamTable[member] = findTeam(teamTable, teamTable[member]);
        }

        return teamTable[member];
    }
}
