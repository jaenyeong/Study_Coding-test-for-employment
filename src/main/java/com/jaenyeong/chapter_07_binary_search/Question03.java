package com.jaenyeong.chapter_07_binary_search;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

public class Question03 {
    /*
    [Question]
    부품 찾기

    [Input]
    1 line > 5
    2 line > 8 3 7 9 2
    3 line > 3
    4 line > 5 7 9
    [Output]
    > no yes yes

     */

    private static final String YES = "yes";
    private static final String NO = "no";

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 보유한 부품 종류 수 (1 <= n <= 1,000,000)
        // 2번째 라인 : 보유한 각 부품 번호 n개 (1 <= e <= 1,000,000)
        // 3번째 라인 : 문의한 부품 종류 수 (1 <= m <= 100,000)
        // 4번째 라인 : 문의한 각 부품 번호 m개 (1 <= e <= 1,000,000,000)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("보유한 부품의 종류 수를 입력하세요");
        final int n = scanner.nextInt();
        scanner.nextLine();

        System.out.println("각 부품 번호를 입력하세요");
        final int[] ownProducts = Arrays.stream(inputProducts(scanner)).sorted().toArray();

        System.out.println("문의할 부품의 종류 수를 입력하세요");
        final int m = scanner.nextInt();
        scanner.nextLine();

        System.out.println("각 부품 번호를 입력하세요");
        final int[] reqProducts = inputProducts(scanner);

        final StringJoiner sj = new StringJoiner(" ");

        // 이진 탐색
        for (int target : reqProducts) {
            sj.add(recursiveBinarySearch(ownProducts, 0, ownProducts.length - 1, target));
        }

        System.out.println(sj.toString());
    }

    private static int[] inputProducts(Scanner scanner) {
        return Arrays.stream(scanner.nextLine().split(" "))
            .mapToInt(Integer::parseInt)
            .toArray();
    }

    private static String recursiveBinarySearch(final int[] ownProducts, final int start, final int end, final int target) {
        if ((ownProducts == null) || (ownProducts.length <= 0)) return NO;

        if (start > end) return NO;

        final int midIdx = (start + end) / 2;
        final int midValue = ownProducts[midIdx];

        // 해당 제품을 찾아낸 경우
        if (target == midValue) return YES;

        // 타겟이 중간 값보다 작은 경우
        if (target < midValue) {
            return recursiveBinarySearch(ownProducts, start, midIdx - 1, target);
        }

        // 타겟이 중간 값보다 큰 경우
        return recursiveBinarySearch(ownProducts, midIdx + 1, end, target);
    }
}
