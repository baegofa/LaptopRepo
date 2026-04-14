package testPackage;

class Point {
	private int x, y;
	public Point (int x, int y) {
		this.x = x; this.y = y;
	}
}

public class chapter5Test {
	public static void main(String[] args) {
		Point p = new Point(2,3);
		String s = p + "점";
		String b = p + s;
		System.out.println(b);
		
		Integer i = Integer.valueOf("10");
		System.out.println(i.getClass().getName());
		System.out.println(i.intValue()+1);
	}
}
