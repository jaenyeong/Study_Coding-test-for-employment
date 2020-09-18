package com.jaenyeong.chapter_09_shortest_path;

import java.util.*;
import java.util.stream.IntStream;

public class Question05 {
    /*
    [Question]
    전보

    [Input]
    1 line > 3 2 1
    2 line > 1 2 4
    3 line > 1 3 2
    [Output]
    > 2 4

     */

    private static final int INF = (int) 1e9;

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 도시의 개수 (1 <= n <= 30,000), 도시 간 통로의 개수(1 <= m <= 200,000), 메시지 송신 도시 (1 <= c <= n)
        // 2번째 라인 이후 : x, y, z (x 도시에서 y 도시로 메시지 전송하는 비용이 z)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("도시의 개수, 도시 간 통로의 개수, 메시지를 송신할 도시를 입력하세요");
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        final int c = scanner.nextInt();
        scanner.nextLine();

        final List<List<City>> graph = initializeGraph(scanner, n, m);

        final int[] costHistory = new int[n + 1];
        Arrays.fill(costHistory, INF);

        dijkstra(graph, c, costHistory);

        printResult(n, costHistory);
    }

    private static List<List<City>> initializeGraph(final Scanner scanner, final int n, final int m) {
        List<List<City>> graph = new ArrayList<>();

        IntStream.range(0, n + 1).forEach((i) -> graph.add(new ArrayList<>()));

        System.out.println("모든 도시 간 연결 통로 및 메시지 전송 시간 정보를 입력하세요");
        for (int i = 0; i < m; i++) {
            System.out.println("통로 " + m + "개 중 " + (i + 1) + "번째 통로, 메시지 전송 시간 정보 입력");
            // a 도시에서 b 도시로 메시지 전송 비용 c
            final int aCity = scanner.nextInt();
            final int bCity = scanner.nextInt();
            final int cost = scanner.nextInt();
            scanner.nextLine();
            graph.get(aCity).add(new City(bCity, cost));
        }

        return graph;
    }

    private static void dijkstra(final List<List<City>> graph, final int startCity, final int[] costHistory) {
        final Queue<City> sendMessagePlans = new PriorityQueue<>(Comparator.comparingInt(City::getDistance));
        costHistory[startCity] = 0;

        sendMessagePlans.offer(new City(startCity, 0));

        while (!sendMessagePlans.isEmpty()) {
            City popCity = sendMessagePlans.poll();
            final int currIndex = popCity.getIndex();
            final int currDistance = popCity.getDistance();

            if (costHistory[currIndex] < currDistance) continue;

            final List<City> linkedCities = graph.get(currIndex);
            for (City linkedCity : linkedCities) {
                final int distance = linkedCity.getDistance();

                if (distance < costHistory[linkedCity.getIndex()]) {
                    costHistory[linkedCity.getIndex()] = distance;
                    sendMessagePlans.offer(new City(linkedCity.getIndex(), distance));
                }
            }
        }
    }

    private static void printResult(final int n, final int[] costHistory) {
        int max = 0;
        int count = 0;
        for (int i = 1; i <= n; i++) {
            final int city = costHistory[i];
            // 시작 노드 값인 0, 도달할 수 없는 무한 값을 제외한 모든 경우
            if ((city > 0) && (city < INF)) {
                max = Math.max(max, city);
                count++;
            }
        }

        System.out.println(count + " " + max);
    }
}

class City {
    private final int index;
    private final int distance;

    public City(int index, int distance) {
        this.index = index;
        this.distance = distance;
    }

    public int getIndex() {
        return index;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "City{" +
            "index=" + index +
            ", distance=" + distance +
            '}';
    }
}
