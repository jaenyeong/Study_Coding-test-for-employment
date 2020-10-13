package com.jaenyeong.chapter_12_implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PS06_FAIL {
    /*
    틀린 이유를 아직 못 찾아 추후에 찾기 위하여 그대로 저장
     */

    private static final int PILLAR = 0; // 기둥
    private static final int BEAM = 1;   // 보
    private static final int DEMOLITION = 0; // 철거
    private static final int INSTALL = 1;    // 설치

    public static void main(String[] args) {
        // 정사각형 격자 크기
        final int n = 5;
        // 명령 횟수
//        final int m = 10;
        final int m = 8;
        // 명령
        final int[][] buildFrame = new int[m][4];

        buildFrame[0] = new int[]{1, 0, 0, 1};
        buildFrame[1] = new int[]{1, 1, 1, 1};
        buildFrame[2] = new int[]{2, 1, 0, 1};
        buildFrame[3] = new int[]{2, 2, 1, 1};
        buildFrame[4] = new int[]{5, 0, 0, 1};
        buildFrame[5] = new int[]{5, 1, 0, 1};
        buildFrame[6] = new int[]{4, 2, 1, 1};
        buildFrame[7] = new int[]{3, 2, 1, 1};

//        buildFrame[0] = new int[]{0, 0, 0, 1};
//        buildFrame[1] = new int[]{2, 0, 0, 1};
//        buildFrame[2] = new int[]{4, 0, 0, 1};
//        buildFrame[3] = new int[]{0, 1, 1, 1};
//        buildFrame[4] = new int[]{1, 1, 1, 1};
//        buildFrame[5] = new int[]{2, 1, 1, 1};
//        buildFrame[6] = new int[]{3, 1, 1, 1};
//        buildFrame[7] = new int[]{2, 0, 0, 0};
//        buildFrame[8] = new int[]{1, 1, 1, 0};
//        buildFrame[9] = new int[]{2, 2, 0, 1};

        System.out.println(Arrays.deepToString(myPS(n, buildFrame)));
    }

    private static int[][] myPS(final int n, final int[][] buildFrame) {
        // 구조물 설치 격자 크기
        final int gridSize = n + 1;

        // 설치된 기둥을 담을 배열
        final boolean[][] pillars = new boolean[gridSize][gridSize];

        // 설치된 보를 담을 배열
        final boolean[][] beams = new boolean[gridSize][gridSize];

        // 주어진 명령 반복 실행
        for (int[] commands : buildFrame) {
            final int x = commands[0];
            final int y = commands[1];
            final int structure = commands[2];
            final int whetherBuild = commands[3];

            // 구조물 설치
            if (whetherBuild == INSTALL) {
                installStructure(pillars, beams, x, y, structure);
                continue;
            }

            // 구조물 철거
            if (whetherBuild == DEMOLITION) {
                demolitionStructure(pillars, beams, x, y, structure);
            }
        }

        // 반환 타입에 맞게 결과값을 변환하여 반환
        return extractResult(gridSize, pillars, beams);
    }

    private static void installStructure(final boolean[][] pillars, final boolean[][] beams,
                                         final int x, final int y, final int structure) {
        // 기둥
        if (structure == PILLAR) {
            if (validateBuildPillar(pillars, beams, x, y)) {
                pillars[x][y] = true;
            }
            return;
        }

        // 보
        if (structure == BEAM) {
            if (validateBuildBeam(pillars, beams, x, y)) {
                beams[x][y] = true;
            }
        }
    }

    private static boolean validateBuildPillar(final boolean[][] pillars, final boolean[][] beams,
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

    private static boolean validateBuildBeam(final boolean[][] pillars, final boolean[][] beams,
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
                                            final int x, final int y, final int structure) {
        final int leftX = x - 1;
        final int leftLeftX = leftX - 1;
        final int rightX = x + 1;
        final int rightRightX = rightX + 1;
        final int upY = y + 1;
        final int bottomY = y - 1;

        // 기둥
        if (structure == PILLAR) {

            // 철거할 기둥 위에 기둥이 설치된 경우
            if ((upY < pillars.length) && (pillars[x][upY])) {
                // 기둥을 받칠 보가 설치되지 않은 경우
                if (((leftX < 0) || (!beams[leftX][upY])) && (!beams[x][upY])) {
                    return;
                }
            }

            // 철거할 기둥 위에 보가 설치된 경우
            if ((upY < pillars.length) && (beams[x][upY])) {
                // 철거할 기둥 우측에 기둥이 설치되어 있지 않으면서
                if (((rightX >= pillars.length) || (!pillars[rightX][y]))
                    // 동시에 양쪽 보가 연결되지도 않은 경우
                    && (((leftX < 0) || (!beams[leftX][upY]))) || ((rightX >= beams.length) || (!beams[rightX][upY]))) {
                    return;
                }
            }

            // 철거할 기둥 좌측 상단에 보가 설치된 경우
            if ((leftX >= 0) && (upY < pillars.length) && (beams[leftX][upY])) {
                // 철거할 기둥 좌측에 기둥이 설치되어 있지 않으면서
                if ((!pillars[leftX][y])
                    // 동시에 양쪽 보가 연결되지도 않은 경우
                    && ((leftLeftX < 0) || (!beams[leftLeftX][upY]) || (!beams[x][upY]))) {
                    return;
                }
            }

            pillars[x][y] = false;
            return;
        }

        // 보
        if (structure == BEAM) {

            // 철거할 보 상단에 기둥이 설치된 경우
            if (pillars[x][y]) {
                // 철거할 보 좌측에 보가 설치되어 있지 않으면서
                if (((leftX < 0) || (!beams[leftX][y]))
                    // 하단에 기둥이 설치되지 않은 경우
                    && ((bottomY < 0) || (!pillars[x][bottomY]))) {
                    return;
                }
            }

            // 철거할 보 우측 상단에 기둥이 설치된 경우
            if ((rightX < pillars.length) && (pillars[rightX][y])) {
                // 철거할 보 우측에 보가 설치되어 있지 않으면서
                if ((!beams[rightX][y])
                    // 하단에 기둥이 설치되지 않은 경우
                    && ((bottomY < 0) || (!pillars[rightX][bottomY]))) {
                    return;
                }
            }

            // 철거할 보 좌측에 보가 설치된 경우
            if ((leftX >= 0) && (beams[leftX][y])) {
                // 철거할 보 하단과 좌측 하단에 기둥이 설치되어 있지 않은 경우
                if ((bottomY < 0) || ((!pillars[x][bottomY]) && (!pillars[leftX][bottomY]))) {
                    return;
                }
            }

            // 철거할 보 우측에 보가 설치된 경우
            if ((rightX < beams.length) && (beams[rightX][y])) {
                // 철거할 보 우측 하단과 그 기둥에 우측에도 기둥이 설치되어 있지 않은 경우
                if ((bottomY < 0) || (
                    (!pillars[rightX][bottomY])
                        && ((rightRightX >= beams.length) || (!pillars[rightRightX][bottomY])))) {
                    return;
                }
            }

            beams[x][y] = false;
        }
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
}
