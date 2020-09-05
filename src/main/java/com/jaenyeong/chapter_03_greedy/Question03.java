package com.jaenyeong.chapter_03_greedy;

import java.util.Arrays;
import java.util.Scanner;

public class Question03 {
    /*
    [Question]
    숫자 카드 게임

    [Input]
    1 line > 3 3
    2 line > 3 1 2
    3 line > 4 1 4
    4 line > 2 2 2
    [Output]
    > 2

    [Input]
    1 line > 2 4
    2 line > 7 3 1 8
    3 line > 3 3 3 4
    [Output]
    > 3

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 행-세로크기 (1 <= n <= 100), 열-가로크기 (1 <= m <= 100)
        // 2번째 라인 이후 : 각 카드의 숫자 (1 <= k <= 10,000)
        final Scanner scanner = new Scanner(System.in);

        // m, n 조건 입력
        System.out.println("숫자 배열 입력");
        System.out.println("행의 값을 입력하세요");
        final int row = scanner.nextInt();
        // Because scanner nextInt method does not read the nextLine character
        scanner.nextLine();

        int result = 0;

        for (int i = 0; i < row; i++) {
            System.out.println("각 행의 카드 값을 입력하세요");
            String[] inputStr = scanner.nextLine().split(" ");

            int rowMinValue = Arrays.stream(inputStr).mapToInt((str) -> {
                if ((str != null) && !(str.trim().isEmpty())) {
                    return Integer.parseInt(str);
                }
                return 10_001;
            }).sorted().toArray()[0];

            result = Math.max(result, rowMinValue);

        }
        System.out.println(result);
    }
}
