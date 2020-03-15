package com.ethan.chapter05;

/**
 * 平方探测法
 * @author Ethan
 *
 */
public class QuadraticProbingHashTable<String> {

	private static final int DEFAULT_TABLE_SIZE = 11;

	/**
	 * 元素数组
	 */
	private HashEntry<String>[] array;

	private int currentSize;


	public QuadraticProbingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}

	public QuadraticProbingHashTable(int size) {
		allocateArray(size);
		makeEmpty();
	}

	/**
	 * 逻辑空
	 */
	public void makeEmpty() {

		currentSize = 0;

		for(int i = 0;i < array.length;i++) {
			array[i] = null;
		}
	}
	/**
	 * 判断是否包含该元素
	 * @param x
	 * @return
	 */
	public boolean contains(String x) {
		int currentPos = findPos(x);
		return isActive(currentPos);
	}

	/**
	 * 插入元素
	 * @param x
	 */
	public void insert(String x) {

		int currentPos = findPos(x);
		// 如果存在，则不操作
		if(isActive(currentPos)){
			return; 
		}
		array[currentPos] = new HashEntry<String>(x, true);
		
		// 如果Hash表元素个数大于表空间的一半以上，则需要再扩容，即再Hash
		if(currentSize > array.length / 2) {
			rehash();
		}

	}

	/**
	 * 删除元素
	 * （惰性删除，只改变状态位）
	 * @param x
	 */
	public void remove(String x) {

		int currentPos = findPos(x);

		if(isActive(currentPos)) {
			array[currentPos].isActive = false;
		}

	}

	private static class HashEntry<String> {

		/**
		 * 元素值
		 */
		private String element;
		/**
		 * 删除标记位
		 * false:代表被删除
		 */
		private boolean isActive;

		public HashEntry(String x) {
			this(x, true);
		}

		private HashEntry(String e, boolean b) {
			this.element = e;
			this.isActive = b;
		}
	}


	/**
	 * alllocate:分配
	 * @param arraySize
	 */
	private void allocateArray(int arraySize) {
		array = new HashEntry[nextPrime(arraySize)];
	}

	/**
	 * 判断元素是否是存在、是否是active的（默认是true）
	 * @param currentPos
	 * @return
	 */
	private boolean isActive(int currentPos) {
		return array[currentPos].element != null && array[currentPos].isActive;
	}

	/**
	 * 找到当前元素位置
	 * 内部提供解决Hash冲突的方法
	 * @param x
	 * @return
	 */

	private int findPos(String x) {
		int offset = 1;
		int currentPos = myHash(x);

		// 解决Hash冲突
		while(array[currentPos] != null && !array[currentPos].element.equals(x)) {
			currentPos = currentPos + offset;
			offset = offset + 2;
			if(currentPos >= array.length) {
				currentPos = currentPos - array.length;
			}
		}
		return currentPos;
	}

	/**
	 * 再Hash，增大Hash表
	 */
	private void rehash() {

		HashEntry<String>[] oldArray = array;
		// 创建一个2倍大的新表
		allocateArray(nextPrime(2 * oldArray.length));
		currentSize = 0;
		
		for (int i = 0; i < oldArray.length; i++) {
			if(oldArray[i] != null && oldArray[i].isActive) {
				insert(oldArray[i].element);
			}
		}
	}

	/**
	 * 计算元素的Hash值
	 * @param x
	 * @return
	 */
	private int myHash(String x) {

		int hashVal = x.hashCode();
		hashVal = hashVal % array.length;
		if(hashVal < 0) {
			hashVal = hashVal + array.length;
		}
		return hashVal;
	}

	/**
	 * 求大于某数的下一个素数
	 * @param n
	 * @return
	 */
	private static int nextPrime(int n) {

		if( n % 2 == 0 )
			n++;
		for( ; !isPrime( n ); n += 2 )
			;
		return n;
	}

	private static boolean isPrime(int n) {

		if( n == 2 || n == 3 )
			return true;

		if( n == 1 || n % 2 == 0 )
			return false;

		for( int i = 3; i * i <= n; i += 2 )
			if( n % i == 0 )
				return false;

		return true;
	}

}
