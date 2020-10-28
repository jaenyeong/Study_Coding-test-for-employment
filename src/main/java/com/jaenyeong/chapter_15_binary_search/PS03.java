package com.jaenyeong.chapter_15_binary_search;

import java.util.Arrays;
import java.util.Scanner;

public class PS03 {
    /*
    [Question]
    공유기 설치

    [Input]
    1 line >
    2 line >
    3 line >
    4 line >
    5 line >
    6 line >
    7 line >
    [Output]
    > 3

    [입력 조건]
    1 line > 집의 개수 (2 <= n <= 200,000), 공유기의 개수 (2 <= c <= n)
    2 + (1 + n) line > 각 집의 좌표 (1 <= x <= 1,000,000,000)
                       다른 집이 같은(중복된) 좌표를 갖는 경우는 없음

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("집의 개수와 공유기의 개수를 입력하세요");
        final int n = SC.nextInt();
        final int c = SC.nextInt();

        final int[] houses = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 집의 좌표를 입력하세요");
            houses[i] = SC.nextInt();
        }

        System.out.println(bookPS(houses, n, c));
    }

    private static int bookPS(final int[] houses, final int housesSize, final int givenRouter) {
        // 이진탐색을 위해 정렬
        Arrays.sort(houses);

        // 설치 공유기 간 거리가 가장 짧은 값
        int minDist = houses[1] - houses[0];
        // 설치 공유기 간 거리가 가장 긴 값
        int maxDist = houses[housesSize - 1] - houses[0];

        //  (최종 반환될 결과값)
        int resultMax = 0;

        // min <= max가 될 때까지 반복
        while (minDist <= maxDist) {
            // 가장 인접한 두 공유기 사이의 거리의 차
            int distGap = (minDist + maxDist) / 2;

            // 공유기 설치 횟수
            final int setCount = setRouter(houses, housesSize, distGap);

            // 주어진 C개 이상의 공유기를 설치할 수 있는 경우 (거리 증가)
            if (setCount >= givenRouter) {
                minDist = distGap + 1;
                resultMax = distGap;
                continue;
            }

            // 주어진 C개 이상의 공유기를 설치할 수 없는 경우 (거리 감소)
            maxDist = distGap - 1;
        }

        return resultMax;
    }

    private static int setRouter(final int[] houses, final int housesSize, final int distGap) {
        // 설치 지점 (첫번째 집에는 무조건 공유기를 설치한다고 가정)
        int setPoint = houses[0];

        // 설치 횟수
        int setCount = 1;

        // 공유기를 주어진 집 목록에 앞에서부터 차례대로 설치
        for (int i = 1; i < housesSize; i++) {
            final int currPoint = houses[i];

            // 공유기 설치 지점 + 설치 거리 갭을 현재 지점과 비교
            if (currPoint >= setPoint + distGap) {
                setPoint = currPoint;
                setCount++;
            }
        }

        return setCount;
    }
}
