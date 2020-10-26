package com.jaenyeong.chapter_14_sort;

import java.util.*;

public class PS03 {
    /*
    [Question]
    실패율

    [Input]
    1 line > 5
    2 line > 2 1 2 6 2 4 3 3
    [Output]
    > [3 4 2 1 5]

    [Input]
    1 line > 4
    2 line > 4 4 4 4 4
    [Output]
    > [4 1 2 3]

    [입력 조건]
    1 line > 스테이지의 개수 (1 <= n <= 500)
    2 line > 각 사용자가 도전 중인 스테이지 번호 (1 <= 사용자 개수 <= (n + 1))
             각 스테이지 번호 길이 (1 <= stages.length <= 200,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("스테이지의 수를 입력하세요");
        final int n = SC.nextInt();

        SC.nextLine();

        System.out.println("각 사용자가 도전중인 스테이지 번호를 입력하세요");
        final int[] stageHistory = Arrays.stream(SC.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        System.out.println(Arrays.toString(bookPS(stageHistory, n)));
    }

    private static int[] bookPS(final int[] stageHistory, final int n) {
        // 스테이지 실패 리스트 초기화
        final List<Stage> failList = new ArrayList<>();

        // 해당 스테이지를 성공한 인원
        int successUsers = stageHistory.length;

        // 스테이지 수 만큼 반복
        for (int levelOfStage = 1; levelOfStage <= n; levelOfStage++) {
            // 해당 스테이지를 실패한 인원
            int failUsers = 0;

            // 주어진 배열을 순회
            for (int j : stageHistory) {
                // 해당 스테이지를 클리어 하지 못한 인원 카운트
                if (levelOfStage == j) {
                    failUsers++;
                }
            }

            // 실패율 초기화 (해당 스테이지의 도달한 인원이 없는 경우 실패율 0으로 가정)
            double rateOfFail = 0;

            // 성공한 인원이 1명 이상인 경우
            if (successUsers >= 1) {
                // 실패율 계산 (해당 스테이지를 실패한 인원 / 해당 스테이지를 성공한 모든 인원)
                rateOfFail = (double) failUsers / successUsers;
            }

            // 상위 스테이지 레벨 계산을 위해 실패 인원 수 차감
            successUsers -= failUsers;

            // 스테이지 실패 리스트에 해당 스테이지 정보 삽입
            failList.add(new Stage(levelOfStage, rateOfFail));
        }

        // 실패율, 스테이지 레벨 순으로 정렬
        Collections.sort(failList);

        final int[] failHistory = new int[n];
        for (int i = 0; i < n; i++) {
            failHistory[i] = failList.get(i).getLevel();
        }

        return failHistory;
    }
}

class Stage implements Comparable<Stage> {
    private final int level;
    private final double rateOfFail;

    public Stage(int level, double rateOfFail) {
        this.level = level;
        this.rateOfFail = rateOfFail;
    }

    public int getLevel() {
        return level;
    }

    public double getRateOfFail() {
        return rateOfFail;
    }

    @Override
    public int compareTo(Stage o) {
        if (this.rateOfFail == o.rateOfFail) {
            return Integer.compare(this.level, o.level);
        }

        return Double.compare(o.rateOfFail, this.rateOfFail);
    }
}
