package com.jaenyeong.chapter_11_greedy;

import java.util.*;

public class PS06 {
    /*
    [Question]
    무지의 먹방 라이브

    [Input]
    1 line > 3 1 2
    2 line > 5
    [Output]
    > 1

    [Input]
    1 line > 8 6 4
    2 line > 15
    [Output]
    > 4

    [입력 조건]
    1 line > 각 음식의 양
    2 line > 방송 중단 시간

    [정확성 테스트 제한 사항]
    1 line > (1 <= food_times.length <= 2,000), (1 <= food_times[i] <= 1,000)
    2 line > (1 <= k <= 2,000,000)

    [효율성 테스트 제한 사항]
    1 line > (1 <= food_times.length <= 200,000), (1 <= food_times[i] <= 1,000,000,000)
    2 line > (1 <= k <= (2 * 10^13))

     */

    private static final Scanner SC = new Scanner(System.in);
    private static final int END_OF_MEAL = -1;

    public static void main(String[] args) {
        System.out.println("각 음식의 양을 입력해주세요");
        final int[] foodTimes =
            Arrays.stream(SC.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        System.out.println("네트워크 장애 시간을 입력해주세요");
        final int k = SC.nextInt();

        System.out.println(bookPS(foodTimes, k));
    }

    private static int bookPS(final int[] foodTimes, final long k) {
        // 네트워크 장애 시간 전에 모든 음식을 다 섭취하는 경우
        // 스트림을 사용하는 경우 효율성 테스트에서 과락
//        final long sumFoodTimes = Arrays.stream(foodTimes).sum();
        long sumFoodTimes = 0;
        for (int foodTime : foodTimes) {
            sumFoodTimes += foodTime;
        }

        if (sumFoodTimes <= k) return END_OF_MEAL;

        // 섭취 시간이 짧은 순서대로 오름차순 정렬되는 우선순위 큐 초기화
        Queue<Food> mealPlans = new PriorityQueue<>(Comparator.comparingInt(Food::getMealTime));

        final int foodSize = foodTimes.length;
        for (int i = 0; i < foodSize; i++) {
            // 인덱스 + 1, 섭취 시간으로 Food 객체 구현하여 삽입
            mealPlans.offer(new Food(i + 1, foodTimes[i]));
        }

        // 음식을 섭취하는데 걸린 총 시간
        long sumMealTime = 0;
        // 직전에 섭취한 음식의 섭취 시간
        long prevFoodTime = 0;
        // 남은 음식 수
        long leftovers = foodSize;

        // 네트워크 장애 시간 동안 음식 섭취 사이클을 반복
        while (isValidMealCycle(mealPlans, sumMealTime, prevFoodTime, leftovers, k)) {
            final Food currFood = mealPlans.poll();
            assert currFood != null;

            // 현재 음식을 섭취하는데 걸리는 시간
            final int currMealTime = currFood.getMealTime();
            // (현재 음식 섭취 시간 - 이전 음식 섭취 시간) * 현재 남은 음식 수
            sumMealTime += (currMealTime - prevFoodTime) * leftovers;

            // 섭취한 음식 제외
            leftovers--;
            // 현재 음식의 섭취 시간을 이전 음식의 섭취 시간으로 변경
            prevFoodTime = currMealTime;
        }

        // 남은 음식 중에서 몇 번째 음식인지 확인하기 위해 사용할 리스트 초기화
        final List<Food> leftoverList = new ArrayList<>(mealPlans);

        // 음식의 인덱스를 기준으로 오름차순 정렬
        leftoverList.sort(Comparator.comparingInt(Food::getIndex));

        // 남은 음식 중에서 몇 번째 음식인지 확인
        final int targetFood = (int) ((k - sumMealTime) % leftovers);
        return leftoverList.get(targetFood).getIndex();
    }

    private static boolean isValidMealCycle(final Queue<Food> mealPlans, final long sumMealTime, final long prevFoodTime,
                                            final long leftovers, final long k) {
        assert mealPlans.peek() != null;
        final int currFoodTime = mealPlans.peek().getMealTime();
        // 현재 사이클에서 해당 음식의 섭취 시간
        // (현재 음식의 섭취 시간 - 이전 음식의 섭취 시간) * 해당 음식을 포함한 현재 남은 음식 수
        final long currMealTime = (currFoodTime - prevFoodTime) * leftovers;
        // mealTime(현재까지의 음식 섭취시간) + 현재 음식의 섭취 시간의 합계와 네트워크 장애 시간과의 대소 비교
        return sumMealTime + currMealTime <= k;
    }
}

class Food {
    // 기존 음식 배열에서의 순번 (인덱스)
    private final int index;
    // 음식의 섭취에 걸리는 총 시간
    private final int mealTime;

    public Food(int index, int mealTime) {
        this.index = index;
        this.mealTime = mealTime;
    }

    public int getIndex() {
        return index;
    }

    public int getMealTime() {
        return mealTime;
    }
}
