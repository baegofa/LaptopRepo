package JavaLecturePractice;

class Circle {
	int radius;
	String name;
	double area;
	
	public Circle(){
		name = "";
		radius = 1;
		this.area = 3.14*radius*radius;
	}
	
	public Circle(int radius,String name){
		this.radius = radius;
		this.name = name;
		this.area = 3.14*radius*radius;
	}
}

public class 챕터4예제4_1 {
	public static void main(String[] args) {
		Circle pizza = new Circle(10,"자바피자");
		System.out.println(pizza.name + "의 면적은 " + pizza.area);
		
		Circle donut = new Circle();
		donut.name = "자바도넛";
		System.out.println(donut.name + "의 면적은 " + donut.area);
	}

}
