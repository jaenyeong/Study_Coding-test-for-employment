package com.jaenyeong.chapter_08_dynamic_programming;

public class Question03 {
    /*
    [Question]
    피보나치 수열(호출 순서 확인)

    [Input]
    >
    [Output]
    >

     */

    private static long[] memoization;

    public static void main(String[] args) {
        final int x = 6;
        memoization = new long[x + 1];
        memoization[1] = 1;
        memoization[2] = 1;

        printFibonacci(6);
    }

    private static long printFibonacci(final int x) {
        System.out.print("f(" + x + ") ");

        // 캐싱된 값이 있다면 캐싱된 값을 반환
        if (memoization[x] != 0) {
            return memoization[x];
        }

        // 캐싱된 값이 없다면 재귀 호출하여 캐싱 후 반환
        memoization[x] = printFibonacci(x - 2) + printFibonacci(x - 1);
        return memoization[x];
    }
}
