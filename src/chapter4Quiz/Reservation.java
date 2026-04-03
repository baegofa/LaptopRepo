package chapter4Quiz;
import java.util.Scanner;

class Seat{
	String name;
	int num;
	
	Seat(String name,int num){
		this.name = name;
		this.num = num;
	}
}

public class Reservation {
	public static final int TOTALTABLENUMBER = 5;
	public static final boolean DEBUG = true;
	Scanner scanner = new Scanner(System.in);
	Seat[] seat = new Seat[TOTALTABLENUMBER];
	int reservedNumber = 0;
	
	//생성자
	Reservation() {
		
	}
	
	//예약자 받기
	public boolean booking(int tableNum) {
		String name;
		int peerNum;
		System.out.print("예약자 이름과 고객수를 입력하세요>>");
		name = scanner.next();
		peerNum = scanner.nextInt();
		if (name.equals("매니저")) {
			callManager();
			return false;
		}
		else {
			makeSeat(tableNum,name,peerNum);
			++reservedNumber;
			return true;
		}
	}
	
	//예약자에 따라 seat 객체 초기화 & 생성
	public void makeSeat(int tableNum, String name, int peerNum) {
		seat[tableNum] = new Seat(name,peerNum);
	}
	//매니저 키워드로 중간 좌석 출력
	public void callManager() {
		System.out.println("현재 예약자를 출력합니다.");
		for (int i=0;i<TOTALTABLENUMBER;++i) {
			if(seat[i] == null) {
				break;
			}
			else {
				System.out.print("좌석 "+(i+1)+": "+seat[i].name+" "+seat[i].num+"명 ");
			}
		}
		System.out.print("\n");
	}
	
	//예약자 출력
	public void showResevation() {
		for (int i=0;i<TOTALTABLENUMBER;++i) {
			System.out.print("좌석 "+(i+1)+": "+seat[i].name+" "+seat[i].num+"명 ");
		}
	}
	
	public void runReservationSystem() {
		System.out.println("명품 레스토랑의 예약 시스템입니다. 좌석수는 5개입니다.");
		for (int i=0;i<TOTALTABLENUMBER;++i) {
			if (booking(i) == false) --i;
		}
		System.out.println("만석입니다. 예약을 종료합니다.");
		showResevation();
	}
	
	
	//main 메소드
	public static void main(String[] args) {
		Reservation reservation = new Reservation();
		reservation.runReservationSystem();
	}
}
