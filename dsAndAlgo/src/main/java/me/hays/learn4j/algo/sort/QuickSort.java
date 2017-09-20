package me.hays.learn4j.algo.sort;

import java.util.Arrays;

public class QuickSort {
	
	public static void sort(int[] args){
		if(args.length > 1){
			sort(args, 0, args.length-1);
		}
	}
	
	private static void sort(int args[], int start, int end){
		if(end > start){
			//在pos处将数组分隔为两部分
			int pos = _sort(args, start, end);
			//排序左边部分
			sort(args, start, pos - 1);
			//排序右边部分
			sort(args, pos + 1, end);
		}
	}
	
	private static int _sort(int args[], int start, int end){
		//选取第一个数据为参考
		int consult = args[start];
		//如果最后一个比元还要小，交换位置
		if(args[end] < args[start]){
			swap(args, start, end);
		}
		//为左右定一个坐标
		int _start = start;
		int _end = end;
		while(_end > _start){
			//从左向右推进直到遇到比元大的数据
			while(_end >= _start && args[_start] <= consult){
				_start ++;
			}
			//从右向左推进直到遇到比元小的数据
			while(_end >= _start && args[_end] > consult){
				_end --;
			}
			//定位到这个的_start和_end交换位置
			if(_end > _start){
				swap(args, _start, _end);
			}
		}
		System.out.println("pos:" + _start);
		System.out.println(Arrays.toString(args));
		System.out.println();
		System.out.println();
		return _start;
	}
	
	/***
	 * 交换数组坐标上两个元素位置
	 * @param args
	 * @param first
	 * @param last
	 */
	private static void swap(int[] args, int first, int last){
		int temp = args[last];
		args[last] = args[first];
		args[first] = temp;
	}
}
