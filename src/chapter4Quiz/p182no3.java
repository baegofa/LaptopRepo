package chapter4Quiz;
import java.util.Scanner;

class Grade {
	String name;
	int cpp,java,web;

	Grade(String name,int cpp,int java,int web){
		this.name = name;
		this.cpp = cpp;
		this.java = java;
		this.web = web;
	}

	public String getName() {
		return name;
	}

	public float getAverage() {
		return (float)(cpp+java+web)/3;
	}

}

public class p182no3 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("이름, C++, Java, Web 순으로 점수 입력>>");
		String name = scanner.next();
		int cpp = scanner.nextInt();
		int java = scanner.nextInt();
		int web = scanner.nextInt();
		Grade st = new Grade(name, cpp, java, web);
		System.out.print(st.getName() + "의 평균은 ");
		System.out.printf("%.2f", st.getAverage());
		scanner.close();
	}
}
