package 챕터4장실습;
import java.util.Scanner;

class player {
	String name;
	player(String name){
		this.name = name;
	}
}

public class GamblingGame {
	Scanner scanner = new Scanner(System.in);
	player[] player;
	String name,input;
	String[] RGB = {"R","G","B"};
	int[] result;
	
	GamblingGame() {
		player = new player[2];
		result = new int[3];
	}
	//선수 입력
	public void addPlayer() {
		for (int i=0;i<player.length;++i) {
			System.out.print("선수 이름 입력>>");
			name = scanner.next();
			player[i] = new player(name);
		}
	}
	//난수 생성
	public int randomNum() {
		return (int)(Math.random()*3);
	}
	//RGB 출력
	public void printRGB(int num) {
		System.out.print("\n"+player[num].name+"님 아무 키나 입력하세요>>");
		scanner.next();
		for(int i=0;i<RGB.length;++i) {
			result[i] = randomNum();
			System.out.print(RGB[result[i]]+" ");
		}
	}
	//결과 판독
	public boolean decideWin(int[] result) {
		for (int i=1;i<result.length;++i) {
			if (result[i]!=result[i-1]) return false;
		}
		return true;
	}
	//게임 실행
	public void run() {
		int count = 0;
		System.out.println("대박겜블링을 시작합니다.");
		addPlayer();
		do {
			printRGB(count%2);
			++count;
		}while(decideWin(result)!=true);
		System.out.println("\n"+player[count%2].name+"님이 우승하셨습니다.");
	}

	public static void main(String[] args) {
		GamblingGame gambling = new GamblingGame();
		gambling.run();
	}

}
