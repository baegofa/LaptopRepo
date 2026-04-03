package chapter4Quiz;
import java.util.Scanner;

public class Area {
	private String name, areaCode;
	public Area(String name, String areaCode) {
		this.name = name;
		this.areaCode = areaCode;
	}
	public String getName() {return name;}
	public String getAreaCode() {return areaCode;}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String name=null, areaCode=null;
		Area[] test = new Area[2];
		for (int i =0;i<2;++i) {
			System.out.print("지역 이름과 지역코드 입력>>");
			name = scanner.next();
			areaCode = scanner.next();
			test[i] = new Area(name,areaCode);
		}
		for (int i =0;i<2;++i) {
			System.out.println(test[i].name + "의 지역코드는 " + test[i].areaCode);
		}
		
		
		
		scanner.close();
	}

}
