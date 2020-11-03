package com.jaenyeong.chapter_18_graph;

import java.util.Arrays;
import java.util.Scanner;

public class PS01 {
    /*
    [Question]
    여행 계획

    [Input]
    1 line > 5 4
    2 line > 0 1 0 1 1
    3 line > 1 0 1 1 0
    4 line > 0 1 0 0 0
    5 line > 1 1 0 0 0
    6 line > 1 0 0 0 0
    7 line > 2 3 4 3
    [Output]
    > YES

    [입력 조건]
    1 line > 여행지의 수 (1 <= n <= 500), 여행 계획에 속한 도시의 수 (1 <= m <= 500)
    2 ~ (1 + n) line > 연결된 여행지 정보 (2차원 배열 형태로 구성, 각 행 정보) (1 = 연결, 0 = 미연결)
    (2 + n) line > 여행 계획에 포함된 여행지의 번호

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int CONNECTED = 1;

    public static void main(String[] args) {
        System.out.println("여행지의 수와 여행 계획에 속한 도시의 수를 입력하세요");
        final int n = SC.nextInt();
        final int m = SC.nextInt();

        System.out.println("연결된 여행지 정보를 입력하세요");
        final int[][] toursInfo = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 연결된 여행지 정보의 행을 입력하세요");
            for (int j = 0; j < n; j++) {
                toursInfo[i][j] = SC.nextInt();
            }
        }

        SC.nextLine();

        System.out.println("여행 계획에 포함된 여행지 번호들을 입력하세요");
        final int[] toursPlan = Arrays.stream(SC.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 작업 수행
        final boolean result = bookPS(toursInfo, n, toursPlan, m);
        System.out.println(result ? "YES" : "NO");
    }

    private static boolean bookPS(final int[][] toursInfo, final int n, final int[] toursPlan, final int m) {
        // 루트 테이블 초기화 (연결된 지역을 하나의 집합으로 만듦)
        final int[] parentList = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            // 자기 자신으로 각 지역 루트 초기화
            parentList[i] = i;
        }

        // 주어진 여행지 정보를 순회
        for (int firstSpot = 0; firstSpot < n; firstSpot++) {
            for (int secondSpot = 0; secondSpot < n; secondSpot++) {

                // 두 지역이 연결된 경우
                final int connect = toursInfo[firstSpot][secondSpot];
                if (connect == CONNECTED) {
                    // 연결된 두 지역을 같은 집합으로 만듦으로써 여행이 가능한 경로인지 확인
                    union(firstSpot + 1, secondSpot + 1, parentList);
                }
            }
        }

        // 여행 계획에 포함된 여행지가 같은 집합 여부 확인 (같은 부모를 가지고 있는지)
        for (int i = 0; i < m - 1; i++) {
            final int toursSpot = findParent(toursPlan[i], parentList);
            final int nextToursSpot = findParent(toursPlan[i + 1], parentList);

            // 같은 집합이 아닌 경우 (같은 부모를 갖고 있지 않은 경우)
            if (toursSpot != nextToursSpot) {
                return false;
            }
        }

        return true;
    }

    private static void union(final int first, final int second, final int[] parentList) {
        final int firstParent = findParent(first, parentList);
        final int secondParent = findParent(second, parentList);

        // 첫 번째 여행지의 순번이 두 번째 여행지의 순번보다 작은 경우
        if (firstParent < secondParent) {
            parentList[second] = first;
            return;
        }

        parentList[first] = second;
    }

    private static int findParent(final int element, final int[] parentList) {
        parentList[element] = (parentList[element] == element)
            ? element
            : findParent(parentList[element], parentList);

        return parentList[element];
    }
}
