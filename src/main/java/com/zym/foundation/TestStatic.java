package com.zym.foundation;

public class TestStatic {

    public static int num;

    static {
        num = 1;
    }

    public static int add(){
        return ++num;
    }

    public static int remove(){
        return --num;
    }

    public int getNum(){
        return num;
    }

    public int addStatic(){
        return ++num;
    }
}
