package com.jaenyeong.chapter_06_sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Question06 {
    /*
    [Question]
    성적이 낮은 순서로 학생 출력하기

    [Input]
    1 line > 2
    2 line > 홍길동 95
    3 line > 이순신 77
    [Output]
    > 이순신 홍길동

     */

    public static void main(String[] args) {
        // 입력 조건
        // 1번째 라인 : 학생의 수 (1 <= n <= 100,000)
        // 2번째 라인 이후 : 학생의 이름을 나타내는 문자열 (문자열 길이는 100 이하)
        //                 : 학생의 성적을 나타내는 정수 (정수는 100 이하의 자연수)
        final Scanner scanner = new Scanner(System.in);

        System.out.println("학생의 수를 입력하세요");
        final int n = scanner.nextInt();
        scanner.nextLine();

        // 간결한 처리를 위해 학생 클래스 생성하여 사용
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println("학생의 이름과 점수를 입력하세요");
            Student newStudent = new Student(scanner.next(), scanner.nextInt());
            students.add(newStudent);
        }

        // 학생의 점수를 오름차순으로 정렬
        students.sort(Comparator.comparing(Student::getScore));

        // 학생 이름만 추출하여 공백으로 구분하여 출력
        String result = students.stream().map(Student::getName).collect(Collectors.joining(" "));
        System.out.println(result);
    }
}

class Student {
    private final String name;
    private final int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

