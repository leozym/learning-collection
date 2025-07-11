package com.zym.wrapperclass;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class StringTest {

    public static void main(String[] args) {
//        testNormalString();
//        stringBufferAndStringBuilderTest();
//        diffStringAndDoubleB();

        try {
            stringPerformanceTest();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    private static final int THREAD_COUNT = 100;
    private static final int OPERATIONS_PER_THREAD = 1000;

    public static void stringBufferAndStringBuilderTest() {
        //线程安全相关
//        threadSafeTest();

        //常用方法相关
//        commonExample();

        //性能测试
        try {
            stringPerformanceTest();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static void threadSafeTest(){
        //JDK1.0提供，线程安全，做线程同步检查，效率低
        StringBuffer stringBuffer = new StringBuffer();
        testBufferAndBuilder(stringBuffer, "StringBuffer");


        //JDK1.5提供，线程不安全，不做线程同步检查，效率高
        StringBuilder stringBuilder = new StringBuilder();
        testBufferAndBuilder(stringBuilder, "StringBuilder");
    }

    /**
     * 启动{@value THREAD_COUNT} 个线程，每个线程追加{@value OPERATIONS_PER_THREAD}个字符"a"
     */
    private static void testBufferAndBuilder(Appendable buffer, String bufferType) {
        System.out.println("\n===== 测试 " + bufferType + " =====");
        System.out.println("启动 " + THREAD_COUNT + " 个线程，每个线程执行 " + OPERATIONS_PER_THREAD + " 次追加操作");

        long startTime = System.currentTimeMillis();

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        // 提交任务
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    try {
                        buffer.append("a");
                    } catch (Exception e) {
                        // 对于StringBuilder，这里可能会抛出异常
                    }
                }
            });
        }

        // 关闭线程池并等待所有任务完成
        executor.shutdown();
        boolean awaitTermination;
        try {
            awaitTermination = executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long duration = System.currentTimeMillis() - startTime;

        // 验证结果
        int expectedLength = THREAD_COUNT * OPERATIONS_PER_THREAD;
        int actualLength = getLength(buffer);

        System.out.println("是否执行完成：" + awaitTermination);
        System.out.println("预期长度: " + expectedLength);
        System.out.println("实际长度: " + actualLength);
        System.out.println("一致性: " + (expectedLength == actualLength ? "✓ 通过" : "✗ 失败"));
        System.out.println("执行时间: " + duration + " ms");
        System.out.println("===============================");
    }

    private static int getLength(Appendable buffer) {
        if (buffer instanceof StringBuilder) {
            return ((StringBuilder) buffer).length();
        } else if (buffer instanceof StringBuffer) {
            return ((StringBuffer) buffer).length();
        }
        return 0;
    }


    /**
     * StringBuilder 和 StringBuffer 的常用方法在名称和功能上是完全相同的。
     * 两者都继承自 AbstractStringBuilder 类，提供了相同的 API 接口。
     */
    private static void commonExample(){
        StringBuilder stringBuilder = new StringBuilder();

        // 追加内容
        stringBuilder.append("Hello").append(" my ");
        System.out.println("追加内容：" + stringBuilder);
        // 插入内容
        stringBuilder.insert(9, "World!");
        System.out.println("插入内容：" + stringBuilder);

        // 删除内容 左闭右开，删除5,6,7
        stringBuilder.delete(5, 8);            // 删除 " my"
        System.out.println("删除内容：" + stringBuilder);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1); // 删除末尾 '!'
        System.out.println("删除末尾：" + stringBuilder);

        // 替换内容
        stringBuilder.replace(0, 5, "Hi");      // "Hello" -> "Hi"
        System.out.println("替换内容：" + stringBuilder);

        // 其他操作
        System.out.println(stringBuilder);                      // "Hi World"
        System.out.println(stringBuilder.reverse());            // 反转: "dlroW iH"
        System.out.println(stringBuilder.charAt(1));            // 获取指定位置内容 'l'
        // 截取字符串"dlro " 左闭右开，截取0,1,2,3位置的字符
        System.out.println(stringBuilder.substring(0, 4));

    }

    /**
     * String为不可变字符序列
     * StringBuffer StringBuilder为可变字符序列
     */
    public static void diffStringAndDoubleB(){
        //String 的加减会丢弃之前的对象，创建新对象
        String s1 = "abcdef";
        System.out.println("s1 hash：" +System.identityHashCode(s1));
        s1 = s1 + "g";
        System.out.println("s1 hash：" +System.identityHashCode(s1));
        String s2 = s1;
        System.out.println("s2 hash：" +System.identityHashCode(s2));
        s1 = s1 + "g";
        //s1丢弃原对象，但s2依旧指向原对象
        System.out.println("s1 hash：" +System.identityHashCode(s1));
        System.out.println("s2 hash：" +System.identityHashCode(s2));


        //StringBuffer和StringBuilder会修改原对象，以StringBuilder为例
        StringBuilder stringBuilder = new StringBuilder("abcdef");
        System.out.println("stringBuilder hash：" + System.identityHashCode(stringBuilder));
        stringBuilder.append("g");
        System.out.println("stringBuilder hash：" +System.identityHashCode(stringBuilder));

    }
    // 测试参数配置
    private static final int WARMUP_ITERATIONS = 10; // 预热迭代次数
    private static final int ITERATIONS = 10;      // 测试迭代次数
    private static final int CONCAT_COUNT = 100000; // 每次迭代的拼接次数
    private static final int PERFORMANCE_THREAD_COUNT = 10;     // 并发线程数


    public static void stringPerformanceTest() throws InterruptedException {

        System.out.println("性能测试配置:");
        System.out.println("---------------------------------");
        System.out.println("预热迭代次数：" + WARMUP_ITERATIONS);
        System.out.println("每次测试拼接次数: " + CONCAT_COUNT);
        System.out.println("测试迭代次数:    " + ITERATIONS);
        System.out.println("并发测试线程数:   " + THREAD_COUNT);
        System.out.println("---------------------------------\n");


        // 单线程性能测试
//        testSingleThreadPerformance();

        // 多线程并发测试
        testConcurrentPerformance();
    }

    /**
     * 单线程性能测试
     */
    private static void testSingleThreadPerformance() {
        System.out.println("===== 单线程性能测试 =====");

        // 预热
        warmup(StringTest::stringConcatTest);
        warmup(StringTest::stringBuilderTest);
        warmup(StringTest::stringBufferTest);

        // 测试 String 拼接
        long stringTime = testOperation(StringTest::stringConcatTest, "String");

        // 测试 StringBuilder
        long builderTime = testOperation(StringTest::stringBuilderTest, "StringBuilder");

        // 测试 StringBuffer
        long bufferTime = testOperation(StringTest::stringBufferTest, "StringBuffer");

        // 打印单线程测试结果
        printResults(stringTime, builderTime, bufferTime);
    }

    /**
     * 多线程并发性能测试
     */
    private static void testConcurrentPerformance() throws InterruptedException {
        System.out.println("\n===== 多线程并发性能测试 =====");

        // 预热
        StringBuilder warmShareStringBuilder = new StringBuilder();
        warmupConcurrent(() -> stringBuilderConcurrentTest(warmShareStringBuilder));
        StringBuffer warmShareStringBuffer = new StringBuffer();
        warmupConcurrent(() -> stringBufferConcurrentTest(warmShareStringBuffer));

        // 测试 StringBuilder 并发
        long builderTime = testConcurrentOperation("StringBuilder");

        // 测试 StringBuffer 并发
        long bufferTime = testConcurrentOperation("StringBuffer");

        // 打印并发测试结果
        printConcurrentResults(builderTime, bufferTime);
    }
    /**
     * 执行单次性能测试操作
     */
    private static long testOperation(Runnable operation, String type) {
        System.out.println("测试 " + type + "...");

        long totalTime = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            // 执行垃圾回收，减少干扰
            System.gc();

            long startTime = System.nanoTime();
            operation.run();
            long duration = System.nanoTime() - startTime;

            totalTime += duration;
        }

        return totalTime / ITERATIONS;
    }
    // 单线程测试方法
    private static void stringConcatTest() {
        String result = "";
        for (int i = 0; i < CONCAT_COUNT; i++) {
            result += "a";
        }
        // 防止优化
        if (result.length() != CONCAT_COUNT) {
            throw new AssertionError();
        }
        System.out.println("string length: " + result.length());
    }

    private static void stringBuilderTest() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < CONCAT_COUNT; i++) {
            stringBuilder.append("a");
        }
        if (stringBuilder.length() != CONCAT_COUNT) {
            throw new AssertionError();
        }
        System.out.println("stringBuilder length: " + stringBuilder.length());
    }

    private static void stringBufferTest() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < CONCAT_COUNT; i++) {
            stringBuffer.append("a");
        }
        if (stringBuffer.length() != CONCAT_COUNT) {
            throw new AssertionError();
        }
        System.out.println("stringBuffer length: " + stringBuffer.length());
    }

    // 并发测试方法
    private static void stringBuilderConcurrentTest(StringBuilder shareStringBuilder) {
        for (int i = 0; i < CONCAT_COUNT / THREAD_COUNT; i++) {
            shareStringBuilder.append("a");
        }
    }

    private static void stringBufferConcurrentTest(StringBuffer shareStringBuffer) {
        for (int i = 0; i < CONCAT_COUNT / THREAD_COUNT; i++) {
            shareStringBuffer.append("a");
        }
    }

    /**
     * 预热：运行指定操作若干次，不记录时间
     */
    private static void warmup(Runnable operation) {
        System.out.println("预热中...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            operation.run();
        }
    }

    private static void warmupConcurrent(Runnable task) throws InterruptedException {
        System.out.println("并发预热中...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
            for (int j = 0; j < THREAD_COUNT; j++) {
                executor.submit(task);
            }
            executor.shutdown();
            boolean awaitTermination = executor.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println("是否执行完成：" + awaitTermination);
        }
    }


    /**
     * 执行并发性能测试
     */
    private static long testConcurrentOperation(String type)
            throws InterruptedException {
        System.out.println("测试并发 " + type + "...");

        long totalTime = 0;

        Appendable appendable;
        if ("StringBuilder".equals(type)) {
            appendable = new StringBuilder();
        }else if ("StringBuffer".equals(type)) {
            appendable = new StringBuffer();
        }else {
            throw new IllegalArgumentException("不支持的类型: " + type);
        }

        for (int i = 0; i < ITERATIONS; i++) {

            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
            long startTime = System.nanoTime();



            Runnable task;
            if ("StringBuilder".equals(type)) {
                task = () -> stringBuilderConcurrentTest((StringBuilder) appendable);
            } else {
                task = () -> stringBufferConcurrentTest((StringBuffer) appendable);
            }

            // 提交任务
            for (int j = 0; j < THREAD_COUNT; j++) {
                executor.submit(task);
            }

            // 等待所有任务完成
            executor.shutdown();
            boolean awaitTermination = executor.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println("并发测试" + type  + "是否执行完成：" + awaitTermination);

            long duration = System.nanoTime() - startTime;
            totalTime += duration;
        }

        return totalTime / ITERATIONS;
    }


    /**
     * 打印单线程测试结果
     */
    private static void printResults(long stringTime, long builderTime, long bufferTime) {
        System.out.println("\n测试结果 (单线程):");
        System.out.println("---------------------------------");
        System.out.printf("String:         %10d ns | %6.1f ms%n",
                stringTime, nsToMs(stringTime));
        System.out.printf("StringBuilder:  %10d ns | %6.1f ms%n",
                builderTime, nsToMs(builderTime));
        System.out.printf("StringBuffer:   %10d ns | %6.1f ms%n",
                bufferTime, nsToMs(bufferTime));
        System.out.println("---------------------------------");

        // 计算性能差异
        double builderVsString = (double) stringTime / builderTime;
        double bufferVsBuilder = (double) bufferTime / builderTime;

        System.out.printf("StringBuilder 比 String 快 %.1f 倍%n", builderVsString);
        System.out.printf("StringBuffer 比 StringBuilder 慢 %.1f 倍%n", bufferVsBuilder);
        System.out.println("=================================");
    }


    /**
     * 打印并发测试结果
     */
    private static void printConcurrentResults(long builderTime, long bufferTime) {
        System.out.println("\n测试结果 (并发):");
        System.out.println("---------------------------------");
        System.out.printf("StringBuilder:  %10d ns | %6.1f ms%n",
                builderTime, nsToMs(builderTime));
        System.out.printf("StringBuffer:   %10d ns | %6.1f ms%n",
                bufferTime, nsToMs(bufferTime));
        System.out.println("---------------------------------");

        // 计算性能差异
        double bufferVsBuilder = (double) bufferTime / builderTime;

        System.out.printf("StringBuffer 比 StringBuilder 慢 %.1f 倍%n", bufferVsBuilder);
        System.out.println("=================================");
    }

    /**
     * 纳秒转毫秒
     */
    private static double nsToMs(long nanoseconds) {
        return nanoseconds / 1_000_000.0;
    }
}
