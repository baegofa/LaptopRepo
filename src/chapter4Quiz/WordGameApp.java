package chapter4Quiz;
import java.util.Scanner;

class addPlayer {
	Scanner scanner = new Scanner(System.in);
	int playerNum;
	String name;
	
	addPlayer () {
		playerNum = 0;
		name = "";
	}
	
	public String[] writePlayerList () {
		System.out.println("게임 참가 인원을 입력하세요:");
		playerNum = scanner.nextInt();
		
		String[] player = new String[playerNum];
		
		for (int i=0;i<playerNum;++i) {
			System.out.println("참가자의 이름을 입력하세요:");
			player[i] = scanner.nextLine();
		}
		return player;
	}
}

class wordCompare {
	
}

class decideWin {
	
}

public class WordGameApp {
	public static void main(String[] args) {
		addPlayer a = new addPlayer();
		a.writePlayerList();
	}
}
