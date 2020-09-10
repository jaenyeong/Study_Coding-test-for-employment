package com.jaenyeong.chapter_08_dynamic_programming;

import java.util.Arrays;
import java.util.Scanner;

public class Question06 {
    /*
    [Question]
    개미 전사

    [Input]
    1 line > 4
    2 line > 1 3 1 5
    [Output]
    > 8

     */

    public static void main(String[] args) {
        // 입력 정보
        // 1번째 라인 : 식량창고의 수 (3 <= n <= 100)
        // 2번째 라인 : 각 식량창고에 저장된 식량 수 (0 <= k <= 1,000)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("식량창고의 수를 입력하세요");
        final int n = scanner.nextInt();
        scanner.nextLine();

        System.out.println("식량창고별 식량 수를 입력하세요");
        final int[] foodWarehouse =
            Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        final int[] dpTable = new int[n];

        dpTable[0] = foodWarehouse[0];
        dpTable[1] = Math.max(dpTable[0], foodWarehouse[0]);

        // 각 창고를 방문하면서 그 전 창고들의 식량 수와 비교
        for (int i = 2; i < n; i++) {
            dpTable[i] = Math.max(dpTable[i - 1], (dpTable[i - 2] + foodWarehouse[i]));
        }

        System.out.println(dpTable[n - 1]);
    }
}
