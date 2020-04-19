import java.util.Arrays;

/**
 * 归并排序
 * 分而治之
 * O(NlogN)
 * @author Ethan
 *
 */
public class MyMergeSort {

	public static void main(String[] args) {

		int[] array = {1,13,24,26,2,15,27,28};
		System.out.println("排序前：" + Arrays.toString(array));

		MyMergeSort.mergeSort(array);
		System.out.println("排序后：" + Arrays.toString(array));
	}
	
	/**
	 * 归并排序启动函数
	 * @param a
	 */
	public static void mergeSort(int[] a) {
		int[] tmpArray = new int[a.length];
		mergeSort(a,tmpArray,0,a.length -1);
	}

	/**
	 * 分
	 * @param a
	 * @param tempArray
	 * @param left
	 * @param right
	 */
	private static void mergeSort(int[] a,int[] tempArray, int left, int right) {

		if(left < right) {
			int center = (left + right) / 2;
			mergeSort(a, tempArray, left, center);
			mergeSort(a, tempArray, center + 1, right);

			// 先排序，再合并
			merge(a,tempArray,left,center + 1,right);
		}
	}
	
	/**
	 * 
	 * @param a
	 * @param tmpArray
	 * @param leftPos
	 * @param rightPos
	 * @param rightEnd
	 */
	private static void merge(int[] a,int[] tmpArray,int leftPos,int rightPos,int rightEnd) {
		
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElement = rightEnd - leftPos + 1;
		
		// 主循环
		while(leftPos <= leftEnd && rightPos <= rightEnd)  { 
			
			if(a[leftPos] <= a[rightPos]) {
				tmpArray[tmpPos] = a[leftPos];
				tmpPos = tmpPos + 1;
				leftPos = leftPos + 1;
			}else {
				tmpArray[tmpPos] = a[rightPos];
				tmpPos = tmpPos + 1;
				rightPos = rightPos + 1;
				}
		}
		
		while(leftPos <= leftEnd) {
			tmpArray[tmpPos] = a[leftPos];
			tmpPos = tmpPos + 1;
			leftPos = leftPos + 1;
		}
		
		// 左右两组合并，当有一组没有元素的情况下，则不用比较大小了，直接把剩余的元素存入合并数组中
		// 左侧数组有剩余
		while(leftPos <= leftEnd) {
			tmpArray[tmpPos] = a[leftPos];
			tmpPos = tmpPos + 1;
			leftPos = leftPos + 1;
		}
		
		// 右侧数据有剩余
		while(rightPos <= rightEnd) {
			tmpArray[tmpPos] = a[rightPos];
			tmpPos = tmpPos + 1;
			rightPos = rightPos + 1;
		}
		
		// 最后将临时结果拷贝回原始数组中
		for(int i = 0;i < numElement;i++,rightEnd--) {
			a[rightEnd] = tmpArray[rightEnd];
		}
		
	}

}
