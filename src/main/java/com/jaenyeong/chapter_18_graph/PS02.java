package com.jaenyeong.chapter_18_graph;

import java.util.Scanner;

public class PS02 {
    /*
    [Question]
    탑승구

    [Input]
    1 line > 4
    2 line > 3
    3 line > 4
    4 line > 1
    5 line > 1
    [Output]
    > 2

    [Input]
    1 line > 4
    2 line > 6
    3 line > 2
    4 line > 2
    5 line > 3
    6 line > 3
    7 line > 4
    8 line > 4
    [Output]
    > 3

    [입력 조건]
    1 line > 탑승구의 수 (1 <= g <= 100,000)
    2 line > 비행기의 수 (1 <= p <= 100,000)
    3 ~ (2 + p) line > 각 비행기가 도킹할 수 있는 탑승구의 정보 (1 <= gi <= g)
                       i번째 비행기가 1번부터 gi번째 탑승구 중 하나에 도킹 가능

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("탑승구의 수를 입력하세요");
        final int g = SC.nextInt();

        // 탑승구 배열 초기화
        final int[] gates = new int[g + 1];
        for (int i = 0; i <= g; i++) {
            gates[i] = i;
        }

        System.out.println("비행기의 수를 입력하세요");
        final int p = SC.nextInt();

        // 도킹할 비행기 배열 초기화
        final int[] airports = new int[p];
        for (int i = 0; i < p; i++) {
            System.out.println((i + 1) + "번째 비행기의 도킹 가능한 탑승구의 최대 번호를 입력하세요");
            airports[i] = SC.nextInt();
        }

        System.out.println(bookPS(gates, airports));
    }

    private static int bookPS(final int[] gates, final int[] airports) {
        // 비행기가 특정 탑승구에 도킹되는 경우 해당 탑승구 좌측 탑승구 집합과 합침

        int docking = 0;
        // 각 비행기 하나씩 도킹
        for (int airport : airports) {
            // 현재 탑승구 집합의 루트 확인
            final int curGateRoot = findGateRoot(airport, gates);
            // 루트가 0인 경우
            if (curGateRoot == 0) break;

            // 현재 탑승구의 왼쪽 탑승구 집합과 합침
            unionGateRoot(curGateRoot, curGateRoot - 1, gates);

            // 도킹 카운트
            docking++;
        }

        return docking;
    }

    private static int findGateRoot(final int target, final int[] gates) {
        gates[target] = (gates[target] == target)
            ? target
            : findGateRoot(gates[target], gates);

        return gates[target];
    }

    private static void unionGateRoot(final int curr, final int prev, final int[] gates) {
        final int currRoot = findGateRoot(curr, gates);
        final int prevRoot = findGateRoot(prev, gates);

        if (currRoot < prevRoot) {
            gates[prev] = curr;
            return;
        }

        gates[curr] = prev;
    }
}
