package com.jaenyeong.chapter_12_implementation;

import java.util.Scanner;

public class PS04 {
    /*
    [Question]
    자물쇠와 열쇠

    [Input]
    1 line > 3
    2 line > 0 0 0
    3 line > 1 0 0
    4 line > 0 1 1
    5 line > 3
    6 line > 1 1 1
    7 line > 1 1 0
    8 line > 1 0 1
    [Output]
    > true

    [입력 조건]
    1 line > 정사각형 형태의 2차원 배열의 키의 크기 (3 <= m <= 20)
             키는 항상 자물쇠 크기보다 작음
    2 ~ (1 + m) line > 홈과 돌기로만 구성 (0 = 홈, 1 = 돌기)

    (2 + m) line > 정사각형 형태의 2차원 배열의 자물쇠 (3 <= n <= 20)
    (3 + m) ~ (2 + m + n) line > 홈과 돌기로만 구성 (0 = 홈, 1 = 돌기)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final String KEY = "key";
    private static final String LOCK = "lock";

    public static void main(String[] args) {
        System.out.println("정사각형 형태의 2차원 배열, 키의 크기를 입력하세요");
        final int n = SC.nextInt();
        SC.nextLine();
        final int[][] key = inputArray(n, KEY);

        System.out.println("정사각형 형태의 2차원 배열, 자물쇠의 크기를 입력하세요");
        final int m = SC.nextInt();
        SC.nextLine();
        final int[][] lock = inputArray(m, LOCK);

        System.out.println(myPS(key, lock));
        System.out.println(bookPS(key, lock));
    }

    private static int[][] inputArray(final int size, final String arrName) {
        final int[][] arr = new int[size][size];

        for (int i = 0; i < size; i++) {
            System.out.println(arrName + "의 " + (i + 1) + " 번째 행을 입력하세요");
            for (int j = 0; j < size; j++) {
                arr[i][j] = SC.nextInt();
            }
            SC.nextLine();
        }

        return arr;
    }

    private static boolean myPS(final int[][] key, final int[][] lock) {
        /*
        [해결 방법]
        > 자물쇠 홈과 열쇠의 돌기가 일치하는지 여부를 판단해야 함
        > 1) 이 때 기존 자물쇠 크기의 3배에 달하는 새 자물쇠 2차원 배열을 생성
             - 열쇠는 자물쇠 크기와 같거나 이하, 따라서 자물쇠를 기준으로 크기 설정
        > 2) 새 자물쇠 기준으로 열쇠 배열을 순차적으로 이동하며 일치 여부 판단
             - 1) 새 자물쇠 배열 정중앙에 기존 자물쇠 배열의 값을 삽입
             - 2) 키 배열을 새 자물쇠 배열 안에 위치를 변경하며 삽입시켜 일치 여부 확인
             - 3) 새 자물쇠 배열 안에 기존 자물쇠 배열의 값이 삽입된 위치를 기준으로 이동시키며 적용
             - 4) 일치 여부는 두 배열의 값을 더해 새 자물쇠 배열 내 자물쇠 영역에 있는 모든 값이 1인지 확인
         */

        // 회전하지 않고 자물쇠와 키의 일치 여부 확인
        int[][] turnedKey = key;
        if (openTheLock(turnedKey, lock)) return true;

        // 총 세 번의 90도 회전을 반복하며 키를 이동시키며 확인
        for (int i = 90; i < 360; i += 90) {
            // 시계방향으로 키를 회전
            turnedKey = turnClockwise(turnedKey);
//            final int[][] turnedKey = turnCounterClockwise(key);

            // 회전한 키와 자물쇠의 일치 여부 확인
            if (openTheLock(turnedKey, lock)) return true;
        }

        return false;
    }

    // 시계 방향으로 90도 회전
    private static int[][] turnClockwise(final int[][] squareArr) {
        final int size = squareArr.length;
        final int[][] turnedArr = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                turnedArr[i][j] = squareArr[size - 1 - j][i];
            }
        }

        return turnedArr;
    }

    // 반시계 방향으로 90도 회전
    private static int[][] turnCounterClockwise(final int[][] squareArr) {
        final int size = squareArr.length;
        final int[][] turnedArr = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                turnedArr[i][j] = squareArr[j][size - 1 - i];
            }
        }

        return turnedArr;
    }

    private static boolean openTheLock(final int[][] key, final int[][] lock) {
        final int keySize = key.length;
        final int lockSize = lock.length;
        final int loopLimit = lockSize * 2;
        final int startPoint = lockSize - keySize + 1;

        // 새 자물쇠에 위치 변경을 반복하며 키 삽입 시도
        for (int row = startPoint; row < loopLimit; row++) {
            for (int col = startPoint; col < loopLimit; col++) {

                // 해결시 사용할 자물쇠 배열을 생성, 기존 자물쇠 배열 값을 삽입하여 초기화
                final int[][] newLock = initNewLock(lock);

                // 키의 돌쇠 값만 새 자물쇠 배열에 삽입처리
                insertKey(key, newLock, keySize, row, col);

                // 자물쇠가 열리는지(모두 1로 구성되어 있는지) 확인
                if (validateOpenTheLock(newLock)) return true;
            }
        }

        return false;
    }

    private static int[][] initNewLock(final int[][] lock) {
        final int size = lock.length;
        // 새 배열 정중앙에 기존 배열 값을 위치 시키기 위해 기존 배열의 3배 크기로 생성
        final int[][] newLock = new int[size * 3][size * 3];
        for (int i = 0; i < size; i++) {
            // 새 자물쇠 배열 정중앙 위치에 기존 자물쇠 배열 값을 깊은 복사
            System.arraycopy(lock[i], 0, newLock[i + size], size, size);
        }

        return newLock;
    }

    private static void insertKey(final int[][] key, final int[][] lockForSolve, final int keySize,
                                  final int row, final int col) {
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < keySize; j++) {
                lockForSolve[row + i][col + j] += key[i][j];
            }
        }
    }

    private static boolean validateOpenTheLock(final int[][] lock) {
        final int start = lock.length / 3;
        for (int i = start; i < start * 2; i++) {
            for (int j = start; j < start * 2; j++) {
                if (lock[i][j] != 1) return false;
            }
        }

        return true;
    }

    private static boolean bookPS(int[][] key, int[][] lock) {
        final int lockSize = lock.length;
        final int keySize = key.length;

        // 자물쇠의 크기를 기존의 3배로 변환
        final int[][] newLock = new int[lockSize * 3][lockSize * 3];

        // 새로운 자물쇠 정중앙에 기존의 자물쇠 값 삽입
        for (int i = 0; i < lockSize; i++) {
            System.arraycopy(lock[i], 0, newLock[i + lockSize], lockSize, lockSize);
        }

        // 4가지 방향에 대해서 확인
        for (int rotation = 0; rotation < 4; rotation++) {
            // 열쇠 90도 회전
            key = rotateMatrixBy90Degree(key, keySize);

            // 열쇠 위치를 설정하는 2중 반복문
            for (int x = 0; x < lockSize * 2; x++) {
                for (int y = 0; y < lockSize * 2; y++) {

                    // 자물쇠에 열쇠를 끼워 넣는 2중 반복문
                    for (int i = 0; i < keySize; i++) {
                        for (int j = 0; j < keySize; j++) {
                            newLock[x + i][y + j] += key[i][j];
                        }
                    }

                    // 새로운 자물쇠 홈과 열쇠의 돌기가 정확히 들어 맞는지 검사
                    if (check(newLock)) return true;

                    // 일치하지 않는다면 열쇠 값을 빼 새로운 자물쇠의 상태를 되돌림
                    for (int i = 0; i < keySize; i++) {
                        for (int j = 0; j < keySize; j++) {
                            newLock[x + i][y + j] -= key[i][j];
                        }
                    }
                }
            }
        }

        return false;
    }

    private static boolean check(final int[][] newLock) {
        final int lockSize = newLock.length / 3;
        for (int i = lockSize; i < lockSize * 2; i++) {
            for (int j = lockSize; j < lockSize * 2; j++) {
                if (newLock[i][j] != 1) return false;
            }
        }

        return true;
    }

    private static int[][] rotateMatrixBy90Degree(int[][] key, final int keySize) {
        // 키는 정사각형 형태 배열
        final int[][] result = new int[keySize][keySize];
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < keySize; j++) {
                // 시계 방향으로 90도 회전
                result[j][keySize - i - 1] = key[i][j];
            }
        }

        return result;
    }
}
