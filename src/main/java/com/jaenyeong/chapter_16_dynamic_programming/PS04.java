package com.jaenyeong.chapter_16_dynamic_programming;

import java.util.*;

public class PS04 {
    /*
    [Question]
    병사 배치하기

    [Input]
    1 line > 7
    2 line > 15 11 4 8 5 2 4
    [Output]
    > 2

    [입력 조건]
    1 line > 병사의 수 (1 <= n <= 2,000)
    2 line > 각 병사의 전투력 (1 <= x <= 10,000,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("병사의 수를 입력하세요");
        final int n = SC.nextInt();

        // 오름차순으로 역정렬 시키기 위해 정수형 배열이 아닌 리스트 사용 (최장 증가 수열 문제)
        final List<Integer> powerOfSoldiers = new ArrayList<>();
        System.out.println("각 병사의 전투력을 입력하세요");
        for (int i = 0; i < n; i++) {
            powerOfSoldiers.add(SC.nextInt());
        }

        System.out.println(bookPS(powerOfSoldiers, n));
    }

    private static int bookPS(final List<Integer> powerOfSoldiers, final int n) {
        // 해당 문제는 내림차순으로 최장 감소 수열을 의미
        // 여기서는 오름차순으로 정렬하여 최장 증가 수열 문제로 풂
        Collections.reverse(powerOfSoldiers);

        // LIS (Longest Increasing Subsequence) DP 테이블 초기화
        final int[] lisDP = new int[n];
        Arrays.fill(lisDP, 1);

        for (int curr = 1; curr < n; curr++) {
            for (int prev = 0; prev < curr; prev++) {
                final int powerOfCurrSoldier = powerOfSoldiers.get(curr);
                final int powerOfPrevSoldier = powerOfSoldiers.get(prev);

                // 현재 병사의 전투력이 이전 병사의 전투력보다 큰 경우만 처리
                if (powerOfCurrSoldier > powerOfPrevSoldier) {
                    lisDP[curr] = Math.max(lisDP[curr], lisDP[prev] + 1);
                }
            }
        }

        return excludeSoldier(n, lisDP);
    }

    private static int excludeSoldier(final int n, final int[] lisDP) {
        // 열외해야 하는 병사의 최소 수를 반환
        int maxSize = 0;
        for (int solider : lisDP) {
            maxSize = Math.max(maxSize, solider);
        }

        return n - maxSize;
    }
}
