package com.jaenyeong.chapter_12_implementation;

import java.util.*;

public class PS06 {
    /*
    [Question]
    기둥과 보 설치

    [Input]
    01 line > 5
    02 line > 8
    03 line > 1 0 0 1
    04 line > 1 1 1 1
    05 line > 2 1 0 1
    06 line > 2 2 1 1
    07 line > 5 0 0 1
    08 line > 5 1 0 1
    09 line > 4 2 1 1
    10 line > 3 2 1 1
    [Output]
    > [[1, 0, 0], [1, 1, 1], [2, 1, 0], [2, 2, 1], [3, 2, 1], [4, 2, 1], [5, 0, 0], [5, 1, 0]]

    [Input]
    01 line > 5
    02 line > 10
    03 line > 0 0 0 1
    04 line > 2 0 0 1
    05 line > 4 0 0 1
    06 line > 0 1 1 1
    07 line > 1 1 1 1
    08 line > 2 1 1 1
    09 line > 3 1 1 1
    10 line > 2 0 0 0
    11 line > 1 1 1 0
    12 line > 2 2 0 1
    [Output]
    > [[0, 0, 0], [0, 1, 1], [1, 1, 1], [2, 1, 1], [3, 1, 1], [4, 0, 0]]

    [입력 조건]
    1 line > 정사각형 격자의 한 면 크기 (5 <= n <= 100)
    2 line > 명령 횟수 (1 <= command <= 1,000)
    (2 + command) line > 각 명령 [x, y, a, b]
                         x, y는 교차점 좌표 (각각 가로, 세로)
                         a는 구조물의 종류 (기둥 = 0, 보 = 1)
                         b는 설치 여부 (삭제 = 0, 설치 = 1)
    추가 설명
    - 구조물 설치는 격자 영역을 벗어날 수 없음
    - 바닥에 보를 설치할 수 없음
    - 구조물은 교차점 좌표를 기준으로 보는 오른쪽, 기둥은 위쪽 방향으로 설치 또는 제거
    - 결과 반환은 아래와 같음
      - [x, y, a] 형태의 배열
      - x, y는 구조물의 교차점 좌표 (각각 가로, 세로)
      - a는 구조물의 종류 (기둥 = 0, 보 = 1)
      - 순서는 가로, 세로, 구조물 순으로 오름차순 정렬

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int PILLAR = 0; // 기둥
    private static final int BEAM = 1;   // 보

    private static final int DEMOLITION = 0; // 철거
    private static final int INSTALL = 1;    // 설치

    public static void main(String[] args) {
        // 정사각형 격자 크기
        System.out.println("정사각형 격자 크기를 입력하세요");
        final int n = SC.nextInt();

        // 명령 횟수
        System.out.println("명령 횟수를 입력하세요");
        final int m = SC.nextInt();;

        // 명령
        final int[][] buildFrame = new int[m][4];

        for (int i = 0; i < m; i++) {
            System.out.println((i + 1) + " 번째 명령을 입력하세요");
            for (int j = 0; j < 4; j++) {
                buildFrame[i][j] = SC.nextInt();
            }
            SC.nextLine();
        }

        System.out.println(Arrays.deepToString(myPS(n, buildFrame)));
        System.out.println(Arrays.deepToString(bookPS(n, buildFrame)));
    }

    private static int[][] myPS(final int n, final int[][] buildFrame) {
        // 구조물 설치 격자 크기
        final int gridSize = n + 1;
        final boolean[][] pillars = new boolean[gridSize][gridSize];
        final boolean[][] beams = new boolean[gridSize][gridSize];

        // 주어진 명령 반복 실행
        for (int[] commands : buildFrame) {
            final int x = commands[0];
            final int y = commands[1];
            final int structure = commands[2];
            final int whetherBuild = commands[3];

            if (isNotValidRange(x, y, gridSize)) {
                continue;
            }

            // 구조물 설치
            if (whetherBuild == INSTALL) {
                installStructure(pillars, beams, x, y, structure);
                continue;
            }

            // 구조물 철거
            if (whetherBuild == DEMOLITION) {
                demolitionStructure(pillars, beams, x, y, structure, gridSize);
            }
        }

        return extractResult(gridSize, pillars, beams);
    }

    private static boolean isNotValidRange(final int x, final int y, final int gridSize) {
        return (x < 0) || (x >= gridSize)
            || (y < 0) || (y >= gridSize);
    }

    private static void installStructure(final boolean[][] pillars, final boolean[][] beams,
                                         final int x, final int y, final int structure) {
        // 기둥
        if (structure == PILLAR) {
            if (validateInstallPillar(pillars, beams, x, y)) {
                pillars[x][y] = true;
            }
            return;
        }

        // 보
        if (structure == BEAM) {
            if (validateInstallBeam(pillars, beams, x, y)) {
                beams[x][y] = true;
            }
        }
    }

    private static boolean validateInstallPillar(final boolean[][] pillars, final boolean[][] beams,
                                                 final int x, final int y) {
        // 바닥에 설치하는 경우
        if (y == 0) return true;

        // 기둥 위에 설치하는 경우
        final int bottomY = y - 1;
        if ((pillars[x][bottomY])) return true;

        // 보 바로 위에 설치하는 경우
        if ((beams[x][y])) return true;

        // 좌측 하단 보 위에 설치하는 경우
        final int leftX = x - 1;
        return (leftX >= 0) && (beams[leftX][y]);
    }

    private static boolean validateInstallBeam(final boolean[][] pillars, final boolean[][] beams,
                                               final int x, final int y) {
        // 기둥 위에 설치하는 경우
        final int bottomY = y - 1;
        if ((bottomY >= 0) && (pillars[x][bottomY])) return true;

        // 우측 하단 기둥 위에 설치하는 경우
        final int rightX = x + 1;
        if ((rightX < pillars.length) && (bottomY >= 0) && (pillars[rightX][bottomY])) return true;

        // 양쪽 끝부분이 다른 보와 연결되게 설치하는 경우
        final int leftX = x - 1;
        return (leftX >= 0) && (beams[leftX][y])
            && (rightX < pillars.length) && (beams[rightX][y]);
    }

    private static void demolitionStructure(final boolean[][] pillars, final boolean[][] beams,
                                            final int x, final int y, final int structure, final int gridSize) {
        if (structure == PILLAR) {
            // 기둥 철거 유효성 확인
            if (isValidDemolitionPillar(pillars, beams, x, y, gridSize)) {
                pillars[x][y] = false;
            }

            return;
        }

        if (structure == BEAM) {
            // 보 철거 유효성 확인
            if (isValidDemolitionBeam(pillars, beams, x, y, gridSize)) {
                beams[x][y] = false;
            }
        }
    }

    private static boolean isValidDemolitionPillar(final boolean[][] pillars, final boolean[][] beams,
                                                   final int x, final int y, final int gridSize) {
        final int leftX = x - 1;
        final int rightX = x + 1;
        final int upY = y + 1;
        final int leftLeftX = x - 2;

        return checkUpPillar(pillars, beams, x, gridSize, leftX, upY)
            && checkUpBeam(pillars, beams, x, y, gridSize, leftX, rightX, upY)
            && checkLeftUpBeam(pillars, beams, x, y, gridSize, leftX, upY, leftLeftX);
    }

    private static boolean checkUpPillar(final boolean[][] pillars, final boolean[][] beams,
                                         final int x, final int gridSize, final int leftX, final int upY) {
        return !isValidPillar(pillars, x, upY, gridSize)
            || isValidBeam(beams, leftX, upY, gridSize)
            || isValidBeam(beams, x, upY, gridSize);
    }

    private static boolean isValidPillar(final boolean[][] pillars, final int x, final int y, final int gridSize) {
        return (x >= 0) && (x < gridSize)
            && (y >= 0) && (y < gridSize)
            && pillars[x][y];
    }

    private static boolean isValidBeam(final boolean[][] beams, final int x, final int y, final int gridSize) {
        return (x >= 0) && (x < gridSize)
            && (y >= 0) && (y < gridSize)
            && beams[x][y];
    }

    private static boolean checkUpBeam(final boolean[][] pillars, final boolean[][] beams, final int x, final int y,
                                       final int gridSize, final int leftX, final int rightX, final int upY) {
        return !isValidBeam(beams, x, upY, gridSize)
            || isValidPillar(pillars, rightX, y, gridSize)
            || (isValidBeam(beams, leftX, upY, gridSize) && isValidBeam(beams, rightX, upY, gridSize));
    }

    private static boolean checkLeftUpBeam(final boolean[][] pillars, final boolean[][] beams, final int x, final int y,
                                           final int gridSize, final int leftX, final int upY, final int leftLeftX) {
        return !isValidBeam(beams, leftX, upY, gridSize)
            || (isValidPillar(pillars, leftX, y, gridSize)
            || (isValidBeam(beams, leftLeftX, upY, gridSize) && isValidBeam(beams, x, upY, gridSize)));
    }

    private static boolean isValidDemolitionBeam(final boolean[][] pillars, final boolean[][] beams,
                                                 final int x, final int y, final int gridSize) {
        final int leftX = x - 1;
        final int rightX = x + 1;
        final int rightRightX = rightX + 1;
        final int bottomY = y - 1;

        return isValidBeam(beams, x, y, gridSize)
            && checkUpPillarForBeam(pillars, beams, x, y, gridSize, leftX, bottomY)
            && checkRightUpPillarForBeam(pillars, beams, y, gridSize, rightX, bottomY)
            && checkLeftBeamForBeam(pillars, beams, x, y, gridSize, leftX, bottomY)
            && checkRightBeamForBeam(pillars, beams, y, gridSize, rightX, rightRightX, bottomY);
    }

    private static boolean checkUpPillarForBeam(final boolean[][] pillars, final boolean[][] beams, final int x, final int y,
                                                final int gridSize, final int leftX, final int bottomY) {
        return !isValidPillar(pillars, x, y, gridSize)
            || (isValidBeam(beams, leftX, y, gridSize)
            || isValidPillar(pillars, x, bottomY, gridSize));
    }

    private static boolean checkRightUpPillarForBeam(final boolean[][] pillars, final boolean[][] beams,
                                                     final int y, final int gridSize,
                                                     final int rightX, final int bottomY) {
        return !isValidPillar(pillars, rightX, y, gridSize)
            || isValidBeam(beams, rightX, y, gridSize)
            || isValidPillar(pillars, rightX, bottomY, gridSize);
    }

    private static boolean checkLeftBeamForBeam(final boolean[][] pillars, final boolean[][] beams,
                                                final int x, final int y, final int gridSize,
                                                final int leftX, final int bottomY) {
        return !isValidBeam(beams, leftX, y, gridSize)
            || (isValidPillar(pillars, leftX, bottomY, gridSize)
            || isValidPillar(pillars, x, bottomY, gridSize));
    }

    private static boolean checkRightBeamForBeam(final boolean[][] pillars, final boolean[][] beams,
                                                 final int y, final int gridSize,
                                                 final int rightX, final int rightRightX, final int bottomY) {
        return !isValidBeam(beams, rightX, y, gridSize)
            || (isValidPillar(pillars, rightX, bottomY, gridSize)
            || isValidPillar(pillars, rightRightX, bottomY, gridSize));
    }

    private static int[][] extractResult(final int gridSize, final boolean[][] pillars, final boolean[][] beams) {
        final List<int[]> buildList = new ArrayList<>();

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (pillars[x][y]) buildList.add(new int[]{x, y, PILLAR});
                if (beams[x][y]) buildList.add(new int[]{x, y, BEAM});
            }
        }

        final int[][] result = new int[buildList.size()][3];
        for (int i = 0; i < result.length; i++) {
            result[i] = buildList.get(i);
        }

        return result;
    }

    private static int[][] bookPS(final int n, final int[][] buildFrame) {
        final List<List<Integer>> answer = new ArrayList<>();

        // 작업(frame)의 개수 최대 1,000개
        for (int[] commands : buildFrame) {
            final int commandX = commands[0];
            final int commandY = commands[1];
            final int commandStuff = commands[2];
            final int operate = commands[3];

            // 삭제
            if (operate == DEMOLITION) {

                // 실행된 작업을 반복하며 수행
                int index = 0;
                for (int j = 0; j < answer.size(); j++) {
                    final int compareX = answer.get(j).get(0);
                    final int compareY = answer.get(j).get(1);
                    final int compareStuff = answer.get(j).get(2);

                    if ((commandX == compareX) && (commandY == compareY) && (commandStuff == compareStuff)) {
                        index = j;
                    }
                }

                // 먼저 철거 작업 후
                final List<Integer> erased = answer.get(index);
                answer.remove(index);

                // 유효성 검증 실패시 철거 작업 복구
                if (isNotValidate(answer)) {
                    answer.add(erased);
                }

                continue;
            }

            // 설치
            if (operate == INSTALL) {
                final List<Integer> inserted = new ArrayList<>();
                inserted.add(commandX);
                inserted.add(commandY);
                inserted.add(commandStuff);

                // 먼저 설치 작업 후
                answer.add(inserted);

                // 유효성 검증 실패시 설치 작업 복구
                if (isNotValidate(answer)) {
                    answer.remove(answer.size() - 1);
                }
            }
        }

        return createResult(answer);
    }

    private static boolean isNotValidate(final List<List<Integer>> answer) {
        // 작업 순서를 반복하며 확인
        for (int i = 0; i < answer.size(); i++) {
            final int x = answer.get(i).get(0);
            final int y = answer.get(i).get(1);
            final int stuff = answer.get(i).get(2);

            // 기둥 확인
            if (stuff == PILLAR) {
                boolean check = false;

                // 바닥 바로 위면 통과
                if (y == 0) check = true;

                for (List<Integer> integers : answer) {
                    final int compareX = integers.get(0);
                    final int compareY = integers.get(1);
                    final int compareStuff = integers.get(2);

                    // 검사할 기둥 좌측 하단에 보가 설치된 경우
                    if ((x - 1 == compareX) && (y == compareY) && (BEAM == compareStuff)) {
                        check = true;
                    }

                    // 검사할 기둥 하단에 보가 설치된 경우
                    if ((x == compareX) && (y == compareY) && (BEAM == compareStuff)) {
                        check = true;
                    }

                    // 검사할 기둥 하단에 기둥이 설치된 경우
                    if ((x == compareX) && (y - 1 == compareY) && (PILLAR == compareStuff)) {
                        check = true;
                    }
                }

                if (!check) return true;
            }

            // 보 확인
            if (stuff == BEAM) {
                boolean check = false;
                boolean left = false;
                boolean right = false;

                // 한쪽 끝부분이 기둥 위거나 양쪽 끝부분이 다른 보와 동시에 연결된 상태인 경우 통과
                for (List<Integer> integers : answer) {
                    final int compareX = integers.get(0);
                    final int compareY = integers.get(1);
                    final int compareStuff = integers.get(2);

                    // 확인할 보 하단에 기둥이 설치된 경우
                    if ((x == compareX) && (y - 1 == compareY) && (PILLAR == compareStuff)) {
                        check = true;
                    }

                    // 확인할 보 우측 하단에 기둥이 설치된 경우
                    if ((x + 1 == compareX) && (y - 1 == compareY) && (PILLAR == compareStuff)) {
                        check = true;
                    }

                    // 확인할 보 좌측에 보가 설치된 경우
                    if ((x - 1 == compareX) && (y == compareY) && (BEAM == compareStuff)) {
                        left = true;
                    }

                    // 확인할 보 우측에 보가 설치된 경우
                    if ((x + 1 == compareX) && (y == compareY) && (BEAM == compareStuff)) {
                        right = true;
                    }
                }

                if (left && right) check = true;
                if (!check) return true;
            }
        }

        return false;
    }

    private static int[][] createResult(final List<List<Integer>> answer) {
        final List<BuildNode> result = new ArrayList<>();

        for (List<Integer> integers : answer) {
            final int row = integers.get(0);
            final int col = integers.get(1);
            final int stuff = integers.get(2);

            result.add(new BuildNode(row, col, stuff));
        }

        // 결과 정렬 수행
        Collections.sort(result);

        // 배열로 변환하여 반환
        final int[][] arrResult = new int[result.size()][3];
        for (int i = 0; i < result.size(); i++) {
            arrResult[i][0] = result.get(i).getX();
            arrResult[i][1] = result.get(i).getY();
            arrResult[i][2] = result.get(i).getStuff();
        }
        return arrResult;
    }
}

class BuildNode implements Comparable<BuildNode> {
    private final int x;
    private final int y;
    private final int stuff;

    public BuildNode(int x, int y, int stuff) {
        this.x = x;
        this.y = y;
        this.stuff = stuff;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStuff() {
        return stuff;
    }

    // x, y, stuff 순으로 오름차순 정렬
    @Override
    public int compareTo(BuildNode o) {
        if (this.x == o.x && this.y == o.y) return Integer.compare(this.stuff, o.stuff);
        if (this.x == o.x) return Integer.compare(this.y, o.y);
        return Integer.compare(this.x, o.x);
    }
}
