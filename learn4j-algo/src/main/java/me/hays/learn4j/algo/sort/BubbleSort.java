package me.hays.learn4j.algo.sort;

import java.util.Arrays;

public class BubbleSort {
	/***
	 * 冒泡排序：每次比较相邻两个元素，如果前面的大于后面的，互换位置
	 * @param args 需要排序的数据
	 */
	public static void sort(int[] args){
		int length = args.length;
		if(length > 1){
			for(int i=0; i<length-1; i++){
				System.out.println("i=" + i);
				for(int j=0; j<length-i-1; j++){
					if(args[j] > args[j+1]){
						int temp = args[j+1];
						args[j+1] = args[j];
						args[j] = temp;
					}
					System.out.println("j=" + j + "排序后:" + Arrays.toString(args));
				}
				System.out.println();
			}
		}
	}
}
