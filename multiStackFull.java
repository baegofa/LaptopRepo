package 연습장;

public class multiStackFull<E> {
	final static int MAX_SIZE = 100;
	final static int MAX_STACK_NO = 10;
	final static int NOT_EXIST_INDEX = -2;
	final static boolean ERROR_DETECT = true;
	//if(ERROR_DETECT) System.out.println("1"); for error detect print
	
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
		boundaryArr[stackNo] = size;
		for(int i=0;i<stackNo;i++) {
			topArr[i] = (size/stackNo) * i - 1;
			boundaryArr[i] = (size/stackNo) * i;
		}
	}
	
	public int size(int index) {
		//return -2 for Not exist index and return -1 for no item in stack
		if(index > topArr.length) return NOT_EXIST_INDEX;
		else if(index == 1) return topArr[1];
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

	public void push(int index, E e) {
		// TODO Auto-generated method stub
		if(isFull(index)) {
			if(adjustMultiStack(index)) data[++topArr[index]] = e;
			else System.out.println("All stack is full");
		}
		else data[++topArr[index]] = e;
	}

	public E pop(int index) {
		// TODO Auto-generated method stub
		if(isEmpty(index)) return null;
		else return data[topArr[index]--];
	}

	public E top(int index) {
		// TODO Auto-generated method stub
		if(isEmpty(index)) return null;
		else return data[topArr[index]];
	}
	
	public boolean adjustMultiStack(int index) {
		if(0 > index && index > topArr.length) {
			System.out.println("wrong index parameter in adjustMultiStack method");
			return false;
		}
		// shift stack left
		if(0 < index) {
			for(int i = index; i > 0; --i) {
				if(isFull(i)) continue;
				else {
					for(int non = topArr[i]; topArr[i]>=boundaryArr[i]; topArr[i]--) {
						data[topArr[i]+1] = data[topArr[i]];
					}
					return true;
				}
			}
			return false;
		}
		
		// shift stack right
		else if(index < topArr.length) {
			for(int i = index; i < topArr.length; ++i) {
				if(isFull(i)) continue;
				else {
					for(int non = topArr[i]; topArr[i]>=boundaryArr[i]; topArr[i]--) {
						data[topArr[i]+1] = data[topArr[i]];
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		multiStackFull s = new multiStackFull<Integer>();
		
		//test code
		System.out.println(s.isEmpty(1));
		System.out.println(s.isFull(1));
		s.push(0, 23);
		System.out.println(s.pop(0));
		//s.adjustMultiStack(2);
		
		
		//test code
	}
}
