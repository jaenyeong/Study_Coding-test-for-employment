package com.jaenyeong.chapter_12_implementation;

import java.util.*;

public class PS05 {
    /*
    [Question]
    뱀

    [Input]
    1 line > 6
    2 line > 3
    3 line > 3 4
    4 line > 2 5
    5 line > 5 3
    6 line > 3
    7 line > 3 D
    8 line > 15 L
    9 line > 17 D
    [Output]
    > 9

    [Input]
    01 line > 10
    02 line > 4
    03 line > 1 2
    04 line > 1 3
    05 line > 1 4
    06 line > 1 5
    07 line > 4
    08 line > 8 D
    09 line > 10 D
    10 line > 11 D
    11 line > 13 L
    [Output]
    > 21

    [Input]
    01 line > 10
    02 line > 5
    03 line > 1 5
    04 line > 1 3
    05 line > 1 2
    06 line > 1 6
    07 line > 1 7
    08 line > 4
    09 line > 8 D
    10 line > 10 D
    11 line > 11 D
    12 line > 13 L
    [Output]
    > 13

    [입력 조건]
    1 line > 정사각형 보드의 크기 (2 <= n <= 100)

    2 line > 사과의 개수 (0 <= k <= 100)s
    3 ~ (2 + k) line > 사과의 위치 (행과 열)

    (3 + k) line > 뱀의 방향 변환 횟수 (1 <= l <= 100)
    (4 + k) ~ end line > 정수 x, 문자 c로 구성된 뱀의 각 방향 변환 정보
                         시작시간으로부터 x초 뒤 방향 전환 (x는 10,000 이하의 자연수로 오름차순으로 제시됨)
                         (왼쪽 = 'L', 오른쪽 = 'D'로 90도 방향 회전)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int LOAD = 0;
    private static final int APPLE = 1;
    private static final int SNAKE_BODY = 2;

    // 순서대로 동, 남, 서, 북 (뱀이 최초에 동쪽을 바라보기 때문)
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private static final int EAST = 0;
    private static final int NORTH = 3;

    private static final char L = 'L';
    private static final char R = 'D';

    private static final int[] DX = {0, 1, 0, -1};
    private static final int[] DY = {1, 0, -1, 0};

    public static void main(String[] args) {
        System.out.println("정사각형 보드의 크기를 입력하세요");
        final int n = SC.nextInt();
        final int[][] map = new int[n + 1][n + 1];
        final int[][] arr = new int[n + 1][n + 1];

        System.out.println("사과의 개수를 입력하세요");
        final int k = SC.nextInt();
        for (int i = 0; i < k; i++) {
            // 맵의 사과 위치 삽입
            System.out.println((i + 1) + "번째 사과의 위치를 입력하세요");
            final int row = SC.nextInt();
            final int col = SC.nextInt();
            map[row][col] = APPLE;
            arr[row][col] = APPLE;
            SC.nextLine();
        }

        System.out.println("뱀의 방향 변환 횟수를 입력하세요");
        final int l = SC.nextInt();
        SC.nextLine();

        final Map<Integer, Character> turnPlans = new HashMap<>();
        final List<Node> turnInfo = new ArrayList<>();

        for (int i = 0; i < l; i++) {
            System.out.println((i + 1) + "번째 방향을 변경할 시간과 방향을 입력하세요");

            final String[] inputs = SC.nextLine().split(" ");
            final int time = Integer.parseInt(inputs[0]);
            final char direction = inputs[1].charAt(0);

            turnPlans.put(time, direction);
            turnInfo.add(new Node(time, direction));
        }

        System.out.println(myPS(map, turnPlans, n));
        System.out.println(bookPS(arr, turnInfo, n, l));
    }

    private static int myPS(final int[][] map, final Map<Integer, Character> turnPlans, final int n) {
        LinkedList<Position> snake = new LinkedList<>();
        snake.offer(new Position(1, 1));
        map[1][1] = SNAKE_BODY;

        int time = 0;
        int direction = EAST;

        // 뱀 이동
        do {
            final Position snakeHead = snake.getLast();
            assert snakeHead != null;

            // 방향 설정
            direction = getDirection(direction, time, turnPlans);

            // 이동할 다음 위치
            final int nextRow = snakeHead.getRow() + DIRECTIONS[direction][0];
            final int nextCol = snakeHead.getCol() + DIRECTIONS[direction][1];

            // 다음 위치 유효성 검사
            if (validateNextPoint(n, nextRow, nextCol, map)) {
                // 다음 시간에 이동하며 유효범위를 벗어나기 때문에 time을 1초 증가
                time++;
                break;
            }

            // 맵의 다음 위치를 뱀의 위치를 저장한 리스트에 삽입
            snake.offer(new Position(nextRow, nextCol));

            // 다음 위치가 사과가 아닌 경우 꼬리 당기기 (맵에서 값 제거)
            if (map[nextRow][nextCol] != APPLE) {
                final Position tail = snake.poll();
                assert tail != null;
                map[tail.getRow()][tail.getCol()] = LOAD;
            }

            // 다음 위치로 뱀 이동
            map[nextRow][nextCol] = SNAKE_BODY;

            // 시간 증가
            time++;

        } while (true);

        return time;
    }

    private static int getDirection(int direction, final int time, final Map<Integer, Character> turnPlans) {
        // 해당 시간에 지정된 방향으로 변경
        final char c = turnPlans.getOrDefault(time, '0');
        if (c == L) {
            // 동쪽에서 좌회전하는 경우 확인
            direction = direction == EAST ? NORTH : direction - 1;
        }

        if (c == R) {
            // 북쪽에서 우회전하는 경우 확인
            direction = direction == NORTH ? EAST : direction + 1;
        }

        return direction;
    }

    private static boolean validateNextPoint(final int n, final int nextRow, final int nextCol, final int[][] map) {
        // 다음 위치가 벽, 또는 뱀의 몸통인 경우 리턴
        return (0 >= nextRow) || (nextRow > n)
            || (0 >= nextCol) || (nextCol > n)
            || (map[nextRow][nextCol] == SNAKE_BODY);
    }

    private static int bookPS(final int[][] arr, final List<Node> turnInfo, final int n, final int l) {
        // 뱀의 머리 위치
        int headRow = 1;
        int headCol = 1;

        // 뱀의 출발 위치를 맵에 삽입
        arr[headRow][headCol] = SNAKE_BODY;

        // 뱀의 이동 방향
        int direction = EAST;

        // 시작한 뒤 지난 '초' 시간
        int time = 0;

        // 다음 회전 정보
        int index = 0;

        // 맵 안에 뱀 위치 정보
        Queue<Position> snake = new LinkedList<>();
        snake.offer(new Position(headRow, headCol));

        // 뱀 이동
        while (true) {
            // 다음 위치
            int nextRow = headRow + DX[direction];
            int nextCol = headCol + DY[direction];

            // 맵 범위 안에 존재하고, 뱀의 몸통과 부딪히지 않는 위치인 경우
            if ((1 <= nextRow) && (nextRow <= n)
                && (1 <= nextCol) && (nextCol <= n)
                && arr[nextRow][nextCol] != SNAKE_BODY) {

                // 사과가 없는 경우
                if (arr[nextRow][nextCol] == LOAD) {
                    arr[nextRow][nextCol] = SNAKE_BODY;
                    snake.offer(new Position(nextRow, nextCol));
                    Position tail = snake.poll();
                    assert tail != null;
                    arr[tail.getRow()][tail.getCol()] = LOAD;
                }

                // 사과가 있는 경우
                if (arr[nextRow][nextCol] == APPLE) {
                    arr[nextRow][nextCol] = SNAKE_BODY;
                    snake.offer(new Position(nextRow, nextCol));
                }

            } else {
                time++;
                break;
            }

            // 다음 위치로 이동
            headRow = nextRow;
            headCol = nextCol;

            // 시간 증가
            time++;

            // 회전할 시간인 경우 회전
            if ((index < l) && (time == turnInfo.get(index).getTime())) {
                direction = turn(direction, turnInfo.get(index).getDirection());
                index++;
            }
        }

        return time;
    }

    private static int turn(int direction, final char c) {
        if (c == 'L') {
            direction = (direction == 0) ? 3 : direction - 1;
        } else {
            direction = (direction + 1) % 4;
        }
        return direction;
    }
}

class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
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

class Node {

    private final int time;
    private final char direction;

    public Node(int time, char direction) {
        this.time = time;
        this.direction = direction;
    }

    public int getTime() {
        return this.time;
    }

    public char getDirection() {
        return this.direction;
    }
}
