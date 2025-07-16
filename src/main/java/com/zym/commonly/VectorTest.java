package com.zym.commonly;

import java.util.Vector;


/**
 * 作为 Java 早期类，设计上存在一些不足（如方法签名不一致、性能开销大），现代开发中推荐使用ArrayList或并发集合替代。
 *
 *  例如增加和删除都被synchronized修饰，保证线程安全
 *  public synchronized boolean add(E e) {
 *             modCount++;
 *             ensureCapacityHelper(elementCount + 1);
 *             elementData[elementCount++] = e;
 *             return true;
 *  }
 *
 *  public synchronized boolean removeElement(Object obj) {
 *         modCount++;
 *         int i = indexOf(obj);
 *         if (i >= 0) {
 *             removeElementAt(i);
 *             return true;
 *         }
 *         return false;
 *     }
 */
public class VectorTest {
    public static void main(String[] args) {
        Vector vector = new Vector();



    }
}
