package 연습장;

public class multiStackFull<E> {
	final static int MAX_SIZE = 100;
	final static int MAX_STACK_NO = 10;
	final static int NOT_EXIST_INDEX = -1;
	
	int[] topArr, boundaryArr;
	private E[] data;
	
	multiStackFull(){
		this(MAX_SIZE,MAX_STACK_NO);
	}
	
	multiStackFull(int size, int stackNo){
		data = (E[]) new Object[size];
		topArr = new int[stackNo];
		boundaryArr = new int[stackNo + 1];
		
		//stack 정보 초기화
		boundaryArr[stackNo + 1] = size;
		for(int i=0;i<stackNo;i++) {
			topArr[i] = (size/stackNo) * i - 1;
			boundaryArr[i] = (size/stackNo) * i;
		}
	}
	
	public int size(int index) {
		if(index > topArr.length) return -1;
		else if(index == 1) return topArr[index];
		else return topArr[index] - boundaryArr[index];
	}

	public boolean isFull(int index) {
		// TODO Auto-generated method stub
		if(topArr[index] == boundaryArr[index+1]-1) return true;
		return false;
	}

	public boolean isEmpty(int index) {
		// TODO Auto-generated method stub
		if(topArr[index] == boundaryArr[index]-1) return true;
		return false;
	}

	public void push(E e) {
		// TODO Auto-generated method stub
		
	}

	public E pop() {
		// TODO Auto-generated method stub
		return null;
	}

	public E top() {
		// TODO Auto-generated method stub
		return null;
	}

}
