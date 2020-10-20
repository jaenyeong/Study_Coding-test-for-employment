package com.jaenyeong.chapter_13_dfs_bfs;

import java.util.Scanner;

public class PS04 {
    /*
    [Question]
    괄호 변환

    [Input]
    > (()())()
    [Output]
    > (()())()

    [Input]
    > )(
    [Output]
    > ()

    [Input]
    > ()))((()
    [Output]
    > ()(())()

    [입력 조건]
    > '('와 ')'로 이루어진 문자열 (2 <= p.length <= 1,000)
      짝수로만 이루어져 있으며 '(', ')'의 개수는 항상 동일

     */

    private static final Scanner SC = new Scanner(System.in);

    private static final char openP = '(';

    public static void main(String[] args) {
        System.out.println("괄호를 입력하세요");
        final String p = SC.nextLine().trim();

        System.out.println(myPS(p));
    }

    private static String myPS(final String parentheses) {
        return recursiveRefactor(parentheses);
    }

    private static String recursiveRefactor(final String parentheses) {
        // 1. 입력이 빈 문자열인 경우 빈 문자열 반환
        if (parentheses == null || parentheses.equalsIgnoreCase("")) return "";

        // 2. 문자열 w를 두 "균형잡힌 괄호 문자열" u, v로 분리
        //    단 u는 "균형잡힌 괄호 문자열"로 더 이상 분리할 수 없어야 하며, v는 빈 문자열이 될 수 있음
        final int balancePoint = getBalancePoint(parentheses);
        final String u = parentheses.substring(0, balancePoint);
        final String v = parentheses.substring(balancePoint);

        // "올바른 괄호 문자열" 경우 그대로 반환
        if (validateParentheses(u)) {
            // 3. 수행한 결과 문자열을 u에 이어 붙인 후 반환
            //    3-1. 문자열 u가 "올바른 괄호 문자열"이라면 문자열 v에 대해 1단계부터 다시 수행
            return u + recursiveRefactor(v);
        }

        // 4. 문자열 u가 "올바른 괄호 문자열"이 아니라면 아래 과정 수행
        final StringBuilder result = new StringBuilder();
        //    4-1. 빈 문자열에 첫 번째 문자로 '('를 붙임
        result.append("(");
        //    4-2. 문자열 v에 대해 1단계부터 재귀적으로 수행한 결과 문자열을 이어 붙임
        result.append(recursiveRefactor(v));
        //    4-3. ')'를 다시 붙임
        result.append(")");
        //    4-4. u의 첫 번째와 마지막 문자를 제거하고, 나머지 문자열의 괄호 방향을 뒤집어서 뒤에 붙임
        String cutP = u.substring(1, u.length() - 1);
        result.append(reverseParentheses(cutP));

        //    4-5. 생성된 문자열을 반환
        return result.toString();
    }

    private static boolean validateParentheses(final String parentheses) {
        int openCount = 0;
        int closeCount = 0;
        for (char c : parentheses.toCharArray()) {
            if (c == openP) {
                openCount++;
            } else {
                closeCount++;
            }

            if (openCount < closeCount) {
                return false;
            }
        }

        return true;
    }

    private static int getBalancePoint(final String parentheses) {
        int openCount = 0;
        int closeCount = 0;
        for (char c : parentheses.toCharArray()) {
            if (c == openP) {
                openCount++;
            } else {
                closeCount++;
            }

            if (openCount == closeCount) {
                return openCount + closeCount;
            }
        }

        return parentheses.length();
    }

    private static String reverseParentheses(final String parentheses) {
        final StringBuilder sb = new StringBuilder();
        for (char c : parentheses.toCharArray()) {
            if (c == openP) {
                sb.append(')');
            } else {
                sb.append('(');
            }
        }

        return sb.toString();
    }
}
