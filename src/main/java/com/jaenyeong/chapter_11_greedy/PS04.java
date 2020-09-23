package com.jaenyeong.chapter_11_greedy;

import java.util.*;
import java.util.stream.Collectors;

public class PS04 {
    /*
    [Question]
    만들 수 없는 금액

    [Input]
    >
    [Output]
    >

    [입력 조건]
    1 line > 동전의 개수를 나타내는 양의 정수 (1 <= n <= 1,000)
    2 line > 각 동전의 화폐 단위를 나타내는 n개의 자연수 (공백으로 구분, 1,000,000 이하의 자연수)

     */
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("동전의 개수를 입력하세요");
        final int n = SC.nextInt();
        SC.nextLine();

        System.out.println("각 동전의 단위를 입력하세요");
        final int[] coins = new int[n];
        for (int i = 0; i < n; i++) {
            coins[i] = SC.nextInt();
        }

        System.out.println(bookPS(Arrays.stream(coins).boxed().collect(Collectors.toList())));
    }

    private static int bookPS(final List<Integer> coinList) {
        // 동전의 크기를 기준으로 오름차순 정렬
        Collections.sort(coinList);

        int target = 1;

        for (int coin : coinList) {
            // 누적 금액이 해당 코인보다 작은 경우
            if (target < coin) break;
            target += coin;
        }

        return target;
    }
}
