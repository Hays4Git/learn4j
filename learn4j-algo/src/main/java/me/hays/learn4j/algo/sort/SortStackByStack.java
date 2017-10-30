package me.hays.learn4j.algo.sort;

import java.util.Stack;

/****
 * @author hays
 * 一个栈中元素的类型为整型，现在要求将该栈从顶到底，按从小到大的顺序排序.
 * 只允许申请一个栈。除此之外，可以申请新的变量，但是不能申请额外的数据结构。
 * 测试数据项：
 *    栈数据项：{2, 4, 1, 3}  
 * 期望结果：
 *	      从顶到底，以此顺序打印如下： 1 , 2 , 3 , 4
 */
public class SortStackByStack {
	
	public static void main(String[] args) {
		//此后的实现中只能申请整型变量，不能申请额外Stack或其他数据结构对象
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(3);
		stack.push(7);
		stack.push(4);
		stack.push(2);
		stack.push(5);
		stack.push(8);
		stack.push(0);
		stack.push(6);
		stack.push(1);
		sort(stack);
	}

	
	/***
	 * 主要点：keep住最大值
	 * 排完stack就没数据了..
	 * @param stack
	 */
	private static void sort(Stack<Integer> stack){
		Stack<Integer> help = new Stack<Integer>();
		while(!stack.isEmpty()){
			Integer stackItem = stack.pop();
			if(!help.isEmpty()){
				while(!help.isEmpty() && stackItem > help.peek()){
					stack.push(help.pop());
				}
			}
			help.push(stackItem);
		}
		while(!help.isEmpty()){
			System.out.println(help.pop());
		}
	}
	
}
