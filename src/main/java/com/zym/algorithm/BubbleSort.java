package com.zym.algorithm;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {3, 14, 52, 36, 17, 28, 9};
        bubbleSort(arr);
        optimizedBubbleSort(arr);
    }

    //基础冒泡算法
    public static void bubbleSort(int[] arr) {
        int swapTimes = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                swapTimes++;
                System.out.println("swap times : " +swapTimes);
            }
        }

        System.out.println(Arrays.toString(arr));
    }

    //优化冒泡算法，通过增加标志位，在已经排好序后，不再进行重复比较。
    public static void optimizedBubbleSort(int[] arr){
        int swapTimes = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = false;
                }
                swapTimes++;
                System.out.println("swap times : " +swapTimes);
            }
            if (flag){
                break;
            }

        }
        System.out.println(Arrays.toString(arr));

    }

}
