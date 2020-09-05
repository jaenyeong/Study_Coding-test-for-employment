package com.jaenyeong.chapter_04_implementation;

import java.util.Scanner;

public class Question02 {
    /*
    [Question]
    시각

    [Input]
    > 5
    [Output]
    > 11475

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 00시 00분 00초 ~ n시 59분 59초 (0 <= n <= 23)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("범위 시간을 입력해주세요");
        final int n = scanner.nextInt();

        final String target = "3";

        final int minuteLimit = 60;
        final int secondLimit = 60;

        int count = 0;

        // 각각 시, 분, 초 루프 (3중 반복문), 3의 존재 여부 판별
        for (int hour = 0; hour <= n; hour++) {
            for (int minute = 0; minute < minuteLimit; minute++) {
                for (int second = 0; second < secondLimit; second++) {

                    String time = String.valueOf(hour) + (minute) + (second);

                    if (time.contains(target)) {
                        count++;
                    }

                    // 시간 확인
                    // 분 앞자리, 뒷자리 확인
                    // 초 앞자리, 뒷자리 확인
//					if ((hour % 10 == 3)
//						|| (minute / 10 == 3) || (minute % 10 == 3)
//						|| (second / 10 == 3) || (second % 10 == 3)) {
//						count++;
//					}
                }
            }
        }

        System.out.println(count);
    }
}
