package com.zym.wrapperclass;

public class StringTest {

    public static void main(String[] args) {
        testNormalString();
    }


    public static void testNormalString(){
        /**
         * 字面量 "abcdef" 会先在字符串常量池中创建
         * 每次 new 都复制这个字面量，在堆上创建新对象：
         * s1 和 s2 是两个独立的堆内存对象。
         * 即使内容相同（"abcdef"），它们的内存地址也不同。
         *
         * 下面两条语句相当于在常量池中创建了"abcdef"对象，又在堆上创建了两个对象
         * s1和s2指向位于堆内存的普通对象区域的对象
         */
        String s1 = new String("abcdef");
        String s2 = new String("abcdef");

        //==比较时，由于是两个String对象，结果返回false
        System.out.println(s1 == s2);
        //identityHashCode返回对象默认的哈希码（未重写 hashCode() 时的原始值）
        //通常与对象内存地址相关（但不保证是实际地址），是最接近"内存地址"的表示
        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));
        //equals比较时，值相同，结果返回true
        System.out.println(s1.equals(s2));

        /**
         * 首次遇到字面量 "abcdef" 时，JVM 将其放入字符串常量池。
         * 后续再遇到相同字面量时，直接复用常量池中的对象。
         *
         * 下面两条语句相当于只在常量池中创建了"abcdef"对象
         * s3和s4只是两个变量，都指向常量池中创建的abcdef对象
         */
        String s3 = "abcdef";
        String s4 = "abcdef";
        System.out.println(s3 == s4);
        System.out.println(System.identityHashCode(s3));
        System.out.println(System.identityHashCode(s4));
        System.out.println(s3.equals(s4));

    }
}
