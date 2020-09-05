package com.jaenyeong.chapter_03_greedy;

import java.util.Arrays;
import java.util.Scanner;

public class Question02 {
    /*
    [Question]
    큰 수의 법칙

    [Input]
    1 line > 5 8 3
    2 line > 2 4 5 4 6
    [Output]
    > 46
    [Explanation]
    > 6 + 6 + 6 + 5 + 6 + 6 + 6 + 5

    [Input]
    1 line > 5 7 2
    2 line > 3 4 3 4 3
    [Output]
    > 28
    [Explanation]
    > 4 + 4 + 4 + 4 + 4 + 4 + 4

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 배열의 크기 (2 <= n <= 1,000), 숫자가 더해지는 횟수 (1 <= m <= 10,000)
        //            : 덧셈 연산시 하나의 원소를 반복 가능한 횟수 (1 <= k <= 10,000)
        // 2번째 라인 : N개의 자연수로 이루어진 배열 원소 (1 <= e <= 10,000)
        final Scanner scanner = new Scanner(System.in);

        // 조건 입력
        System.out.println("N (배열 길이) 값을 입력하세요");
        final int n = scanner.nextInt();
        System.out.println("N의 값만큼 배열 원소를 입력하세요");
        final int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        System.out.println("M (숫자가 더해지는 횟수) 값을 입력하세요");
        final int m = scanner.nextInt();
        System.out.println("K (같은 수를 반복 가능한 횟수) 값을 입력하세요");
        final int k = scanner.nextInt();

        // 큰 수를 뽑기 위해 정렬
        Arrays.sort(numbers);

        // 값이 가장 큰 수와 그 다음으로 큰 수
        final int firstMaxValue = numbers[n - 1];
        final int secondMaxValue = numbers[n - 2];

        // 가장 큰 수가 더해지는 횟수
        // ((m / (k + 1)) * k) + (m % (k + 1))
        int opCount = (m / (k + 1)) * k; // 몫
        opCount += m % (k + 1);          // 나머지

        int result = opCount * firstMaxValue;
        result += (m - opCount) * secondMaxValue;

        System.out.println(result);
    }
}
