package com.jaenyeong.chapter_14_sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PS01 {
    /*
    [Question]
    국영수

    [Input]
    01 line > 12
    02 line > Junkyu 50 60 100
    03 line > Sangkeun 80 60 50
    04 line > Sunyoung 80 70 100
    05 line > Soong 50 60 90
    06 line > Haebin 50 60 100
    07 line > Kangsoo 60 80 100
    08 line > Donghyuk 80 60 100
    09 line > Sei 70 70 70
    10 line > Wonseob 70 70 90
    11 line > Sanghyun 70 70 80
    12 line > nsj 80 80 80
    13 line > Taewhan 50 60 90
    [Output]
    01 line > Donghyuk
    02 line > Sangkeun
    03 line > Sunyoung
    04 line > nsj
    05 line > Wonseob
    06 line > Sanghyun
    07 line > Sei
    08 line > Kangsoo
    09 line > Haebin
    10 line > Junkyu
    11 line > Soong
    12 line > Taewhan

    [입력 조건]
    1 line > 반의 학생 수 (1 <= n <= 100,000)
    2 ~ (1 + n) line > 알파벳 대소문자로 구성된 각 학생의 이름 (name.length <= 10)
                       각각 국어, 영어, 수학 점수 (1 <= score <= 100)

     */

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("학생 수를 입력하세요");
        final int n = SC.nextInt();
        SC.nextLine();

        final List<Student> students = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "번째 학생 정보를 입력하세요");
            String input = SC.nextLine();
            String[] inputs = input.split(" ");
            Student s = new Student(inputs[0], Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]), Integer.parseInt(inputs[3]));
            students.add(s);
        }

        myPS(students);
    }

    private static void myPS(final List<Student> students) {
        Collections.sort(students);

        for (Student s : students) {
            System.out.println(s.getName());
        }
    }
}

class Student implements Comparable<Student> {
    private final String name;
    private final int koreanScore;
    private final int englishScore;
    private final int mathScore;

    public Student(String name, int koreanScore, int englishScore, int mathScore) {
        this.name = name;
        this.koreanScore = koreanScore;
        this.englishScore = englishScore;
        this.mathScore = mathScore;
    }

    public String getName() {
        return name;
    }

    public int getKoreanScore() {
        return koreanScore;
    }

    public int getEnglishScore() {
        return englishScore;
    }

    public int getMathScore() {
        return mathScore;
    }

    @Override
    public int compareTo(Student o) {
        // 이름 대소 비교
        if ((this.koreanScore == o.koreanScore)
            && (this.englishScore == o.englishScore)
            && (this.mathScore == o.mathScore)) {
            return this.name.compareTo(o.name);
        }

        // 수학 점수 대소 비교 (내림차순)
        if ((this.koreanScore == o.koreanScore) && (this.englishScore == o.englishScore)) {
            return Integer.compare(o.mathScore, this.mathScore);
        }

        // 영어 점수 대소 비교 (오름차순)
        if (this.koreanScore == o.koreanScore) {
            return Integer.compare(this.englishScore, o.englishScore);
        }

        // 국어 점수 대소 비교 (내림차순)
        return Integer.compare(o.koreanScore, this.koreanScore);
    }
}
