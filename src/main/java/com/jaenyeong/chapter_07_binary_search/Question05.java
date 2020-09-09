package com.jaenyeong.chapter_07_binary_search;

import java.util.*;

public class Question05 {
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

        final Set<Integer> ownProducts = new HashSet<>();
        System.out.println("각 부품 번호를 입력하세요");
        for (int i = 0; i < n; i++) {
            ownProducts.add(scanner.nextInt());
        }

        System.out.println("문의할 부품의 종류 수를 입력하세요");
        final int m = scanner.nextInt();
        scanner.nextLine();

        System.out.println("각 부품 번호를 입력하세요");
        final int[] reqProducts = inputProducts(scanner);

        StringJoiner strJoiner = new StringJoiner(" ");
        for (int req : reqProducts) {
            if (ownProducts.contains(req)) {
                strJoiner.add(YES);
                continue;
            }
            strJoiner.add(NO);
        }

        System.out.println(strJoiner.toString());
    }

    private static int[] inputProducts(Scanner scanner) {
        return Arrays.stream(scanner.nextLine().split(" "))
            .mapToInt(Integer::parseInt)
            .toArray();
    }
}
