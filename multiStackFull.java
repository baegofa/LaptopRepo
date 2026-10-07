package 연습장;

public class multiStackFull<E> {
	final static int MAX_SIZE = 100;
	final static int MAX_STACK_NO = 10;
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
		if(index < 0|| index > topArr.length) {
			throw new IndexOutOfBoundsException("존재하지 않는 stack index: " + index);
		}
		return topArr[index] - boundaryArr[index];
	}

	public boolean isFull(int index) {
		if(topArr[index] == boundaryArr[index+1]-1) return true;
		return false;
	}

	public boolean isEmpty(int index) {
		if(topArr[index] == boundaryArr[index]-1) return true;
		return false;
	}

	public void push(int index, E e) {
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
		if(0 > index || index >= topArr.length) {
			System.out.println("wrong index parameter in adjustMultiStack method");
			return false;
		}
		// shift stack left
		if(0 < index) {
			for(int i = index; i > 0; --i) {
				if(isFull(i-1)) continue;
				else {
					for(int k = index; k <= index; k++) {
						for(int j = boundaryArr[k]; j<=topArr[k]; j++) {
							data[j-1] = data[j];
						}
						topArr[k] --;
						boundaryArr[k] --;
					}
					return true;
				}
			}
		}
		
		// shift stack right
		if(index < topArr.length) {
			for(int i = index; i < topArr.length; ++i) {
				if(isFull(i)) continue;
				else {
					for(int j = topArr[i]; j>=boundaryArr[i]; j--) {
						data[j+1] = data[j];
					}
					topArr[i]++;
					boundaryArr[i]++;
					return true;
				}
			}
		}
		return false;
	}
	
	// ===================== 테스트 보조 =====================
		/** 배열 전체와 각 스택의 상태를 한눈에 출력 */
		public void print(String label) {
			System.out.println("  ── " + label + " ──");
			StringBuilder idx = new StringBuilder("   idx : ");
			StringBuilder val = new StringBuilder("   val : ");
			StringBuilder own = new StringBuilder("   stk : ");
			for (int i = 0; i < data.length; i++) {
				idx.append(String.format("%3d", i));
				val.append(String.format("%3s", data[i] == null ? "." : data[i].toString()));
				int s = -1;
				for (int k = 0; k < topArr.length; k++)
					if (i >= boundaryArr[k] && i < boundaryArr[k+1]) { s = k; break; }
				own.append(String.format("%3s", s < 0 ? "-" : String.valueOf(s)));
			}
			System.out.println(idx);
			System.out.println(val);
			System.out.println(own);
			for (int i = 0; i < topArr.length; i++)
				System.out.printf("   stack %d : 영역[%2d..%2d] top=%2d 개수=%d%s%s%n",
					i, boundaryArr[i], boundaryArr[i+1]-1, topArr[i],
					topArr[i] - boundaryArr[i] + 1,
					isEmpty(i) ? "  <EMPTY>" : "", isFull(i) ? "  <FULL>" : "");
			System.out.println();
		}

		/** 예외가 나도 테스트가 중단되지 않게 감싸서 호출 */
		private static String safe(java.util.function.Supplier<Object> f) {
			try { return String.valueOf(f.get()); }
			catch (Exception e) { return e.getClass().getSimpleName() + "(" + e.getMessage() + ")"; }
		}

	
	public static void main(String[] args) {
		multiStackFull<Integer> s = new multiStackFull<>(20, 4);

		System.out.println("===== [A] 생성 직후 (배열 20칸 / 스택 4개) =====");
		s.print("A");

		System.out.println("===== [B] 초기 구성 =====");
		System.out.println("  stack0=2개(여유)  stack1=0개(EMPTY)  stack2=5개(FULL)  stack3=2개(여유)");
		s.push(0, 1); s.push(0, 2);
		for (int v = 21; v <= 25; v++) s.push(2, v);
		s.push(3, 31); s.push(3, 32);
		s.print("B");

		System.out.println("===== [C] 조회 method 확인 =====");
		for (int i = 0; i < 4; i++) {
			final int k = i;
			System.out.printf("   i=%d  isEmpty=%-5b isFull=%-5b top=%-5s size=%s%n",
				i, s.isEmpty(i), s.isFull(i), String.valueOf(s.top(i)),
				safe(() -> s.size(k)));
		}
		System.out.println("   기대 : i=0 F/F/2/2 | i=1 T/F/null/0 | i=2 F/T/25/5 | i=3 F/F/32/2");
		System.out.println("   size(4) = " + safe(() -> s.size(4)) + "   (없는 index -> -2 기대)");
		System.out.println("   size(-1)= " + safe(() -> s.size(-1)) + "   (없는 index -> -2 기대)");
		System.out.println();

		System.out.println("===== [D] 왼쪽 밀기 : stack2는 FULL, 왼쪽 stack1이 EMPTY =====");
		System.out.println("  push(2, 99) 실행");
		s.push(2, 99);
		s.print("D");
		System.out.println("   기대 : 21~25가 한 칸 왼쪽(9~13)으로 밀리고 99는 14번에, stack3은 그대로");
		System.out.println();

		System.out.println("===== [E] 오른쪽 밀기 : stack0을 FULL로 만든 뒤 push =====");
		while (!s.isFull(0)) s.push(0, 3);
		s.print("E-1 : stack0 포화");
		System.out.println("  push(0, 77) 실행 - 왼쪽에 스택이 없으므로 오른쪽에서 공간을 받아와야 함");
		s.push(0, 77);
		s.print("E-2");
		System.out.println();

		System.out.println("===== [F] 전체 포화 =====");
		for (int i = 0; i < 4; i++) while (!s.isFull(i)) s.push(i, 0);
		s.print("F-1 : 전부 포화");
		System.out.println("  push(1, 55) 실행 - \"All stack is full\" 출력 기대");
		s.push(1, 55);
		s.print("F-2");

		System.out.println("===== [G] pop / top =====");
		System.out.println("   pop(2) = " + s.pop(2));
		System.out.println("   pop(2) = " + s.pop(2));
		System.out.println("   top(2) = " + s.top(2));
		s.print("G");
	}
}
