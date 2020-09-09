package com.jaenyeong.chapter_08_dynamic_programming;

public class Question04 {
    /*
    [Question]
    피보나치 수열

    [Input]
    >
    [Output]
    >

     */

    public static void main(String[] args) {
        final int x = 50;
        long[] memoization = new long[x + 1];
        memoization[1] = 1;
        memoization[2] = 1;

        // bottom-up 방식
        for (int i = 3; i <= x; i++) {
            memoization[i] = memoization[i - 2] + memoization[i - 1];
        }

        System.out.println(memoization[x]);
    }
}
