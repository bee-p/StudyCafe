// �� ��ü Ŭ����
// ����� ��ü�� �� ���� �־� �Բ� ����/������

import java.util.Calendar;
import java.util.Date;				// Date Ŭ���� ��� - �ð�(��¥) �ҷ����� ����
import java.text.SimpleDateFormat;	// �ð�(Date)�� �����ϱ� ���� ���(���� ��ȯ)

import java.io.Serializable;		// ��ü ���� ������� ���� �������̽�
import java.io.ObjectInputStream;	// ��ü ���� �Է�
import java.io.ObjectOutputStream;	// ��ü ���� ���

class Room implements Serializable {
	private User user = new User();	// ����� ���� ��ü
	
	private String roomName;		// �ش� ���� �̸�
	private int roomType;			// �ش� ���� Ÿ��
									// --> 0: 1�ν�, 1: 2�ν�, 2: 3�ν�
	private boolean state;			// �ش� ���� ���� ����
									// --> true: ��� ��, false: �������
	private Date startTime;			// �ش� �濡 ����(üũ��)�� �ð�(��¥)
	
	// ������
	Room() {}	// �μ� ���� ������ ���
	Room(String roomName, int roomType)	// �� �̸�, �� Ÿ�� �޾Ƽ� ����
	{
		// state �ʵ�� false�� �ڵ� �ʱ�ȭ �ǹǷ� ���� �ʱ�ȭ���� ����
		this.roomName = roomName;
		this.roomType = roomType;
	}
	
	// Serializable �������̽� ����
	private void writeObject(ObjectOutputStream out) throws java.io.IOException
	{
		out.defaultWriteObject();
	}
	private void readObject(ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	// user ��ü�� ���� getter, setter �޼ҵ�
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	
	// roomName �ʵ忡 ���� getter, setter �޼ҵ�
	public String getRoomName()
	{
		return roomName;
	}
	public void setRoomName(String roomName)
	{
		this.roomName = roomName;
	}
	
	// roomType �ʵ忡 ���� getter, setter �޼ҵ�
	public int getRoomType()
	{
		return roomType;
	}
	public void setRoomType(int roomType)
	{
		this.roomType = roomType;
	}
	
	// state �ʵ忡 ���� getter, setter �޼ҵ�
	public boolean getState()
	{
		return state;
	}
	public void setState(boolean state)
	{
		this.state = state;
	}
	
	// startTime �ʵ忡 ���� getter, setter �޼ҵ�
	public Date getStartTime()
	{
		return startTime;
	}
	public String getStartTimeFormat()	// �ð� ������ ���Ŀ� ���� ���ڿ��� ��ȯ
	{	// �ð� ���� ����
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� aa hh�� mm��");
		
		// �ش� �������� �ð��� ���ڿ��� ��ȯ
		return dateFormat.format(startTime);
	}
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	
	// ---------- �� �� ��� ---------- \\
	
	// üũ�� �޼ҵ�
	// ����ڰ� Ư�� �濡 ������ �� ���
	// ����� ���� ��ü�� �� ��ü�� ����
	public void checkIn(User user) throws Exception
	{
		if (getState() == false)
		{	// �� ���� ������� ���
			setUser(user);						// �ش� �濡 ����� ���� �ֱ�
			setState(true);						// ��� ������ �� ���� ��ȯ
			setStartTime(new Date());			// ���� ��¥�� �ð��� ������ ��ü ����
		}
		else
		{	// üũ�ο� �������� ��� �ͼ��� �߻�
			throw new Exception("üũ�� ����");
		}	
	}
	
	// ���� �޼ҵ�(üũ�ƿ� ��� ����)
	// üũ�ƿ�(����)�� �� ��� - �� �̸����� ������ ���� �Ŵ������� �˻��� ����
	// ����ڰ� ������ ���� ��ȯ��(����ڰ� UI���� Ȯ���ϱ� ����)
	// �ܰ� �ҷ�����, ���� ������ ���� ������ ��ü �޾ƿ�
	public int pay(Management mg)	throws Exception
	{
		int cash;	// ������ �� ���� ����
		
		// �ش�(üũ�ƿ���) ���� ã���� ���
		if (getState() == true)
		{
			Date afterTime = new Date();	// ���� �ð��� ��¥�� ������ ��ü ����
			
			// ������ �ð����� ��������� �ð� ���ϱ�
			int keepTime = (int)(afterTime.getTime() - getStartTime().getTime());
			
			// getTime() �޼ҵ�� Date�� ms ������ ��ȯ�ϱ� ������
			// �ð�(h) ������ �˱� ���ؼ��� (1000 * 60 * 60)�� ������� �Ѵ�.
			keepTime = keepTime / 1000 / 60 / 60;	// h ������ �ӹ��� �ð� ���ϱ�
			
			// (�ð���)�ܰ� * �ӹ��� �ð�
			cash = mg.getRoomTypePrice()[getRoomType()] * keepTime;
			
			// income(����) �迭�� ���� ����
			Calendar cal = Calendar.getInstance();		// Date�� �ð��� Ȱ���ϱ� ���� Calendar ��ü ����
			cal.setTime(afterTime);						// üũ�ƿ� ���� �������� ���� �����ϱ� ����
			int today = cal.get(Calendar.DAY_OF_MONTH);	// üũ�ƿ� ���� today�� ����(1���̸� 1�� �����)
			mg.plusIncome(today, cash);					// �� ���ڿ� �ش��ϴ� �ε����� ���� ���ϱ�
			
			// �� ���¸� �� ������ ����
			setState(false);
			
			return cash;	// �������� ���Ұ� ��ȯ
		}
		
		// ���� üũ�ƿ��� �������� ���
		throw new Exception("üũ�ƿ� ����");
	}
}
