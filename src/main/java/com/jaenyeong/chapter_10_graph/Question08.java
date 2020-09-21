package com.jaenyeong.chapter_10_graph;

import java.util.*;

public class Question08 {
    /*
    [Question]
    커리큘럼

    [Input]
    1 line > 5
    2 line > 10 -1
    3 line > 10 1 -1
    4 line > 4 1 -1
    5 line > 4 3 1 -1
    6 line > 3 3 -1
    [Output]
    1 line > 10
    2 line > 20
    3 line > 14
    4 line > 18
    5 line > 17

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 수강할 강의 수 (1 <= n <= 500)
        // 2번째 라인 이후 : (라인 번호 - 1) 번째 강의 시간 (100,000 이하의 자연수), 선수 강의 번호, -1
        //                 : -1은 라인의 끝을 의미
        final Scanner scanner = new Scanner(System.in);

        System.out.println("수강할 강의 개수를 입력하세요");
        final int n = scanner.nextInt();
        scanner.nextLine();

        // 진입차수 테이블 초기화
        final int[] inDegree = new int[n + 1];

        // 수강 시간 테이블 초기화
        final int[] times = new int[n + 1];

        // 강의 정보 입력 받아 강의 그래프 초기화
        final List<List<Integer>> lectures = initializeLectures(scanner, inDegree, times, n);

        // 위상 정렬
        final int[] result = topologySort(inDegree, times, lectures);

        // 출력
        Arrays.stream(result).skip(1).forEach(System.out::println);
    }

    private static List<List<Integer>> initializeLectures(final Scanner scanner, final int[] inDegree,
                                                          final int[] times, final int n) {
        // 강의 목록 초기화
        List<List<Integer>> lectures = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            lectures.add(new ArrayList<>());
        }

        System.out.println("강의 정보를 입력하세요");
        for (int i = 1; i <= n; i++) {
            System.out.println((i) + "번째 강의 정보 입력");

            // 강의 시간 테이블 삽입
            final int time = scanner.nextInt();
            times[i] = time;

            // -1이 나올때까지 반복하여 선수 과목 입력 받음
            while (true) {
                final int prerequisite = scanner.nextInt();
                if (prerequisite == -1) {
                    break;
                }

                // 진입차수 증가
                inDegree[i]++;
                // 선수 강의 번호에 해당 강의 번호 삽입
                lectures.get(prerequisite).add(i);
            }
        }

        return lectures;
    }

    private static int[] topologySort(final int[] inDegree, final int[] times,
                                      final List<List<Integer>> lectures) {
        // 반환할 결과 객체
        final int[] result = Arrays.copyOf(times, times.length);
        // 수강 큐 초기화
        final Queue<Integer> schedules = new LinkedList<>();
        final int size = inDegree.length;
        for (int i = 1; i < size; i++) {
            // 수강 큐에 선수 과목이 없는 강의 삽입
            if (inDegree[i] == 0) schedules.offer(i);
        }

        // 수강 큐가 빌 때까지 반복
        while (!schedules.isEmpty()) {
            // 수강 강의를 수강 큐에서 꺼내 다음 강의 목록 불러오기
            final int current = schedules.poll();
            final List<Integer> nextLectures = lectures.get(current);

            for (int next : nextLectures) {
                // 강의 시간 비교하여 더 큰 값 삽입
                // 강의 시간 테이블에 삽입된 시간 vs 현재까지 강의를 수강한 시간 + 다음 강의 시간
                result[next] = Math.max(result[next], result[current] + times[next]);
                // 진입차수 감소
                inDegree[next]--;

                // 다음 강의의 선수 강의를 모두 수강한 경우
                if (inDegree[next] == 0) {
                    schedules.offer(next);
                }
            }
        }

        return result;
    }
}
