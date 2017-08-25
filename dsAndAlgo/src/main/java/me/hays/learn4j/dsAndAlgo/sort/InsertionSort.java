package me.hays.learn4j.dsAndAlgo.sort;

import java.util.Arrays;

public class InsertionSort {
	/***
	 * 插入排序，先把前面的当做是已经排好序的数据
	 * @param args
	 */
	public static void sort(int[] args){
		int length = args.length;
		if(length > 1){
			for(int i = 1; i<length; i++){
				System.out.println("i=" + i);
				if(args[i-1] > args[i]){
					int j;
					int temp = args[i];
					for(j=i-1; j>=0 && args[j] > temp; j--){
						args[j+1] = args[j];
					}
					args[j+1] = temp;
					System.out.println(Arrays.toString(args));
				}
			}
		}
	}
}
