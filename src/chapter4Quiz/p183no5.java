package chapter4Quiz;

class Memo {
	String name, time, content;

	Memo(String name,String time, String content){
		this.name = name;
		this.time = time;
		this.content = content;
	}

	public boolean isSameTime(Memo b) {
		if(time.equals(b.time)) {
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void show() {
		System.out.println(name+" "+time+" "+content);
	}

	public int length() {
		return content.length();
	}

}

public class p183no5 {
	public static void main(String[] args) {
		Memo a = new Memo("옥지윤", "09:00","경진 대회 준비");
		Memo b = new Memo("박채원", "09:00","그래프 DB 설치 필요");
		Memo c = new Memo("성주연", "11:30","EPUB Reader 코딩");

		a.show();
		if(a.isSameTime(b)) {
			System.out.println("메모 시간 동일");
		} else {
			System.out.println("메모 시간 다름");
		}
		System.out.println(c.getName() + "이 작성한 메모의 길이는 " + c.length() + "글자");
	}

}
