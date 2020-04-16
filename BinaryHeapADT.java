
/**
 * 优先队列ADT实现
 * @author Ethan
 *
 */
public class BinaryHeapADT {

	/**
	 * 当前大小
	 */
	private int currentSize;
	/**
	 * 二叉堆存储结构
	 */
	private int[] array;
	
	/**
	 * 默认二叉堆存储大小
	 */
	private static final int DEFAULT_HEAP_SIZE = 10;
	
	
	public BinaryHeapADT() {
		this(DEFAULT_HEAP_SIZE);
	}
	
	public BinaryHeapADT(int capacity) {
		currentSize = 0;
		array = new int[capacity + 1];
	}
	
	public BinaryHeapADT(int[] items) {
		currentSize = items.length;
		array = new int[(currentSize + 2 ) * 11 / 10];
		int i= 1;
		for(int item : items) {
			array[i] = item;
			i++;
		}
		// 建立最小堆
		buildHeap();
	}
	
	/**
	 * 建立最小堆
	 * 从堆中最后一个非叶子节点进行下滤操作。
	 * 下滤：将堆（子堆）中顶点值下移，保证顶点值最小。
	 */
	private void buildHeap() {
		
		for(int i = currentSize / 2;i > 0;i--) {
		// 下滤
			percolateDown(i);
		}
	}
	
	/**
	 * 下滤：比较当前节点与子节点大小，如果小于子节点，则与子节点交换位置
	 * @param hole
	 */
	private void percolateDown(int hole) {
		
		int tmp = array[hole];
		int child;
		
		for(;hole * 2 <= currentSize;hole = child) {
			child = hole * 2;
			// 左节点 < 右节点，则跳到右节点
			if((child != currentSize) && (array[child] < array[child + 1])) {
				child++;
			}
			
			if(tmp > array[child]) {
				array[hole] = array[child];
			}else {
				break;
			}
		}
		array[hole] = tmp;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public void makeEmpty() {
		currentSize = 0;
	}
	
	/**
	 * 插入元素，上滤
	 * @param x
	 */
	public void insert(int x) {
		
		if(currentSize == array.length - 1) {
			enlargeHeap(array.length * 2 + 1);
		} 
		
		currentSize = currentSize + 1;
		int hole = currentSize;
		
		for(;hole > 0 && x < array[hole / 2];hole = hole /2) {
			array[hole] = array[hole / 2];
		}
		array[hole] = x;
	}
	
	/**
	 * 堆扩容
	 * @param newSize
	 */
	private void enlargeHeap(int newSize) {
		int[] old = array;
		array = new int[newSize];
		
		for(int i = 0;i < old.length;i++) {
			array[i] = old[i];
		}
	}
	
	/**
	 * 找最小元素
	 * @return
	 */
	public int findMin() {
		return array[1];
	}
	
	/**
	 * 删除最小元素
	 * @return
	 */
	public int deleteMin() {
		int min =  findMin();
		array[1] = array[currentSize];
		currentSize = currentSize - 1;
		percolateDown(1);
		return min;
	}
}
