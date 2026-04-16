package JavaLectureProject;

import java.util.Scanner;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

//도서관 시스템
class Library{
	//멤버 변수 & 상수 선언 & 객체 생성
	public static final int NO_BOOK = 0;
	public static final int PRINT_ALL = 1;
	public static final int PRINT_ONE = 2;
	public static final boolean DEBUG = false;
	Scanner scanner;
	HashMap<String,Integer> bookLibrary;
	Set<String> keys;
	Iterator<String> it;
	
	
	Library (){
		Scanner scanner = new Scanner(System.in);
		HashMap<String,Integer> bookLibrary = new HashMap<String,Integer>();
	}
	
	//책 등록
	public boolean registerBook(String title, Integer bookNum) {
		if(bookLibrary.containsKey(title)) {
			System.out.println("해당하는 책은 이미 도서관에 존재합니다.");
			return false;
	    }  
		bookLibrary.put(title, bookNum);
		System.out.println("등록되었습니다.");
		printBookDB(title,PRINT_ALL);
		return true;
	}
	
	//책 대여.
	public void leaseBook(String title) {	
		if (!bookLibrary.containsKey(title)) {
			System.out.println("해당 책은 도서관에 없습니다.");
		}
		else {
			bookLibrary.put(title, bookLibrary.get(title)-1);
			System.out.println("대여 되었습니다.");
			printBookDB(title,PRINT_ONE);
			if(bookLibrary.get(title)==0) {
				bookLibrary.remove(title);
			}
		}
	}
	
	//도서 관리 현황 출력
	public void printBookDB(String title, int mode) {
		Set<String> keys = bookLibrary.keySet();
		Iterator<String> it = keys.iterator();
		if(mode == PRINT_ALL) {
			System.out.println("[도서 현황]");
			while(it.hasNext()) {
				String key = it.next();
				int value = bookLibrary.get(key);
				System.out.println(key+"는 "+value+"권 있습니다.");
			}
			
			System.out.println("----------------------");
		}
		if(mode == PRINT_ONE) {
			if(bookLibrary.get(title)==NO_BOOK) {
				System.out.println('\''+title+"\'은 도서관에 없습니다.");
			}
			else {
				System.out.println(title+"은 "+bookLibrary.get(title)+"권 있습니다.");
			}
		}
	}
	
	//도서관 시스템 동작 
	public void runSystem() {
		String userOrder = "",title = "";
		Integer bookNum;
		while(userOrder.equals("종료") == false) {
			
			System.out.println("찾기,등록,대여,종료 중 하나를 입력하세요>>");
			userOrder = scanner.next();
			
			if(userOrder.equals("찾기")) {
				System.out.println("찾고자 하는 도서의 제목을 입력하세요>>");
				title = scanner.next();
				printBookDB(title,PRINT_ONE);
			}
			else if(userOrder.equals("등록")){
				System.out.println("등록할 도서의 제목과 권 수를 입력하세요>>");
				title = scanner.next();
				bookNum = scanner.nextInt();
				registerBook(title, bookNum);
			}
			else if(userOrder.equals("대여")) {
				System.out.println("대여할 도서의 제목을 입력하세요>>");
				title = scanner.next();
				leaseBook(title);
			}
		}
	}
}

public class hashMapLibrary {
	public static void main(String[] args) {
		Library library = new Library();
		library.runSystem();
	}
}
