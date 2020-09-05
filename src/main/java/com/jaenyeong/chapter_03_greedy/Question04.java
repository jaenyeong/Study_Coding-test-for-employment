package com.jaenyeong.chapter_03_greedy;

import java.util.Scanner;

public class Question04 {
    /*
    [Question]
    1이 될 때까지

    [Input]
    > 17 4
    [Output]
    > 3

    [Input]
    > 25 5
    [Output]
    > 2

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 피제수 (2 <= n <= 100,000),  제수 (2 <= k <= 100,000)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("피제수 N, 제수 K 값을 입력하세요");
        final int n = scanner.nextInt();
        final int k = scanner.nextInt();

        System.out.println(getCount(n, k));
        System.out.println(getCount2(n, k));
    }

    private static int getCount(int n, final int k) {
        int count = 0;

        while (n > 1) {
            if (n % k != 0) {
                n -= 1;
                count++;
                continue;
            }

            n /= k;
            count++;
        }

        return count;
    }

    private static int getCount2(int n, final int k) {
        int count = 0;

        while (true) {
            // 반복하면서 n을 k로 나눌 때 n이 최대한 빠르게 (적은 연산으로) k의 배수가 되게끔 처리
            int target = (n / k) * k;
            count += (n - target);
            n = target;

            if (n < k) {
                break;
            }

            n /= k;
            count++;
        }

        count += (n - 1);

        return count;
    }
}
