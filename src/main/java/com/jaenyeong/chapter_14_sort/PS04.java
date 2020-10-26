package com.jaenyeong.chapter_14_sort;

import java.util.*;

public class PS04 {
    /*
    [Question]
    카드 정렬하기

    [Input]
    1 line > 3
    2 line > 10
    3 line > 20
    4 line > 40
    [Output]
    > 100

    [입력 조건]
    1 line > 카드 묶음의 수 (1 <= n <= 100,000)
    2 ~ (1 + n) line > 각 카드 묶음의 카드 수

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("카드 묶음의 수를 입력하세요");
        final int n = SC.nextInt();

        final int[] cards = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 카드 묶음의 카드 수를 입력하세요");
            cards[i] = SC.nextInt();
        }

        System.out.println(myPS(cards));
    }

    private static int myPS(final int[] cards) {
        // 카드의 수가 작은 순서부터 계산하는 것이 효율적이기 때문에 오름차순 정렬
        final Queue<Card> cardQ = new PriorityQueue<>(Comparator.comparingInt(Card::getNumber));

        // 기존 카드 묶음을 카드 큐에 삽입
        for (int cardNumber : cards) {
            cardQ.offer(new Card(cardNumber));
        }

        int result = 0;

        // 주어진 카드 묶음이 없다면
        while (!cardQ.isEmpty()) {
            // 첫번째 카드 묶음을 꺼냄
            final Card firstCards = cardQ.poll();

            // 카드 큐가 비었다면 반복문 탈출
            if (cardQ.peek() == null) {
                break;
            }

            final Card secondCards = cardQ.poll();

            // 두 카드 묶음의 비교 횟수
            final int compareSum = firstCards.getNumber() + secondCards.getNumber();

            // 최종 비교 횟수 연산
            result += compareSum;

            // 다른 카드 묶음과 비교 횟수 연산을 위해 카드 큐에 해당 비교 횟수를 삽입
            cardQ.offer(new Card(compareSum));
        }

        return result;
    }
}

class Card {
    private final int number;

    public Card(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
