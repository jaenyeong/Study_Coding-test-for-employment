package com.jaenyeong.chapter_12_implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiPredicate;

public class PS07 {
    /*
    [Question]
    치킨 배달

    [Input]
    1 line > 5 3
    2 line > 0 0 1 0 0
    3 line > 0 0 2 0 1
    4 line > 0 1 2 0 0
    5 line > 0 0 1 0 0
    6 line > 0 0 0 0 2
    [Output]
    > 5

    [Input]
    1 line > 5 2
    2 line > 0 2 0 1 0
    3 line > 1 0 1 0 0
    4 line > 0 0 0 0 0
    5 line > 2 0 0 1 1
    6 line > 2 2 0 1 2
    [Output]
    > 10

    [Input]
    1 line > 5 1
    2 line > 1 2 0 0 0
    3 line > 1 2 0 0 0
    4 line > 1 2 0 0 0
    5 line > 1 2 0 0 0
    6 line > 1 2 0 0 0
    [Output]
    > 11

    [Input]
    1 line > 5 1
    2 line > 1 2 0 2 1
    3 line > 1 2 0 2 1
    4 line > 1 2 0 2 1
    5 line > 1 2 0 2 1
    6 line > 1 2 0 2 1
    [Output]
    > 32

    [입력 조건]
     1 line > 정사각형 형태의 도시 크기 (2 <= n <= 50)
              가장 수익을 많이 낼 수 있는(폐업시키지 않을) 치킨집의 개수 (1 <= m <= 13)
     2 ~ (1 + n) line > 도시의 정보
                        빈 칸(0), 집(1), 치킨가게(2)으로만 구성
                        집 개수 (1 <= h <= 2n)
                        치킨집 개수 (m <= c <= 13)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int HOUSE = 1;
    private static final int CHICKEN = 2;

    private static final int INF = (int) 1e9;

    public static void main(String[] args) {
        System.out.println("정사각형 형태의 도시의 크기와 폐업시키지 않고 남길 치킨집의 개수를 입력하세요");
        final int n = SC.nextInt();
        final int m = SC.nextInt();

        // bookPS
        final List<Point> bookChickens = new ArrayList<>();
        final List<Point> bookHouses = new ArrayList<>();

        final int[][] city = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + " 번째 행의 도시 정보를 입력하세요");
            for (int j = 0; j < n; j++) {
                city[i][j] = SC.nextInt();

                // bookPS
                if (city[i][j] == HOUSE) bookHouses.add(new Point(i, j));
                if (city[i][j] == CHICKEN) bookChickens.add(new Point(i, j));
            }
        }

        System.out.println(myPS(city, n, m));
        System.out.println(bookPS(bookChickens, bookHouses, n, m));
    }

    private static int myPS(final int[][] city, final int n, final int m) {
        // 집 목록 추출
        final List<Point> houses = extractPointList(n, (p1, p2) -> city[p1][p2] == HOUSE);

        // 치킨가게 목록 추출
        final List<Point> chickens = extractPointList(n, (p1, p2) -> city[p1][p2] == CHICKEN);

        // 주어진 m의 수대로 치킨가게 조합 목록 생성
        myCombination myCombination = new myCombination(chickens, m);
        myCombination.combination(0, 0, 0);
        final List<List<Point>> combChickens = myCombination.getResult();

        return getMinSumDistance(combChickens, houses);
    }

    private static int getMinSumDistance(final List<List<Point>> combChickens, final List<Point> houses) {
        int result = INF;

        for (List<Point> chickens : combChickens) {
            int tempSumDistance = 0;
            // 집 목록만큼 반복
            for (Point house : houses) {
                int distance = INF;
                // 각 치킨가게를 순회하며 거리 계산
                for (Point chicken : chickens) {
                    final int rowDistance = Math.abs(house.getRow() - chicken.getRow());
                    final int colDistance = Math.abs(house.getCol() - chicken.getCol());
                    distance = Math.min(distance, (rowDistance + colDistance));
                }
                tempSumDistance += distance;
            }

            result = Math.min(result, tempSumDistance);
        }

        return result;
    }

    private static List<Point> extractPointList(final int limit, final BiPredicate<Integer, Integer> condition) {
        final List<Point> points = new ArrayList<>();
        for (int row = 0; row < limit; row++) {
            for (int col = 0; col < limit; col++) {
                if (condition.test(row, col)) {
                    points.add(new Point(row, col));
                }
            }
        }

        return points;
    }

    private static int bookPS(final List<Point> chickens, final List<Point> houses, final int n, final int m) {
        // 모든 치킨집 중 m개의 치킨집 조합을 추출
        BookCombination bookCombination = new BookCombination(chickens.size(), m);
        bookCombination.combination(chickens, 0, 0, 0);
        List<List<Point>> combChickens = bookCombination.getResult();

        int result = INF;
        for (List<Point> combChicken : combChickens) {
            result = Math.min(result, getSum(combChicken, houses));
        }

        return result;
    }

    private static int getSum(final List<Point> chickens, final List<Point> houses) {
        int result = 0;
        // 모든 집을 순회
        for (Point house : houses) {
            final int hRow = house.getRow();
            final int hCol = house.getCol();

            // 가장 가까운 치킨집 찾기
            int tempDistance = INF;
            for (Point chicken : chickens) {
                final int cRow = chicken.getRow();
                final int cCol = chicken.getCol();

                // 계산한 거리와 기존 거리 중 작은 값을 삽입
                tempDistance = Math.min(tempDistance, (Math.abs(hRow - cRow) + Math.abs(hCol - cCol)));
            }
            result += tempDistance;
        }

        return result;
    }
}

class Point {
    private final int row;
    private final int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Point{" +
            "row=" + row +
            ", col=" + col +
            '}';
    }
}

class myCombination {
    // 기존 배열
    private final List<Point> originPoints;
    // 기존 배열의 크기
    private final int arrSize;
    // 조합시 원소의 개수
    private final int limit;
    // 현재까지 추출한 인덱스 배열
    private final int[] currIdxArr;
    // 모든 조합
    private final List<List<Point>> result = new ArrayList<>();

    // myPS
    public myCombination(final List<Point> points, final int limit) {
        this.originPoints = points;
        this.arrSize = this.originPoints.size();
        this.limit = limit;
        this.currIdxArr = new int[limit];
    }

    public void combination(final int depth, final int saveIdx, final int targetIdx) {
        // 원소 개수 만큼 현재 조합 배열에 담긴 경우
        if (depth == limit) {
            final List<Point> tempComb = new ArrayList<>();
            for (int i : currIdxArr) {
                final Point point = originPoints.get(i);
                tempComb.add(point);
            }
            result.add(tempComb);
            return;
        }

        // 배열 끝까지 탐색한 경우
        if (targetIdx == arrSize) return;

        // 조합 생성시 사용하는 인덱스 배열에 해당 인덱스를 저장
        currIdxArr[saveIdx] = targetIdx;

        // 해당 조합 다음 순서를 조합
        combination(depth + 1, saveIdx + 1, targetIdx + 1);
        // 해당 순서에서 다음 원소를 조합
        combination(depth, saveIdx, targetIdx + 1);
    }

    public List<List<Point>> getResult() {
        return result;
    }
}

class BookCombination {
    private final int n;
    private final int r;
    private final int[] now; // 현재 조합
    private final List<List<Point>> result; // 모든 조합

    public BookCombination(final int n, final int r) {
        this.n = n;
        this.r = r;
        this.now = new int[r];
        this.result = new ArrayList<>();
    }

    public void combination(List<Point> arr, int depth, int index, int target) {
        if (depth == r) {
            final List<Point> temp = new ArrayList<>();
            for (int i : now) {
                temp.add(arr.get(i));
            }
            result.add(temp);
            return;
        }

        if (target == n) return;

        now[index] = target;
        combination(arr, depth + 1, index + 1, target + 1);
        combination(arr, depth, index, target + 1);
    }

    public List<List<Point>> getResult() {
        return result;
    }
}
