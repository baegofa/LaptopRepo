package chapter4Quiz;
import java.util.Scanner;

class Chart{
	String title, type;
	int[] data;
	int next=0;
	
	Chart(String title,String type,int size){
		this.title = title;
		this.type = type;
		data = new int[size];
	}
	
	public void add(int n) {
		data[next] = n;
		next += 1;
	}
	
	public double getAvg() {
		int sum = 0;
		for (int i=0; i<data.length;++i) {
			sum += data[i];
		}
		return sum/data.length;
	}
	
	public void draw() {
		System.out.println("온도: " + type + "로 그리기 ******");
		for (int i=0; i<data.length;++i) {
			System.out.print(data[i]+" ");
		}
		System.out.println(", 평균:" + getAvg());
	}
	
	public int getCapacity() {
		return data.length;
	}
	
	public String getTitle() {
		return title;
	}
}

public class p184no6 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		Chart tempChart = new Chart("온도","Line", 7);
		Chart gradeChart = new Chart("성적","Bar", 4);
		
		System.out.print(tempChart.getTitle()+" "+tempChart.getCapacity()+"개>>");
		for(int i=0;i<tempChart.getCapacity();++i) {
			tempChart.add(scanner.nextInt());
		}
		
		System.out.print(gradeChart.getTitle()+" "+gradeChart.getCapacity()+"개>>");
		for(int i=0;i<gradeChart.getCapacity();++i) {
			gradeChart.add(scanner.nextInt());
		}
		
		tempChart.draw();
		gradeChart.draw();
		scanner.close();
		
	}

}
