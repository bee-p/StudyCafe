// 사용자 정보를 담는 클래스

import java.io.Serializable;		// 객체 파일 입출력을 위한 인터페이스
import java.io.ObjectInputStream;	// 객체 파일 입력
import java.io.ObjectOutputStream;	// 객체 파일 출력

class User implements Serializable{
	private String userName;	// 사용자의 이름
	private String userPhone;	// 사용자의 전화번호
	
	// Serializable 인터페이스 구현
	private void writeObject(ObjectOutputStream out) throws java.io.IOException
	{
		out.defaultWriteObject();
	}
	private void readObject(ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	// userName 필드에 대한 getter, setter 메소드
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	// userPhone 필드에 대한 getter, setter 메소드
	public String getUserPhone()
	{
		return userPhone;
	}
	public void setUserPhone(String userPhone)
	{
		this.userPhone = userPhone;
	}
}
