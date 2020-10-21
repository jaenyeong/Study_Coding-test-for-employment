package com.jaenyeong.chapter_13_dfs_bfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PS06 {
    /*
    [Question]
    감시피하기

    [Input]
    1 line > 5
    2 line > X S X X T
    3 line > T X S X X
    4 line > X X X X X
    5 line > X T X X X
    6 line > X X T X X
    [Output]
    > YES

    [Input]
    1 line > 4
    2 line > S S S T
    3 line > X X X X
    4 line > X X X X
    5 line > T T T X
    [Output]
    > NO

    [입력 조건]
    1 line > 정사각형 형태의 복도 크기 (3 <= n <= 6)
    2 ~ (1 + n) line > 복도의 각 행 정보 (학생 = S, 선생님 = T, EMPTY = X)

                       빈칸의 개수는 항상 3개 이상

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int BUILD_LIMIT = 3;

    private static final char STUDENT = 'S';
    private static final char TEACHER = 'T';
    private static final char EMPTY = 'X';
    private static final char WALL = 'W';

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    public static void main(String[] args) {
        System.out.println("복도의 크기를 입력하세요");
        final int n = SC.nextInt();

        final char[][] hallway = new char[n][n];
        final List<ZoneOfHallway> pointsOfTeachers = new ArrayList<>();
        final List<ZoneOfHallway> pointsOfEmptyZones = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 복도 행 정보를 입력하세요");
            for (int j = 0; j < n; j++) {
                hallway[i][j] = SC.next().charAt(0);

                // 선생님이 있는 공간의 경우 따로 보관
                final char whatIsInZone = hallway[i][j];
                if (whatIsInZone == TEACHER) {
                    pointsOfTeachers.add(new ZoneOfHallway(i, j));
                    continue;
                }

                // 빈 공간인 경우 따로 보관
                if (whatIsInZone == EMPTY) {
                    pointsOfEmptyZones.add(new ZoneOfHallway(i, j));
                }
            }
        }

        bookPS(hallway, n, pointsOfTeachers, pointsOfEmptyZones);
    }

    private static void bookPS(final char[][] hallway, final int n,
                               final List<ZoneOfHallway> pointsOfTeachers, final List<ZoneOfHallway> pointsOfEmptyZones) {

        // 빈 공간에 칸막이 3개를 설치하는 모든 경우의 수
        BuildCombination bc = new BuildCombination(pointsOfEmptyZones, BUILD_LIMIT);
        final List<List<ZoneOfHallway>> casesOfAllEnableBuild = bc.getAllComb();

        // 학생들을 발견했는지 여부
        boolean hasNotWatchedStudents = false;

        // 모든 경우의 수를 하나씩 순회하며 확인
        for (List<ZoneOfHallway> caseOfEnableBuild : casesOfAllEnableBuild) {
            fillTheZoneByWhat(WALL, caseOfEnableBuild, hallway);

            // 선생님들이 학생을 1명도 발견하지 못한 경우
            if (hasNotWatchedStudents(pointsOfTeachers, hallway, n)) {
                hasNotWatchedStudents = true;
                break;
            }

            // 그 외 설치된 칸막이 철거
            fillTheZoneByWhat(EMPTY, caseOfEnableBuild, hallway);
        }

        System.out.println(hasNotWatchedStudents ? "YES" : "NO");
    }

    private static void fillTheZoneByWhat(final char task, final List<ZoneOfHallway> caseOfEnableBuild, final char[][] hallway) {
        // 해당 경우의 수에 해당하는 모든 빈 공간을 하나씩 방문
        for (ZoneOfHallway zone : caseOfEnableBuild) {
            final int row = zone.getRow();
            final int col = zone.getCol();
            // 해당 위치에 칸막이(장애물) 설치 또는 철거
            hallway[row][col] = task;
        }
    }

    private static boolean hasNotWatchedStudents(final List<ZoneOfHallway> pointsOfTeachers, final char[][] hallway, final int n) {
        for (ZoneOfHallway teacher : pointsOfTeachers) {
            final int row = teacher.getRow();
            final int col = teacher.getCol();

            // 상하좌우 방향으로 학생 찾기
            for (int direction = 0; direction < 4; direction++) {
                // 학생을 찾은 경우 false 반환
                if (watchStudent(row, col, direction, hallway, n)) {
                    return false;
                }
            }
        }

        // 학생을 찾지 못한 경우 true 반환
        return true;
    }

    private static boolean watchStudent(int row, int col, final int direction, final char[][] hallway, final int n) {
        // 상
        if (direction == UP) {
            while (col < n) {
                // 학생을 발견한 경우
                if (watchSomethingThat(STUDENT, hallway, row, col)) return true;

                // 칸막이가 설치되어 있는 경우
                if (watchSomethingThat(WALL, hallway, row, col)) return false;

                // 그 외 계속 라인 확인
                col++;
            }
        }

        // 하
        if (direction == DOWN) {
            while (col >= 0) {
                if (watchSomethingThat(STUDENT, hallway, row, col)) return true;

                if (watchSomethingThat(WALL, hallway, row, col)) return false;

                col--;
            }
        }

        // 좌
        if (direction == LEFT) {
            while (row >= 0) {
                if (watchSomethingThat(STUDENT, hallway, row, col)) return true;

                if (watchSomethingThat(WALL, hallway, row, col)) return false;

                row--;
            }
        }

        // 우
        if (direction == RIGHT) {
            while (row < n) {
                if (watchSomethingThat(STUDENT, hallway, row, col)) return true;

                if (watchSomethingThat(WALL, hallway, row, col)) return false;

                row++;
            }
        }

        return false;
    }

    private static boolean watchSomethingThat(final char something, final char[][] hallway, final int row, final int col) {
        return hallway[row][col] == something;
    }
}

class BuildCombination {
    private final List<ZoneOfHallway> pointsOfEmptySpaces;
    // 순열 조합을 생성할 배열의 크기
    private final int arrSize;
    // 순열을 구성할 배열 원소 개수
    private final int elementLimit;
    // 현재까지 구성된 순열의 원소 인덱스
    private final int[] currentIdxes;
    // 지정한 원소 개수만큼 조합을 이루는 경우의 수
    private final List<List<ZoneOfHallway>> allComb;

    public BuildCombination(final List<ZoneOfHallway> pointsOfEmptySpaces, final int elementLimit) {
        this.pointsOfEmptySpaces = pointsOfEmptySpaces;
        this.arrSize = this.pointsOfEmptySpaces.size();
        this.elementLimit = elementLimit;
        this.currentIdxes = new int[elementLimit];
        this.allComb = new ArrayList<>();
    }

    public List<List<ZoneOfHallway>> getAllComb() {
        this.combination(0, 0, 0);
        return allComb;
    }

    private void combination(final int currStep, final int currArrIdx, final int targetIdx) {
        // 현재 진입한 단계와 순열 조합시 원소의 개수가 일치하는 경우
        if (currStep == elementLimit) {
            // 현재 조합을 담을 객체 초기화
            final List<ZoneOfHallway> currComb = new ArrayList<>();
            for (int i : currentIdxes) {
                currComb.add(pointsOfEmptySpaces.get(i));
            }

            allComb.add(currComb);
            return;
        }

        // 해당 인덱스가 마지막 인덱스인 경우 종료
        if (targetIdx == arrSize) return;

        // 현재 인덱스를 담고 있는 배열에 해당 인덱스를 적재
        currentIdxes[currArrIdx] = targetIdx;
        // 다음번 순서의 원소를 조합
        this.combination(currStep + 1, currArrIdx + 1, targetIdx + 1);
        // 해당 순서의 다음 원소를 조합
        this.combination(currStep, currArrIdx, targetIdx + 1);
    }
}

class ZoneOfHallway {
    private final int row;
    private final int col;

    public ZoneOfHallway(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
