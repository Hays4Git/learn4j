package me.hays.learn4j.algo.sort;

import java.util.Arrays;

/***
 * @author hays
 */
public class Classic {

	public static void main(String[] args) {
		System.out.println("bubble排序后数组：" + Arrays.toString(bubble(getTestParams())));
		System.out.println("select排序后数组：" + Arrays.toString(select(getTestParams())));
		System.out.println("insert排序后数组：" + Arrays.toString(insert(getTestParams())));
		System.out.println("quick排序后数组：" + Arrays.toString(quick(getTestParams())));
	}
	
	private static int[] getTestParams(){
		int[] params = new int[]{3, 4, 1, 7, 9, 0, 10, 2, 3, 5, 8, 6, 2, 1, 5, 9};
		//System.out.println("=====>待排序数组：" + Arrays.toString(params));
		return params;
	}
	
	/***
	 * 冒泡排序
	 * 时间复杂度（n^2）
	 * 目的，每次排好最后一个。
	 * 方式，从第一个开始查看相邻俩，不合适（前面的大）就交换，这样最后一个一定是最大的。
	 * @param params 待排序的数组
	 * @return int[] 排序完成的数组
	 */
	private static int[] bubble(int[] params){
		for(int i=0; i<params.length-1; i++){
			for(int j=0; j<params.length-1-i; j++){
				if(params[j] > params[j+1]){
					int temp = params[j];
					params[j] = params[j+1];
					params[j+1] = temp;
				}
			}
		}
		return params;
	}

	/***
	 * 选择排序
	 * 时间复杂度（n^2）
	 * 每次从待选数组中拎出一个最大的来，放到最后，然后缩小规模再来一次。
	 * @param params 待排序的数组
	 * @return int[] 排序完成的数组
	 */
	private static int[] select(int[] params){
		for(int i=0; i<params.length; i++){
			int min = params[i];
			int minPos = i;
			for(int j=i; j<params.length; j++){
				if(min > params[j]){
					min = params[j];
					minPos = j;
				}
			}
			int temp = params[i];
			params[i] = params[minPos];
			params[minPos] = temp;
		}
		return params;
	}
	
	/***
	 * 插入排序
	 * 时间复杂度（n^2）
	 * 假设后面的序列是有序的，每次从剩余数组中拎出最后一个，插入到有序数组中合适位置。然后剩余数组缩小规模再插一次。
	 * @param params 待排序的数组
	 * @return int[] 排序完成的数组
	 */
	private static int[] insert(int[] params){
		for(int i=1; i<params.length; i++){
			for(int j=i; j>0; j--){
				if(params[j-1] > params[j]){
					int temp = params[j-1];
					params[j-1] = params[j];
					params[j] = temp;
				}
			}
		}
		return params;
	}
	
	/***
	 * 快速排序
	 * 时间复杂度（nlgn）
	 * 该方法的基本思想是（分治法）：
	 * 1、先从数列中取出一个数作为基准数。
	 * 2、分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
	 * 3、再对左右区间重复第二步，直到各区间只有一个数。
	 * 参考http://blog.csdn.net/morewindows/article/details/6684558
	 * @param params 待排序的数组
	 * @return int[] 排序完成的数组
	 */
	private static int[] quick(int[] params){
		_quick(params, 0, params.length-1);
		return params;
	}
	private static void _quick(int[] params, int ll, int rr){
		if(rr > ll){
			int l = ll, r = rr;
			int base = params[l];//取左边的为基数
			while(r > l){
				System.out.println(Arrays.toString(params));
				//先从右往左找，直到找到比基数base小的数，将这个数填到基数的坑里
				while(r > l && base <= params[r]){
					r--;
				}
				if(r > l){
					params[l] = params[r];
				}
				//从左往右找，直到找到比基数base大或者等于base的数，将这个数填到刚刚挖走的数的位置
				while(r > l && base > params[l]){
					l++;
				}
				if(r > l){
					params[r] = params[l];
				}
				System.out.println(Arrays.toString(params));
			}
			//到这里的话r=l
			params[l] = base;
			System.out.println(Arrays.toString(params));
			//递归调用
			_quick(params, ll, l-1);
			_quick(params, l+1, rr);
		}
	}
	
	/***
	 * 归并排序
	 * 时间复杂度（nlgn）
	 * 该方法的基本思想是（分治法）：
	 * 1、分解：把长度为n 的待排序列分解成 两个长度为n/2 的序列
	 * 2、治理：对每个子序列分别调用归并排序，进行递归操作。当子序列长度为1 时，序列本身有序，停止递归
	 * 3、合并：合并每个排序好的子序列
	 * @param params 待排序的数组
	 * @return int[] 排序完成的数组
	 */
	//private static int[] merge(int[] params){return null;}
	
	
	
	/***
	 * 堆排序
	 * 时间复杂度（nlgn）
	 * 该方法的基本思想是（分治法）：
	 * 这个比较有意思，核心要实现一个堆化函数。
	 * 这个函数什么意思呢，就是假设一个大顶堆只有根元素不合法，左右子树都合法（符合堆性质），
	 * 然后把堆顶元素一路往下搞，跟冒泡差不多，使整个树满足堆的性质。
	 * @param params 待排序的数组
	 * @return int[] 排序完成的数组
	 */
	//private static int[] merge(int[] params){return null;}
}





















