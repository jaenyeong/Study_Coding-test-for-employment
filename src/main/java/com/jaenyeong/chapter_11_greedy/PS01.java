package com.jaenyeong.chapter_11_greedy;

import java.util.*;

public class PS01 {
    /*
    [Question]
    모험가 길드

    [Input]
    1 line > 5
    2 line > 2 3 1 2 2
    [Output]
    > 2

    [입력 조건]
    1 line > 모험가 수 (1 <= n <= 100,000)
    2 line > 각 모험가의 공포도 (n 이하의 자연수)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        // 입력
        System.out.println("모험가의 수를 입력하세요");
        final int people = SC.nextInt();
        SC.nextLine();
        System.out.println("각 모험가의 공포도를 순서대로 입력하세요");
        // mySolve
        final int[] fearOfPeople = new int[people];
        // bookSolve
        final List<Integer> fearList = new ArrayList<>();
        for (int i = 0; i < people; i++) {
            fearOfPeople[i] = SC.nextInt();
            fearList.add(fearOfPeople[i]);
        }

        // 정렬
        Arrays.sort(fearOfPeople);
        Collections.sort(fearList);

        System.out.println(myPS(fearOfPeople, people));
        System.out.println(bookPS(fearList, people));
    }

    private static int myPS(final int[] fearOfPeople, final int people) {
        int members = 0; // 현재까지 모인 인원
        int party = 0;   // 반환할 파티 수

        for (int i = 0; i < people; i++) {
            members++;
            // 현재 모험가의 공포도 추출
            final int fear = fearOfPeople[i];

            // 현재까지 모인 인원 수와 현재 모험가의 공포도 비교
            if (members >= fear) {
                members = 0;
                party++;
            }
        }

        return party;
    }

    private static int bookPS(final List<Integer> people, final int n) {
        int result = 0; // 총 그룹의 수
        int count = 0;  // 현재 그룹에 포함된 모험가의 수

        for (int i = 0; i < n; i++) {
            // 그룹에 현재 모험가 합류
            count += 1;

            // 현재 그룹에 포함된 모험가의 수가 현재의 공포도 이상인 경우 그룹 결성
            if (count >= people.get(i)) {
                // 총 그룹 수 증가
                result += 1;
                // 현재 그룹에 포함된 모험가의 수 초기화
                count = 0;
            }
        }

        return result;
    }
}
