package com.jaenyeong.chapter_13_dfs_bfs;

import java.util.*;
import java.util.List;

public class PS08 {
    /*
    [Question]
    블록 이동하기

    [Input]
    1 line > 0 0 0 1 1
    2 line > 0 0 0 1 0
    3 line > 0 1 0 1 1
    4 line > 1 1 0 0 1
    5 line > 0 0 0 0 0
    [Output]
    > 7

    [입력 조건]
    1 ~ n line > 정사각형 형태의 지도를 구성하는 각 원소 값 (빈칸 = 0, 벽 = 1)
                 (5 <= board.length <= 100)
                 로봇이 처음에 놓여 있는 칸 (1, 1), (1, 2)는 항상 0
                 로봇이 항상 도착지에 도착할 수 있는 경우만 입력됨

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int EMPTY = 0;
    private static final int WALL = 1;

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int ROW = 0;
    private static final int COL = 1;

    private static final int[] ROTATION = {-1, 1};

    public static void main(String[] args) {
        System.out.println("정사각형 형태의 지도 크기를 입력하세요");
        final int n = SC.nextInt();

        final int[][] board = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 지도 행 정보를 입력하세요");
            for (int j = 0; j < n; j++) {
                board[i][j] = SC.nextInt();
            }
        }
        
        System.out.println(bookPS(board));
    }

    private static int bookPS(final int[][] board) {
        // 원활한 구현을 위해 사용할 새 맵 초기화
        final int n = board.length;
        final int[][] newBoard = initNewBoard(board, n);

        // BFS 작업 수행을 위해 연결리스트 초기화
        final Queue<Node> movePlans = new LinkedList<>();
        // 방문 지역
        final List<Node> visitedPoints = new ArrayList<>();
        // 스타팅 지역 초기화
        final Node startingPoint = new Node(1, 1, 1, 2, 0);

        // 이동 계획에 추가 및 방문 처리
        movePlans.offer(startingPoint);
        visitedPoints.add(startingPoint);

        // 이동할 공간이 없을 때까지 반복
        while (!movePlans.isEmpty()) {
            final Node curPoint = movePlans.poll();

            // 로봇의 어느 한 부분이라도 해당 위치에 도착한 경우 이동거리 반환하며 종료
            if ((curPoint.getFirstRow() == n && (curPoint.getFirstCol() == n))
                || ((curPoint.getSecondRow() == n) && (curPoint.getSecondCol() == n))) {
                return curPoint.getMoveStep();
            }

            // 도착하지 못한 경우 다음 이동할 지역 확인
            final List<Node> nextPoints = getNextMovePoints(curPoint, newBoard);

            // 다음에 이동할 지역을 순회
            for (Node willMovePoint : nextPoints) {
                // 방문 여부
                boolean hasNotVisited = true;

                // 다음에 이동할 포인트를 방문했던 포인트와 하나씩 비교
                for (Node visitedP : visitedPoints) {

                    // 다음에 이동할 포인트가 방문한 곳인 경우
                    if ((willMovePoint.getFirstRow() == visitedP.getFirstRow())
                        && (willMovePoint.getFirstCol() == visitedP.getFirstCol())
                        && (willMovePoint.getSecondRow() == visitedP.getSecondRow())
                        && (willMovePoint.getSecondCol() == visitedP.getSecondCol())) {

                        hasNotVisited = false;
                        break;
                    }
                }

                // 방문한 적이 없는 포인트인 경우 이동 계획 및 방문 지역에 추가
                if (hasNotVisited) {
                    movePlans.offer(willMovePoint);
                    visitedPoints.add(willMovePoint);
                }
            }
        }

        return 0;
    }

    private static int[][] initNewBoard(final int[][] board, final int n) {
        // 맵 외곽에 벽을 두는 형태로 맵을 구현하면 로봇이 맵을 벗어나는 지에 대한 유효 범위 판정을 간단히 할 수 있음
        final int[][] newBoard = new int[n + 2][n + 2];

        // 새 맵의 모든 영역을 벽으로 초기화
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {
                newBoard[i][j] = WALL;
            }
        }

        // 기존 맵의 정보를 새 맵에 적재
        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, newBoard[i + 1], 1, n);
        }

        return newBoard;
    }

    private static List<Node> getNextMovePoints(final Node curPoint, final int[][] newBoard) {
        // 반환할 다음에 이동할 지역 목록 초기화
        final List<Node> nextMovePoints = new ArrayList<>();

        // 상하좌우로 이동 가능한 지역 확인
        for (int[] dir : DIRECTIONS) {
            final int nextFirstRow = curPoint.getFirstRow() + dir[ROW];
            final int nextFirstCol = curPoint.getFirstCol() + dir[COL];
            final int nextSecondRow = curPoint.getSecondRow() + dir[ROW];
            final int nextSecondCol = curPoint.getSecondCol() + dir[COL];

            final int nextMoveStep = curPoint.getMoveStep() + 1;
            // 다음에 이동하려는 두 칸 모두 비어 있는 경우
            if ((newBoard[nextFirstRow][nextFirstCol] == EMPTY)
                && (newBoard[nextSecondRow][nextSecondCol] == EMPTY)) {
                // 다음에 이동할 지역 목록에 삽입
                nextMovePoints.add(new Node(nextFirstRow, nextFirstCol, nextSecondRow, nextSecondCol, nextMoveStep));
            }
        }

        // 로봇이 가로로 위치한 경우 회전 가능 여부 확인
        if (curPoint.getFirstRow() == curPoint.getSecondRow()) {
            // 회전 하는 경우는 위쪽, 아래쪽으로 총 2가지 경우
            for (int dir : ROTATION) {
                // 위쪽, 아래쪽 모두
                if ((newBoard[curPoint.getFirstRow() + dir][curPoint.getFirstCol()] == EMPTY)
                    && (newBoard[curPoint.getSecondRow() + dir][curPoint.getSecondCol()] == EMPTY)) {

                    // 다음에 이동할 지역 목록에 삽입
                    insertHorNextPoint(curPoint, nextMovePoints, dir);
                }
            }
        }

        // 로봇이 세로로 위치한 경우 회전 가능 여부 확인
        if (curPoint.getFirstCol() == curPoint.getSecondCol()) {
            // 회전 하는 경우는 왼쪽, 오른쪽으로 총 2가지 경우
            for (int dir : ROTATION) {
                // 왼쪽, 오른쪽 모두
                if ((newBoard[curPoint.getFirstRow()][curPoint.getFirstCol() + dir] == EMPTY)
                    && (newBoard[curPoint.getSecondRow()][curPoint.getSecondCol() + dir] == EMPTY)) {

                    // 다음에 이동할 지역 목록에 삽입
                    insertVerNextPoint(curPoint, nextMovePoints, dir);
                }
            }
        }

        return nextMovePoints;
    }

    private static void insertHorNextPoint(final Node curPoint, final List<Node> nextMovePoints, final int dir) {
        // 왼쪽 포인트를 기준으로 위쪽, 아래쪽
        nextMovePoints.add(
            new Node(curPoint.getFirstRow(), curPoint.getFirstCol(),
                curPoint.getFirstRow() + dir, curPoint.getFirstCol(),
                curPoint.getMoveStep() + 1));

        // 오른쪽 포인트를 기준으로 위쪽, 아래쪽
        nextMovePoints.add(
            new Node(curPoint.getSecondRow(), curPoint.getSecondCol(),
                curPoint.getSecondRow() + dir, curPoint.getSecondCol(),
                curPoint.getMoveStep() + 1));
    }

    private static void insertVerNextPoint(final Node curPoint, final List<Node> nextMovePoints, final int dir) {
        // 위쪽 포인트를 기준으로 왼쪽, 오른쪽
        nextMovePoints.add(
            new Node(curPoint.getFirstRow(), curPoint.getFirstCol(),
                curPoint.getFirstRow(), curPoint.getFirstCol() + dir,
                curPoint.getMoveStep() + 1));

        // 아래쪽 포인트를 기준으로 왼쪽, 오른쪽
        nextMovePoints.add(
            new Node(curPoint.getSecondRow(), curPoint.getSecondCol(),
                curPoint.getSecondRow(), curPoint.getSecondCol() + dir,
                curPoint.getMoveStep() + 1));
    }
}

class Node {
    private final int firstRow;
    private final int firstCol;
    private final int secondRow;
    private final int secondCol;
    private final int moveStep;

    public Node(final int firstRow, final int firstCol, final int secondRow, final int secondCol, final int moveStep) {
        this.firstRow = firstRow;
        this.firstCol = firstCol;
        this.secondRow = secondRow;
        this.secondCol = secondCol;
        this.moveStep = moveStep;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getFirstCol() {
        return firstCol;
    }

    public int getSecondRow() {
        return secondRow;
    }

    public int getSecondCol() {
        return secondCol;
    }

    public int getMoveStep() {
        return moveStep;
    }
}
