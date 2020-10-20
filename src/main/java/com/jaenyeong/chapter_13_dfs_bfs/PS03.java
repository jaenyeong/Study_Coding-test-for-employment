package com.jaenyeong.chapter_13_dfs_bfs;

import java.util.*;

public class PS03 {
    /*
    [Question]
    경쟁적 전염

    [Input]
    1 line > 3 3
    2 line > 1 0 2
    3 line > 0 0 0
    4 line > 3 0 0
    5 line > 2 3 2
    [Output]
    > 3

    [Input]
    1 line > 3 3
    2 line > 1 0 2
    3 line > 0 0 0
    4 line > 3 0 0
    5 line > 1 2 2
    [Output]
    > 0

    [입력 조건]
    1 line > 정사각형 형태의 시험관 크기(1 <= n <= 200), 바이러스 종류 수 (1 <= k <= 1,000)
    2 ~ (1 + n) line > 시험관의 정보 (0 = 빈칸, 1 ~ k = 바이러스)
                       각 행 원소의 수 = n
    (2 + n) line > 바이러스 상태를 확인할 초 (0 <= s <= 10,000), 바이러스를 확인할 좌표 (1 <= x, y <= n)

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int ROW = 0;
    private static final int COL = 1;

    private static final int EMPTY = 0;

    public static void main(String[] args) {
        System.out.println("시험관의 크기와 바이러스의 종류 수를 입력하세요");
        final int n = SC.nextInt();
        final int k = SC.nextInt();
        SC.nextLine();

        final int[][] tube = new int[n][n];
        // 바이러스 종류의 크기가 작은 것부터 전염이 되기 때문에 전염 순서를 위하여 사용
        final List<ZoneOfVirus> infectedZones = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 시험관 행의 정보를 입력하세요");

            for (int j = 0; j < n; j++) {
                final int zoneOfTube = SC.nextInt();
                tube[i][j] = zoneOfTube;

                // 해당 영역에 바이러스가 존재하는 경우
                if (zoneOfTube != EMPTY) {
                    infectedZones.add(new ZoneOfVirus(i, j, zoneOfTube, 0));
                }
            }
        }

        // 감염 바이러스 크기를 기준으로 오름차순 정렬
        Collections.sort(infectedZones);

        System.out.println("초 단위 경과 시간과 바이러스 전염 상태를 확인할 좌표를 입력하세요");
        final int s = SC.nextInt();
        final int x = SC.nextInt();
        final int y = SC.nextInt();

        System.out.println(bookPS(tube, n, infectedZones, s, x, y));
    }

    private static int bookPS(final int[][] tube, final int tubeSize, final List<ZoneOfVirus> infectedZones,
                              final int limitTime, final int x, final int y) {

        // 이후 작업부터는 전염시간 순으로 처리되기 때문에 링크드 리스트에 적재
        final Queue<ZoneOfVirus> infectedList = new LinkedList<>(infectedZones);

        // 전염된 공간
        while (!infectedList.isEmpty()) {
            final ZoneOfVirus virus = infectedList.poll();

            // 해당 바이러스에 감염 시간
            final int timeOfInfection = virus.getTime();

            // 주어진 시간만큼 시간이 경과한 경우 전염 종료
            if (timeOfInfection == limitTime) {
                break;
            }

            // 해당 좌표를 기준으로 상하좌우 전염
            for (int[] dir : DIRECTIONS) {
                final int nextRow = virus.getRow() + dir[ROW];
                final int nextCol = virus.getCol() + dir[COL];

                // 다음 좌표가 유효 범위가 아닌경우 통과
                if ((0 > nextRow) || (nextRow >= tubeSize) || (0 > nextCol) || (nextCol >= tubeSize)) {
                    continue;
                }

                // 해당 인접 좌표가 빈 영역이면 바이러스 전염
                final int nextArea = tube[nextRow][nextCol];

                if (nextArea == EMPTY) {
                    final int virusName = virus.getName();
                    tube[nextRow][nextCol] = virusName;

                    infectedList.add(new ZoneOfVirus(nextRow, nextCol, virusName, timeOfInfection + 1));
                }
            }
        }

        // 주어진 좌표에 있는 바이러스 반환
        return tube[x - 1][y - 1];
    }
}

class ZoneOfVirus implements Comparable<ZoneOfVirus>  {
    private final int row;
    private final int col;
    private final int name;
    private final int time;

    public ZoneOfVirus(int row, int col, int name, int time) {
        this.row = row;
        this.col = col;
        this.name = name;
        this.time = time;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    // 정렬 기준은 '번호가 낮은 순서'
    @Override
    public int compareTo(ZoneOfVirus other) {
        if (this.name < other.name) {
            return -1;
        }
        return 1;
    }
}
