package com.jaenyeong.chapter_03_greedy;

public class Question01 {
    /*
    [Question]
    거스름돈

    [Input]
    1 line > 500 100 50 10
    2 line > 1260
    [Output]
    > 6

     */

    public static void main(String[] args) {
        int change = 1260;
        int qty = 0;
        final int[] coinTypes = {500, 100, 50, 10};

        for (final int coin : coinTypes) {
            qty += change / coin;
            change %= coin;
        }

        System.out.println(qty);
    }
}
