package com.jaenyeong.chapter_07_binary_search;

import java.util.Arrays;
import java.util.Scanner;

public class Question06 {
    /*
    [Question]
    떡볶이 떡 만들기

    [Input]
    1 line > 4 6
    2 line > 19 15 10 17
    [Output]
    > 15

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 떡의 개수 (1 <= n <= 1,000,000), 요청한 떡의 길이 (1 <= m <= 2,000,000,000)
        // 2번째 라인 : 각 떡의 길이 (총합은 항상 m 이상, 0부터 10억 이하의 정수)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("떡의 개수와 요청할 떡의 길이를 입력하세요");
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        scanner.nextLine();

        System.out.println("각 떡의 길이를 입력하세요");
        final int[] tteokBundle = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        System.out.println(binarySearch(tteokBundle, m));
    }

    private static int binarySearch(final int[] tteokBundle, final int target) {
        if ((tteokBundle == null) || (tteokBundle.length <= 0)) return -1;

        int start = 0;
        int end = Arrays.stream(tteokBundle).max().getAsInt();

        int result = 0;

        while (start <= end) {
            // 자를 지점 계산
            final int cutIdx = (start + end) / 2;

            // 각 떡을 자르고 남은 떡의 길이 합산
            int leftovers = leftoversAfterCutting(tteokBundle, cutIdx);

            // 찾은 경우 반환
            if (leftovers == target) return cutIdx;

            // 남은 떡의 양이 요청한 떡의 양보다 작을 때
            if (leftovers < target) {
                end = cutIdx - 1;
                continue;
            }

            // 남은 떡의 양이 요청한 떡의 양보다 클 때
            result = cutIdx; // 최대한 덜 잘랐을 경우 측정을 위해 매번 삽입
            start = cutIdx + 1;
        }

        return result;
    }

    // 각 떡의 길이에서 자를 포인트 지점을 뺀 값을 합산하여 반환
    private static int leftoversAfterCutting(final int[] tteokBundle, final int cutIdx) {
        int leftovers = 0;
        for (int tteok : tteokBundle) {
            if (tteok > cutIdx) {
                leftovers += (tteok - cutIdx);
            }
        }
        return leftovers;
    }
}
