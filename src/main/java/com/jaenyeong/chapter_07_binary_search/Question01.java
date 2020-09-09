package com.jaenyeong.chapter_07_binary_search;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Question01 {
    /*
    [Question]
    순차탐색

    [Input]
    >
    [Output]
    >

     */

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("찾을 문자열을 입력하세요");
        final String target = scanner.nextLine();

        System.out.println("생성할 배열의 크기를 입력하세요");
        final int inputsSize = scanner.nextInt();
        scanner.nextLine();

        System.out.println("배열의 원소가 될 문자열들을 입력하세요");
        List<String> inputs = new ArrayList<>();
        for (int i = 0; i < inputsSize; i++) {
            inputs.add(scanner.next());
        }

        final int targetIdx = sequentialSearch(inputs, target);
        System.out.println(targetIdx);
    }

    private static int sequentialSearch(final List<String> inputs, final String target) {
        if ((inputs == null) || (inputs.size() <= 0)) return -1;
        if ((target == null) || (target.trim().equals(""))) return -1;

        for (String input : inputs) {
            if (target.equals(input)) {
                return input.indexOf(input) + 1;
            }
        }

        return -1;
    }
}
