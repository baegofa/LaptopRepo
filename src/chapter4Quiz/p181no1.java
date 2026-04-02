package chapter4Quiz;

class Camera {
	String brand;
	int price;
	
	Camera(String brand,int price){
		this.brand = brand;
		this.price = price;
	}
	
	public String getBrand() {
		return brand;
	}
	public int getPrice() {
		return price;
	}
	
}

public class p181no1 {
	public static void main(String[] args) {
		Camera myCam = new Camera("Samsung", 250000);
		Camera yourCam = new Camera("Nikon", 320000);
		
		System.out.println(myCam.getBrand() + "," + myCam.getPrice() + "원");
		System.out.println(yourCam.getBrand() + "," + yourCam.getPrice() + "원");
	}

}
