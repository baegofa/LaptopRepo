package chapter4Quiz;

class Cube{
	int width,length,height;
	Cube(int width,int length, int height){
		this.width = width;
		this.length = length;
		this.height = height;
	}
	
	public void increase(int addwidth,int addlength, int addheight) {
		width += addwidth;
		length -= addlength;
		height += addheight;
	}
	
	public int getVolume() {
		return width*length*height;
	}
	
	public boolean isZero() {
		if((width*length*height) == 0) return true;
		else return false;
	}
}

public class p183no4 {
	public static void main() {
		Cube cube = new Cube(1,2,3);
		System.out.println("큐브의 부피는 " + cube.getVolume());
		cube.increase(1,2,3);
		System.out.println("큐브의 부피는 " + cube.getVolume());
		if (cube.isZero()) {
			System.out.println("큐브의 부피는 0");
		}
		else {
			System.out.println("큐브의 부피는 0이 아님");
		}
	}
}
