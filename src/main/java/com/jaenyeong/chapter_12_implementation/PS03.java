package com.jaenyeong.chapter_12_implementation;

import java.util.Scanner;

public class PS03 {
    /*
    [Question]
    문자열 압축

    [Input]
    > aabbaccc
    [Output]
    > 7

    [Input]
    > ababcdcdababcdcd
    [Output]
    > 9

    [Input]
    > abcabcdede
    [Output]
    > 8

    [Input]
    > abcabcabcabcdededededede
    [Output]
    > 14

    [Input]
    > xababcdcdababcdcd
    [Output]
    > 17

    [입력 조건]
    > 알파벳 소문자로만 이루어진 문자열 (1 <= s <= 1,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("알파벳 소문자로만 이루어진 문자열을 입력하세요");
        final String input = SC.next();

        System.out.println(myPS(input));
        System.out.println(bookPS(input));
    }

    private static int myPS(final String input) {
        final int inputSize = input.length();

        int shortestLength = inputSize;

        // 주어진 문자열에 가운데 인덱스
        final int midIdx = inputSize / 2;

        // 비교할 키 사이즈를 1부터 midIdx까지 반복
        for (int keySize = 1; keySize <= midIdx; keySize++) {
            String inputStr = input;

            // 키 사이즈에 맞게 압축할 키워드 추출
            String keyword = inputStr.substring(0, keySize);
            inputStr = inputStr.substring(keySize);

            // 문자열 압축 후 최종 문자열
            StringBuilder finalStr = new StringBuilder();

            // 일치 여부 카운트
            int equalCnt = 1;

            // 압축할 키워드로 주어진 문자열을 모두 확인할 때까지 반복
            while (true) {

                // 키워드 사이즈와 문자열 길이를 비교하여 작은 값을 삽입
                int endIdx = Math.min(inputStr.length(), keySize);
                // 비교할 문자열 추출
                String compareStr = inputStr.substring(0, endIdx);
                // 비교할 문자열이 없는 경우
                if (compareStr.length() <= 0) {
                    inputFinalStr(keyword, finalStr, equalCnt);
                    break;
                }

                // 기존 문자열에서 비교할 문자열을 자르고 난 나머지 문자열
                if (endIdx < inputStr.length()) {
                    inputStr = inputStr.substring(endIdx);
                } else {
                    inputStr = "";
                }

                // 비교할 키워드와 동일하면 카운트
                if (keyword.equalsIgnoreCase(compareStr)) {
                    equalCnt++;
                    continue;
                }

                // 동일하지 않을 경우 현재까지 계산한 값을 최종 문자열에 삽입
                inputFinalStr(keyword, finalStr, equalCnt);
                // 동일한 문자 수 초기화
                equalCnt = 1;
                // 키워드 변경
                keyword = compareStr;
            }

            // 최종 문자 수와 기존 문자 수를 비교하여 더 작은 값을 삽입
            shortestLength = Math.min(shortestLength, finalStr.length());
        }

        return shortestLength;
    }

    private static void inputFinalStr(final String keyword, final StringBuilder finalStr, final int equalCnt) {
        if (equalCnt == 1) {
            finalStr.append(keyword);
            return;
        }

        finalStr.append(equalCnt).append(keyword);
    }

    private static int bookPS(final String input) {
        int answer = input.length();

        // 1개 단위(step)부터 압축 단위를 늘려가며 확인
        for (int step = 1; step < input.length() / 2 + 1; step++) {
            // 압축된 문자열을 담을 변수
            String compressed = "";

            // 앞에서부터 step만큼의 문자열 추출
            String prev = input.substring(0, step);

            // 일치 여부 카운트
            int cnt = 1;

            // 단위(step) 크기만큼 증가시키며 이전 문자열과 비교
            for (int j = step; j < input.length(); j += step) {

                // 이전 상태와 동일하다면 압축 횟수(count) 증가
                String sub = "";
                // 압축 단어의 크기 만큼 반복하여 비교할 문자열 추출
                for (int k = j; k < j + step; k++) {
                    if (k < input.length()) {
                        sub += input.charAt(k);
                    }
                }

                // 이전 문자열과 같은지 비교하여 압축 횟수 증가
                if (prev.equals(sub)) {
                    cnt += 1;
                } else { // 다른 문자열이 나온 경우 (더 이상 압축하지 못하는 경우)

                    // 2번 이상 반복되면 반복된 횟수를 앞에 붙여줌
                    compressed += (cnt >= 2) ? cnt + prev : prev;

                    // 문자열 및 카운트 상태 초기화
                    prev = sub;
                    cnt = 1;
                }
            }

            // 남아있는 문자열 처리
            // 2번 이상 반복되면 반복된 횟수를 앞에 붙여줌
            compressed += (cnt >= 2) ? cnt + prev : prev;

            // 만들어지는 압축 문자열이 가장 짧은 것이 정답
            answer = Math.min(answer, compressed.length());
        }

        return answer;
    }
}
