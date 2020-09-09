package com.jaenyeong.chapter_08_dynamic_programming;

public class Question01 {
    /*
    [Question]
    피보나치 수열

    [Input]
    >
    [Output]
    >

     */

    public static void main(String[] args) {
        System.out.println(fibonacci(4));
    }

    private static int fibonacci(final int x) {
        if ((x == 1) || (x == 2)) return 1;

        return fibonacci(x - 2) + fibonacci(x - 1);
    }
}
