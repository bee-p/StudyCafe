// ����� ������ ��� Ŭ����

import java.io.Serializable;		// ��ü ���� ������� ���� �������̽�
import java.io.ObjectInputStream;	// ��ü ���� �Է�
import java.io.ObjectOutputStream;	// ��ü ���� ���

class User implements Serializable{
	private String userName;	// ������� �̸�
	private String userPhone;	// ������� ��ȭ��ȣ
	
	// Serializable �������̽� ����
	private void writeObject(ObjectOutputStream out) throws java.io.IOException
	{
		out.defaultWriteObject();
	}
	private void readObject(ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	// userName �ʵ忡 ���� getter, setter �޼ҵ�
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	// userPhone �ʵ忡 ���� getter, setter �޼ҵ�
	public String getUserPhone()
	{
		return userPhone;
	}
	public void setUserPhone(String userPhone)
	{
		this.userPhone = userPhone;
	}
}
