package com.zym.algorithm;

import java.util.Scanner;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,14,25,26,47,82,93};

        Scanner sc = new Scanner(System.in);

        System.out.println("请输入要查找的数字（输入exit结束）：");
        while (sc.hasNext()){
            String next = sc.next();
            if ("exit".equals(next)){
                break;
            }
            try {
                int target = Integer.parseInt(next);
                int res = binarySearch(arr, target);

                if (res < 0) {
                    System.out.println("查询失败，未查询到对应结果。");
                } else {
                    System.out.println("查询成功，对应坐标为：" + res);
                }
            }catch (Exception e){
                System.out.println("请输入整数或exit。");
            }

        }

        sc.close();

    }


    public static int binarySearch(int[] arr, int target){
        int times = 0;
        try {
            int low = 0;
            int high = arr.length-1;
            while (low <= high){
                int mid = (low + high)/2;
                if (target == arr[mid]){
                    return mid;
                }

                if (target < arr[mid]){
                    high = mid - 1;
                }
                if (target > arr[mid]){
                    low = mid + 1;
                }
                times++;
            }
            return -1;
        }finally {
            System.out.println("search times : " + times);
        }

    }
}
