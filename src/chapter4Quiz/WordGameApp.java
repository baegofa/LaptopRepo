package chapter4Quiz;
import java.util.Scanner;

class player {
	public static final boolean DEBUG = true;
	Scanner scanner = new Scanner(System.in);
	String[][] playerWordList;
	String playerName, inputWord, loser, lastWord, nowWord;
	char lastchar,nowchar;
	int playerNum, count = 0,wordSection=1,nameSection=0, wordIndex;
//참가 인원과 이름을 입력받는 메소드
	public void addPlayer() {
		System.out.println("참가 인원을 입력하세요:");
		playerNum = scanner.nextInt();
		playerWordList = new String[playerNum][2];
		
		for (int i =0; i<playerNum; ++i) {
			System.out.println("참가 선수의 이름을 입력하세요:");
			playerName = scanner.next();
			playerWordList[i][nameSection] = playerName;
		}
	}

//단어를 입력받아 저장하는 메소드
	public void updatePlayerWord(int count,String input) {
		playerWordList[count%playerNum][1] = input;
	}
//단어를 비교하는 메소드
	public boolean compareWord(int count) {
		//비교 단어 설정 - 배열을 다쓰기엔 문장이 너무 길어짐
		if(count<1) return true;
		
		else {
			if(count%playerNum == 0) {
				lastWord = playerWordList[2][wordSection];
				nowWord = playerWordList[0][wordSection];
				wordIndex = lastWord.length();
				lastchar = lastWord.charAt(wordIndex-1);
				nowchar = nowWord.charAt(0);
				
			}
			else {
				lastWord = playerWordList[count%playerNum-1][wordSection];
				nowWord = playerWordList[count%playerNum][wordSection];
				wordIndex = lastWord.length();
				lastchar = lastWord.charAt(wordIndex-1);
				nowchar = nowWord.charAt(0);
			}
		}
		
		if(lastchar == nowchar) return true;
		else {
			return false;
		}
	}
//패자를 가리는 메소드
	public void decideLoser(int count) {
		loser = playerWordList[count%playerNum][0];
		System.out.println("패자는 " + loser + "입니다.");
	}
//실행하는 메소드
	public void run() {
		addPlayer();
		while(compareWord(count-1)) {
			System.out.println(playerWordList[count%playerNum][nameSection]+"님 단어를 입력하세요:");
			inputWord = scanner.next();
			updatePlayerWord(count,inputWord);
			++count;
		}
		decideLoser(count-1);
	}
}
public class WordGameApp {
	public static void main(String[] args) {
		player wordGame = new player();
		wordGame.run();
	}
}