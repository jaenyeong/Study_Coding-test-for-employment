package com.jaenyeong.chapter_14_sort;

import java.util.*;

public class PS02 {
    /*
    [Question]
    안테나

    [Input]
    1 line > 4
    2 line > 5 1 7 9
    [Output]
    > 5

    [입력 조건]
    1 line > 집의 수 (1 <= n <= 200,000)
    2 line > 각 집의 위치 (1 <= h <= 100,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("집의 수를 입력하세요");
        final int n = SC.nextInt();

        System.out.println("각 집의 위치를 입력하세요");
        final int[] houses = new int[n];
        for (int i = 0; i < n; i++) {
            houses[i] = SC.nextInt();
        }

        bookPS(houses, n);
    }

    private static void bookPS(final int[] houses, final int n) {
        // 중간 지점에 집에 설치하는 것이 거리의 총합을 최소로 할 수 있음
        // 각 집의 위치를 담은 배열을 정렬한 후 중간값을 출력
        Arrays.sort(houses);

        final int target = ((n - 1) / 2);
        System.out.println(houses[target]);
    }
}
