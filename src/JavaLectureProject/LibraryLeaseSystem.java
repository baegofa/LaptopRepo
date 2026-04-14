package JavaLectureProject;

import java.util.Scanner;
import java.util.Vector;

//책정보
class book{
	String title;
	int bookNum;
	book(String title, int bookNum) {
		this.title = title;
		this.bookNum = bookNum;
	}
}

//도서관 시스템
class librarySystem{
	//멤버 변수 & 상수 선언 & 객체 생성
	public static final int NO_BOOK = -1;
	public static final int PRINT_ALL = 1;
	public static final int PRINT_ONE = 2;
	public static final boolean DEBUG = false;
	Scanner scanner = new Scanner(System.in);
	Vector<book> bookLib = new Vector<book>(10);
	
	//책 index 찾기
	public int searchBook(String title) {
		for (int i = 0;i<bookLib.size();++i) {
			if(title.equals(bookLib.get(i).title)) {
				return i;
			}
		}
		return NO_BOOK;
	}
	
	//책 등록
	public boolean registerBook(String title, int bookNum) {
		if(DEBUG) System.out.println(searchBook(title));
		if(searchBook(title) == NO_BOOK) {
			book book = new book(title,bookNum);
			bookLib.add(book);
			System.out.println("등록되었습니다.");
			printBookDB(title,PRINT_ALL);
			return true;
		}
		else {
			System.out.println("해당하는 책은 이미 도서관에 존재합니다.");
			return false;
		}	
	}
	
	//책 대여
	public void leaseBook(String title) {	
		if(bookLib.get(searchBook(title)).bookNum==0) {
			System.out.println("해당 책은 모두 대여되었습니다.");
		}
		else {
			bookLib.get(searchBook(title)).bookNum -= 1;
			System.out.println("대여 되었습니다.");
			printBookDB(title,PRINT_ONE);
		}
	}
	
	//도서 관리 현황 출력
	public void printBookDB(String title, int mode) {
		if(mode == PRINT_ALL) {
			System.out.println("[도서 현황]");
			for (int i = 0;i<bookLib.size();++i) {
				System.out.println(bookLib.get(i).title+" : "+bookLib.get(i).bookNum+"권");
			}
			System.out.println("----------------------");
		}
		if(mode == PRINT_ONE) {
			if(searchBook(title) == NO_BOOK) {
				System.out.println('\''+title+"\'은 도서관에 없습니다.");
			}
			else {
				System.out.println(title+"은 "+bookLib.get(searchBook(title)).bookNum+"권 있습니다.");
			}
		}
	}
	
	//도서관 시스템 동작 
	public void runSystem() {
		String userOrder = "",title = "";
		int bookNum;
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
		scanner.close();
	}
}

public class LibraryLeaseSystem {
	public static void main(String[] args) {
		librarySystem librarySystem = new librarySystem();
		librarySystem.runSystem();
	}
}
