package chapter4Quiz;
import java.util.Scanner;

//phone 클래스
class phone {
	public static final boolean DEBUG = true;
	String name,phoneNum;

	phone(String name, String phoneNum){
		this.name = name;
		this.phoneNum = phoneNum;
	}
}
//phoneManager 클래스
public class PhoneManager {
	Scanner scanner = new Scanner(System.in);

	int peopleNum = 0;
	String searchName = null;
	phone[] recordBook;

	public void addPhone() {
		//사람 수에 맞는 phone객체 생성
		System.out.print("사람 수>>");
		peopleNum = scanner.nextInt();
		recordBook = new phone[peopleNum];
	}

	//사람 이름 & 전화번호 저장 하는 메소드
	public void addPhoneInfo() {
		for (int i=0;i<peopleNum;++i) {
			System.out.print("이름과 전화번호>>");
			recordBook[i] = new phone(scanner.next(),scanner.next());
		}
		System.out.println("저장되었습니다.");
	}

	//검색하는 메소드
	public int searchPhoneInfo() {
		System.out.print("검색할 이름>>");
		String name = scanner.next();
		for (int i=0;i<peopleNum;++i) {
			if((recordBook[i].name).equals(name)) {
				return i;
			}
			else if (name.equals("그만")) { 						//-1과 -2로 case를 구분 했지만 이러면 if문 보다 switch문이 더 나아보임.
				return -2;
			}
		}
		return -1;
	}

	//저장된 전화번호를 출력하는 메소드
	public boolean showPhoneInfo(int searchedPhone) {
		if(searchedPhone == -1) {
			System.out.println("잘못된 이름을 입력하셨습니다.");
			return true;
		}
		else if(searchedPhone == -2) {
			System.out.println("프로그램을 종료합니다.");
			return false;
		}
		else {
			System.out.println("검핵하신 정보>> "+recordBook[searchedPhone].name+"의 전화번호는 "+recordBook[searchedPhone].phoneNum);
			return true;
		}
	}

	//main 메소드
	public static void main(String[] args) {
		PhoneManager phoneManager = new PhoneManager();

		phoneManager.addPhone();
		phoneManager.addPhoneInfo();
		while(true) {
			if(!phoneManager.showPhoneInfo(phoneManager.searchPhoneInfo())) {
				break;
			}
			phoneManager.showPhoneInfo(phoneManager.searchPhoneInfo());
		}
	}
}
