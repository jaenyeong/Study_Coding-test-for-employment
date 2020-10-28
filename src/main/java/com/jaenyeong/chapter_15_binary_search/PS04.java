package com.jaenyeong.chapter_15_binary_search;

import java.util.*;

public class PS04 {
    /*
    [Question]
    가사 검색

    [Input]
    1 line > frodo front frost frozen frame kakao
    2 line > fro?? ????o fr??? fro??? pro?
    [Output]
    > 3 2 4 1 0

    [입력 조건]
    1 line > 가사에 사용된 모든 단어들이 담긴 배열
             가사 배열의 길이 (2 <= words.length <= 100,000)
             각 가사 단어 길이 (1 <= word.length <= 10,000)
             전체 가사 단어 길이의 합 (2 <= words.sum <= 1,000,000)
             중복된 단어 없음
             각 가사 단어는 오직 알파벳 소문자로만 구성
    2 line > 찾고자 하는 키워드가 담긴 배열
             키워드 배열의 길이 (2 <= keywords.length <= 100,000)
             전체 검색 키워드 길이의 합 (2 <= keywords.sum <= 1,000,000)
             중복된 단어 존재
             각 검색 키워드는 알파벳 소문자와 '?'로만 구성

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("가사에 사용된 단어들을 입력하세요");
        final String[] words = SC.nextLine().split(" ");

        System.out.println("검색할 키워드들을 입력하세요");
        final String[] queries = SC.nextLine().split(" ");

        System.out.println(Arrays.toString(bookPS(words, queries)));
    }

    private static int[] bookPS(final String[] words, final String[] queries) {
        // 주어진 단어 목록을 글자 수를 기준으로 나눠 저장한 리스트 초기화
        final List<List<String>> wordList = new ArrayList<>();
        // 주어진 단어 목록을 글자 수를 기준으로 나눈 후 역정렬하여 저장한 리스트 초기화
        // 접두사의 와일드카드가 붙는 경우
        // 예를 들어 "????o"의 경우 위 리스트를 이용해 처리할 수 없기 때문에 역정렬 리스트 선언
        final List<List<String>> reverseWordList = new ArrayList<>();

        // 각 리스트 초기화
        for (int i = 0; i <= 10000; i++) {
            wordList.add(new ArrayList<>());        // 워드 리스트
            reverseWordList.add(new ArrayList<>()); // 리버스 워드 리스트
        }

        // 주어진 단어 목록을 각 리스트에 저장
        for (String word : words) {
            wordList.get(word.length()).add(word);
            String reverseWord = new StringBuilder().append(word).reverse().toString();
            reverseWordList.get(word.length()).add(reverseWord);
        }

        // 정렬
        for (int i = 0; i <= 10000; i++) {
            Collections.sort(wordList.get(i));
            Collections.sort(reverseWordList.get(i));
        }

        // 찾은 개수 카운트
        final List<Integer> foundCounts = new ArrayList<>();

        // 주어진 검색 키워드들을 하나씩 반복하면서 해당 단어 찾기
        for (String keyword : queries) {
            final int keySize = keyword.length();

            // 검색 키워드의 첫번째 문자 추출
            final char firstChar = keyword.charAt(0);

            // 검색 키워드를 찾을 리스트 초기화
            final List<String> searchWordList = firstChar == '?'
                // 검색 키워드가 와일드카드로 시작하는 경우 (접두사 == 와일드카드)
                // "????o"의 경우 기존 리스트를 이용해 처리할 수 없기 때문에
                ? reverseWordList.get(keySize)
                // 검색 키워드가 와일드카드로 시작하지 않는 경우 (접미사 == 와일드카드)
                : wordList.get(keySize);

            // 접두사에 와일드카드가 붙은 경우 키워드 문자열을 뒤집어 처리
            if (firstChar == '?') {
                keyword = new StringBuilder().append(keyword).reverse().toString();
            }

            // 찾은 개수 합산
            final int findCount = getFindWordCount(searchWordList, keyword);
            foundCounts.add(findCount);
        }

        // 찾은 개수 반환
        final int[] resultCount = new int[foundCounts.size()];
        for (int i = 0; i < foundCounts.size(); i++) {
            resultCount[i] = foundCounts.get(i);
        }

        return resultCount;
    }

    private static int getFindWordCount(final List<String> targetList, final String keyword) {
        // 해당 키워드와 동일한 단어의 시작 인덱스와 마지막 인덱스로 해당 단어 수 추출

        // 와일드 카드를 a로 변경
        String aKeyword = keyword.replaceAll("\\?", "a");
        final int startIdx = getStartIdxKeyword(targetList, aKeyword);

        // 와일드 카드를 z로 변경
        String zKeyword = keyword.replaceAll("\\?", "z");
        final int endIdx = getEndIdxKeyword(targetList, zKeyword);

        return endIdx - startIdx;
    }

    private static int getStartIdxKeyword(final List<String> targetList, final String aKeyword) {
        int start = 0;
        int end = targetList.size();

        while (start < end) {
            final int mid = (start + end) / 2;

            final String target = targetList.get(mid);

            // 타겟 단어가 검색 키워드보다 사전 순으로 같거나 큰 경우
            if (target.compareTo(aKeyword) >= 0) {
                end = mid;
                continue;
            }

            start = mid + 1;
        }

        return end;
    }

    private static int getEndIdxKeyword(final List<String> targetList, final String zKeyword) {
        int start = 0;
        int end = targetList.size();

        while (start < end) {
            final int mid = (start + end) / 2;

            final String target = targetList.get(mid);

            // 타겟 단어가 검색 키워드보다 사전 순으로 큰 경우
            // z라는 단어를 포함하지 않는 경우가 다음 순서의 단어를 의미
            if (target.compareTo(zKeyword) > 0) {
                end = mid;
                continue;
            }

            start = mid + 1;
        }

        return end;
    }
}
