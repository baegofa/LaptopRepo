package 챕터4장실습;
import java.util.Scanner;

class Dic {
	private static String [] eng = {"sun", "knight","school", "holiday", "happiness"};
	private static String [] kor = {"태양", "기사","학교", "휴일", "행복"};
	
	public static String eng2Kor(String word) {
		for(int i=0;i<eng.length;++i) {
			if (word.equals(eng[i])) {
				return kor[i];
			}
		}
		return "단어장에 없습니다.";
	}
}
public class p186no9 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String stop ="그만";
		String question = "";
		Dic dic = new Dic();
		System.out.println("영한 단어 검색 프로그램입니다.");
		
		while(question.equals(stop)==false) {
			System.out.print("영어 단어?");
			question = scanner.next();
			if (question.equals(stop)) break;
			System.out.println(question+"은 "+dic.eng2Kor(question));
		}
		scanner.close();

	}

}
