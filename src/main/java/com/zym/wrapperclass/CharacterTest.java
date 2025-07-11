package com.zym.wrapperclass;

public class CharacterTest {
    public static void main(String[] args) {
        characterCache();
    }

    public static void characterCache(){

        // ASCII 范围内（0~127）复用缓存对象
        Character ch1 = 'A';  // 自动装箱 -> valueOf('A')
        Character ch2 = 'A';
        System.out.println(ch1 == ch2); // true（同一缓存对象）

        // 超出 ASCII 范围（>127）不缓存
        Character ch3 = '中';  // Unicode 20013 > 127
        Character ch4 = '中';
        System.out.println(ch3 == ch4); // false（创建新对象）

        Character ch5 = '9';
        Character ch6 = '9';
        System.out.println((int)ch5);
        System.out.println(ch5 == ch6);

    }
}
