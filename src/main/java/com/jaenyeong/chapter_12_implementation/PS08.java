package com.jaenyeong.chapter_12_implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PS08 {
    /*
    [Question]
    외벽 점검

    [Input]
    1 line > 12
    2 line > 1 5 6 10
    3 line > 1 2 3 4
    [Output]
    > 2

    [Input]
    1 line > 12
    2 line > 1 3 4 9 10
    3 line > 3 5 7
    [Output]
    > 1

    [입력 조건]
    1 line > 점검할 외벽의 길이 또는 둘레 (1 <= n <= 200)
    2 line > 오름차순으로 정렬된 취약지점 (1 <= weak.length <= 15)
             각 원소의 크기 (0 <= w <= (n - 1))
    3 line > 각각 친구들이 1시간 동안 이동할 수 있는 거리 (1 <= friends.length <= 8)
             각 원소의 크기 (1 <= f <= 100)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int FAIL = -1;
    private static final int FIRST_MEMBER = 0;

    public static void main(String[] args) {
        System.out.println("외벽의 길이를 입력하세요");
        final int n = SC.nextInt();
        SC.nextLine();

        System.out.println("점검이 필요한 취약지점을 입력하세요");
        final int[] weak = Arrays.stream(SC.nextLine().split(" "))
            .mapToInt(Integer::parseInt).toArray();

        System.out.println("점검을 도와줄 친구들의 1시간 동안 이동 가능한 거리를 입력하세요");
        final int[] dist = Arrays.stream(SC.nextLine().split(" "))
            .mapToInt(Integer::parseInt).toArray();

        System.out.println(bookPS(n, weak, dist));
        System.out.println(myPS(n, weak, dist));
    }

    private static int bookPS(final int n, final int[] weak, final int[] dist) {
        int canInputFriends = dist.length;
        // 반환할 점검에 투입될 최소 인원
        int inputMembers = canInputFriends + 1;

        // 원형으로 나열된 데이터를 처리하는 경우
        // 문제 풀이를 간단히 하기 위해서 길이를 2배로 늘려 일자 형태로 만듦
        final List<Integer> weakPoints = new ArrayList<>();
        for (int w : weak) {
            weakPoints.add(w);
        }
        for (int w : weak) {
            weakPoints.add(w + n);
        }

        // 외벽 점검에 인원을 투입하는 모든 경우의 수를 확인 (순열의 개수를 계산)
        Permutation permutation = new Permutation(dist, canInputFriends);
        final List<List<Integer>> inputCases = permutation.getResult();

        // 모든 취약지점을 반복하면서 점검 시작 위치로 설정하여 탐색
        for (int start = 0; start < weak.length; start++) {

            // 인원을 투입하는 모든 경우의 수를 반복하며 확인
            for (List<Integer> inputCase : inputCases) {

                // 투입 시도하는 인원 수
                int tryInputMember = 1;

                // 해당 인원이 점검할 수 있는 마지막 위치
                int lastCheckPoint = weakPoints.get(start) + inputCase.get(FIRST_MEMBER);

                // 점검 시작점부터 주어진 모든 취약점까지 순회하며 점검 시도
                for (int check = start; check < (start + weak.length); check++) {

                    // 점검 가능한 위치인 경우 계속해서 점검 시도
                    if (lastCheckPoint >= weakPoints.get(check)) continue;

                    // 점검 인원 추가 투입
                    tryInputMember++;

                    // 인원 추가 투입이 불가능한 경우 종료 (전체 인원보다 큰 경우)
                    if (tryInputMember > canInputFriends) {
                        break;
                    }

                    // 추가 인원을 포함하여 점검할 수 있는 마지막 위치
                    lastCheckPoint = weakPoints.get(check) + inputCase.get(tryInputMember - 1);
                }

                // 투입 최소 인원 판별
                inputMembers = Math.min(inputMembers, tryInputMember);
            }
        }

        // 투입 인원 수가 점검 인원 수보다 큰 경우 -1 반환
        return (inputMembers > canInputFriends) ? FAIL : inputMembers;
    }

    private static int myPS(final int n, final int[] weak, final int[] dist) {
        // 복습을 위해 한 번 더 작성

        // 점검에 투입 가능한 친구 수
        final int canInputFriends = dist.length;
        // 반환할 점검에 투입될 최소 인원
        int minInputMembers = canInputFriends + 1;

        // 원형 데이터인 취약 지점 데이터를 편리한 계산을 위해 2배로 증가
        List<Integer> weakList = new ArrayList<>();
        for (int w : weak) {
            weakList.add(w);
        }
        for (int w : weak) {
            weakList.add(w + n);
        }

        // 인원 투입 모든 경우의 수 (순열)
        List<List<Integer>> inputCases = new Permutation(dist, canInputFriends).getResult();

        // 첫번째 취약 지점부터 모든 취약 지점을 순회하며 확인
        for (int start = 0; start < weak.length; start++) {

            // 인원 투입 모든 경우의 수를 순회하며 확인
            for (List<Integer> inputCase : inputCases) {

                // 투입 시도할 인원 수
                int tryInputMember = 1;

                // 투입된 인원이 점검 가능한 마지막 위치
                int lastCheckPoint = weakList.get(start) + inputCase.get(FIRST_MEMBER);

                // 일자 형태로 만든 취약 지점 목록을 순회하며 확인
                for (int check = start; check < (start + weak.length); check++) {

                    // 점검 가능한 위치라면 다음 위치로 이동
                    if (lastCheckPoint >= weakList.get(check)) continue;

                    // 투입된 인원으로 취약 지점 점검이 힘든 경우 인원 추가 투입
                    tryInputMember++;

                    // 투입 가능한 인원을 초과하여 투입되는 경우
                    if (tryInputMember > canInputFriends) break;

                    // 추가 투입된 인원을 포함하여 점검 지점 확인
                    lastCheckPoint = weakList.get(check) + inputCase.get(tryInputMember - 1);
                }

                // 기존 투입된 인원과 현재 투입된 인원의 최솟값 비교
                minInputMembers = Math.min(minInputMembers, tryInputMember);
            }
        }

        return (minInputMembers > canInputFriends) ? FAIL : minInputMembers;
    }
}

class Permutation {
    private final int[] originArr;
    private final int originArrSize;
    private final int elementQty;
    private final List<List<Integer>> result = new ArrayList<>();

    public Permutation(final int[] arr, final int elementQty) {
        this.originArr = arr;
        this.originArrSize = this.originArr.length;
        this.elementQty = elementQty;
    }

    public void permute(final int depth) {
        // 현재 순열의 단계가 조합할 원소 수와 동일해지면 저장
        if (depth == elementQty) {
            final List<Integer> inputFriends = new ArrayList<>();
            for (int j : originArr) {
                inputFriends.add(j);
            }

            result.add(inputFriends);
            return;
        }

        for (int i = depth; i < originArrSize; i++) {
            // 재귀호출 전후로 값을 바꿔주지 않으면 제대로 값이 조합되지 않음
            swap(i, depth);
            permute(depth + 1);
            swap(i, depth);
        }
    }

    private void swap(final int first, final int second) {
        final int temp = originArr[first];
        originArr[first] = originArr[second];
        originArr[second] = temp;
    }

    public List<List<Integer>> getResult() {
        if (result.size() <= 0) this.permute(0);
        return result;
    }
}
