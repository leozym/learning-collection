package com.zym.wrapperclass;

public class IntegerTest {

    public static void main(String[] args) {
        integerCache();
    }

    /**
     * integer的缓存默认为 -128~127
     */
    public static void integerCache(){
        Integer a1 = -128;
        Integer a2 = -128;

        //因为有缓存，所以a1 a2引用的是同一个对象
        System.out.println(a1 == a2);
        System.out.println(a1.equals(a2));

        int a3 = -128;

        System.out.println(a1 == a3);


        int b1 = 128;
        Integer b2 = 128;
        Integer b3 = 128;
        //b2 b3为不同对象，所以结果为false
        System.out.println(b2 == b3);
        //触发自动拆箱，比较的是基本类型int的值，均为128
        System.out.println(b1 == b2);
        System.out.println(b1 == b3);

    }
}
