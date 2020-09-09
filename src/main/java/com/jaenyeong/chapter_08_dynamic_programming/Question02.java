package com.jaenyeong.chapter_08_dynamic_programming;

public class Question02 {
    /*
    [Question]
    피보나치 수열(메모이제이션)

    [Input]
    >
    [Output]
    >

     */

    private static long[] memoization;

    public static void main(String[] args) {
        final int x = 50;
        memoization = new long[x + 1];
        memoization[1] = 1;
        memoization[2] = 1;

        System.out.println(fibonacci(x));
    }

    // top-down 방식
    private static long fibonacci(final int x) {
        // 캐싱된 값이 있다면 캐싱된 값을 반환
        if (memoization[x] != 0) {
            return memoization[x];
        }

        // 캐싱된 값이 없다면 재귀 호출하여 캐싱 후 반환
        memoization[x] = fibonacci(x - 2) + fibonacci(x - 1);
        return memoization[x];
    }
}
