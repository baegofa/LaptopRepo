package JavaLecturePractice;

class Book{
	String title, author;
	public Book(String t) {
		title = t;
		author = "작자미상";
	}
	public Book(String t, String a) {
		title = t;
		author = a;
	}
}

public class 챕터4예제4_4 {
	public static void main(String[] args) {
		Book littlePrince = new Book("어린왕자","생택쥐페리");
		
		Book loveStory = new Book("춘향전");
		System.out.println(littlePrince.title + " " + littlePrince.author);
		System.out.println(loveStory.title + " " + loveStory.author);
	}

}
