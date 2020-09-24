package com.jaenyeong.chapter_12_implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PS02 {
    /*
    [Question]
    문자열 재정렬

    [Input]
    > K1KA5CB7
    [Output]
    > ABCKK13

    [Input]
    > AJKDLSI412K4JSJ9D
    [Output]
    > ADDIJJJKKLSS20

    [입력 조건]
    > 알파벳 대문자와 숫자로만 구성되어 있는 하나의 문자열 (1 <= s.length <= 10,000)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("알파벳 대문자와 문자로만 구성되어 있는 문자열을 입력하세요");
        final String input = SC.nextLine();

        System.out.println(myPS(input));
        System.out.println(bookPS(input));
    }

    private static String myPS(final String input) {
        // 주어진 문자열을 캐릭터 배열로 변환
        final char[] inputs = input.toCharArray();
        // 문자열에 포함된 캐릭터를 담을 리스트
        final List<Character> characters = new ArrayList<>();

        // 캐릭터 배열을 반복
        int sum = 0;
        for (char character : inputs) {
            // 해당 캐릭터가 숫자인지 판단
            if (Character.isDigit(character)) {
                // 숫자인 경우 합산
                sum += character - '0';
                continue;
            }
            // 문자인 경우 리스트에 삽입
            characters.add(character);
        }

        // 추출한 캐릭터 리스트 오름차순 정렬
        Collections.sort(characters);

        // 결과로 반환할 문자열 객체에 삽입
        StringBuilder result = new StringBuilder();
        for (char c : characters) {
            result.append(c);
        }
        result.append(sum);

        return String.valueOf(result);
    }

    private static String bookPS(final String input) {
        ArrayList<Character> result = new ArrayList<>();
        int value = 0;

        // 문자를 하나씩 확인
        for (int i = 0; i < input.length(); i++) {
            // 문자인 경우 캐릭터 배열에 삽입
            if (Character.isLetter(input.charAt(i))) {
                result.add(input.charAt(i));
            } else {
                // 숫자인 경우 합산
                value += input.charAt(i) - '0';
            }
        }

        Collections.sort(result);

        StringBuilder resultStr = new StringBuilder();
        for (Character character : result) {
            resultStr.append(character);
        }

        if (value != 0) resultStr.append(value);

        return resultStr.toString();
    }
}
