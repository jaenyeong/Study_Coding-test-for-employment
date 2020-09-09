package com.jaenyeong.chapter_07_binary_search;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

public class Question04 {
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
    private static final int OWN_LIMIT = 1_000_000;

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

        final int[] ownProducts = new int[OWN_LIMIT];
        System.out.println("각 부품 번호를 입력하세요");
        final int[] inputs = inputProducts(scanner);

        System.out.println("문의할 부품의 종류 수를 입력하세요");
        final int m = scanner.nextInt();
        scanner.nextLine();

        System.out.println("각 부품 번호를 입력하세요");
        final int[] reqProducts = inputProducts(scanner);

        // 계수 정렬을 위해 배열 세팅
        for (int input : inputs) {
            ownProducts[input]++;
        }

        StringJoiner strJoiner = new StringJoiner(" ");

        // 문의 부품 번호를 찾아 결과 문자열 생성
        for (int req : reqProducts) {
            if (ownProducts[req] >= 1) {
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
