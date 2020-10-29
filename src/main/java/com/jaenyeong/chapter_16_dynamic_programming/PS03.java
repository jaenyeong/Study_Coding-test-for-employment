package com.jaenyeong.chapter_16_dynamic_programming;

import java.util.Scanner;

public class PS03 {
    /*
    [Question]
    퇴사

    [Input]
    1 line > 7
    2 line > 3 10
    3 line > 5 20
    4 line > 1 10
    5 line > 1 20
    6 line > 2 15
    7 line > 4 40
    8 line > 2 200
    [Output]
    > 45

    [Input]
    01 line > 10
    02 line > 1 1
    03 line > 1 2
    04 line > 1 3
    05 line > 1 4
    06 line > 1 5
    07 line > 1 6
    08 line > 1 7
    09 line > 1 8
    10 line > 1 9
    11 line > 1 10
    [Output]
    > 55

    [Input]
    01 line > 10
    02 line > 5 10
    03 line > 5 9
    04 line > 5 8
    05 line > 5 7
    06 line > 5 6
    07 line > 5 10
    08 line > 5 9
    09 line > 5 8
    10 line > 5 7
    11 line > 5 6
    [Output]
    > 20

    [Input]
    01 line > 10
    02 line > 5 50
    03 line > 4 40
    04 line > 3 30
    05 line > 2 20
    06 line > 1 10
    07 line > 1 10
    08 line > 2 20
    09 line > 3 30
    10 line > 4 40
    11 line > 5 50
    [Output]
    > 90

    [입력 조건]
    1 line > 상담일(상담 가능한 일수) (1 <= n <= 15)
    2 ~ (1 + n) line > 각 상담일에 걸리는 시간과 받을 수 있는 금액 (1 <= t <= 5), (1 <= p <= 1,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("상담 가능한 일수를 입력하세요");
        final int n = SC.nextInt();

        final int[] times = new int[n];
        final int[] pays = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 상담일에 걸리는 시간과 받을 수 있는 금액을 입력하세요");
            times[i] = SC.nextInt();
            pays[i] = SC.nextInt();
        }

        System.out.println(bookPS(times, pays, n));
    }

    private static int bookPS(final int[] times, final int[] pays, final int allDays) {
        // DP 테이블
        final int[] consult = new int[16];

        // 현재까지 페이 합계 최댓값
        int maxSumPay = 0;

        // 컨설팅 마지막날부터 역순으로 확인
        for (int day = (allDays - 1); 0 <= day; day--) {
            // 상담 시간 초기화 (해당 날짜에 소요되는 상담시간 + 인덱스)
            final int time = times[day] + day;

            // 상담이 나머지 기간안에 끝나는 경우
            if (time <= allDays) {
                // DP 테이블, 페이 합계 최댓값 갱신
                consult[day] = Math.max(pays[day] + consult[time], maxSumPay);
                maxSumPay = consult[day];
                continue;
            }

            // 상담이 나머지 기간안에 끝나지 못하는 경우
            consult[day] = maxSumPay;
        }

        return maxSumPay;
    }
}
