package com.zym.foundation;

public class MainTest {
    public static void main(String[] args) {

        TestStatic testStatic = new TestStatic();

        System.out.println(TestStatic.num);
        System.out.println(TestStatic.add());
        System.out.println(testStatic.getNum());

        System.out.println(testStatic.addStatic());
        System.out.println(testStatic.getNum());

        System.out.println(TestStatic.add());
        System.out.println(testStatic.getNum());

    }
}