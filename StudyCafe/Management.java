// ���� �ý��� Ŭ����

import java.io.Serializable;		// ��ü ���� ������� ���� �������̽�
import java.io.ObjectInputStream;	// ��ü ���� �Է�
import java.io.ObjectOutputStream;	// ��ü ���� ���

// �� Ÿ���� ������ �� �����̸�, 1�ν�, 2�ν�, 3�νǷ� ������
class Management implements Serializable {
	private Room[] room = new Room[50];			// Room ��ü �迭 ����
	private int roomCount = 0;					// ���� ������ ���� ����
	
	private int[] income = new int[31];			// ���� ���� �迭(�� ����)
	private int[] roomTypePrice = new int[3];	// �� Ÿ�Ժ� �ܰ� ���� �迭
	// 0�� �ε����� 1�ν� �ܰ� ~ 2�� �ε����� 3�ν� �ܰ� �����ϴ� ���
	
	
	// ������
	Management() {}		// �μ� ���� ������ ���
	Management(int[] roomTypePrice)	// �� Ÿ�Ժ� �ܰ��� �޾� �ʱ�ȭ
	{
		// income �迭�� 0���� �ڵ� �ʱ�ȭ �ǹǷ� ���� �ʱ�ȭ ���� ����
		for (int i = 0; i < this.roomTypePrice.length; i++)
			this.roomTypePrice[i] = roomTypePrice[i];
	}
	
	// Serializable �������̽� ����
	private void writeObject(ObjectOutputStream out) throws java.io.IOException
	{
		out.defaultWriteObject();
	}
	private void readObject(ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	// Room ��ü �迭 �ʵ忡 ���� getter, setter �޼ҵ�
	// ��ü �迭 ��ü ��ȯ
	public Room[] getRoom()
	{
		return room;
	}
	// �� �̸��� �Ű������� �ް�, �� ���� ã�� ��ȯ(Ư�� ��ü ��ȯ)
	public Room getSelectRoom(String roomName) throws Exception
	{
		for (int i = 0; i < room.length; i++)	// �� Ž��(�� �̸� ��������)
		{
			if (room[i] == null) // �ƿ� ���� ������ ��� �ѱ�
				continue;
			else if (room[i].getRoomName().equals(roomName))	// ���ϴ� ���� ã���� ���
			{
				return room[i];	// �ش� �� ��ü ��ȯ
			}
		}
		// �Էµ� �� �̸��� ������ �̸��� ���� ���� ã�� ������ ���
		throw new Exception("�� ã�� ����");
	}

	public void setRoom(Room[] room)
	{
		this.room = room;
	}
	
	
	// roomCount �ʵ忡 ���� getter, setter �޼ҵ�
	public int getRoomCount()
	{
		return roomCount;
	}
	public void setRoomCount(int roomCount)
	{
		this.roomCount = roomCount;
	}
	
	
	// income �ʵ忡 ���� getter, setter �޼ҵ�
	public int getIncome(int dateIndex)	throws Exception // Ư�� ������ ���� ��ȯ
	{	// dateIndex�� 1~31 ������ ���� ������ �ͼ��� �߻�
		if (!(dateIndex >= 1 && dateIndex <= 31))
			throw new Exception();
		
		// ��¥�� 1~31 ������ �����̹Ƿ� 1 ���ҽ��� �ε����� Ȱ����
		return income[--dateIndex];
	}
	public int getTotalIncome()		// ������� income �迭�� ����� �� ���� ��ȯ
	{
		int sum = 0;	// �հ踦 ���� ����
		
		for (int i : income)	// income�� �ִ� ��Ҹ� ���ʷ� i�� �ֱ�
			sum += i;
		
		return sum;
	}
	
	public void setIncome(int dateIndex, int money)	// �ش� ��¥�� ����� ���� ����
	{	// ��¥�� 1~31 ������ �����̹Ƿ� 1 ���ҽ��� �ε����� Ȱ����
		income[--dateIndex] = money;
	}
	public void plusIncome(int dateIndex, int money) // �ش� ��¥�� ���� plus
	{	// ��¥�� 1~31 ������ �����̹Ƿ� 1 ���ҽ��� �ε����� Ȱ����
		income[--dateIndex] += money;
	}
	
	
	// roomTypePrice �ʵ忡 ���� getter, setter �޼ҵ�
	public int[] getRoomTypePrice()
	{
		return roomTypePrice;
	}
	public void setRoomTypePrice(int[] price)
	{
		for (int i = 0; i < roomTypePrice.length; i++)
			roomTypePrice[i] = price[i];
	}
	
	
	
	// ----------------- �� �� ��� �޼ҵ� ----------------- \\
	
	
	
	// �� �߰� �޼ҵ� - � ���� �߰�����(�� �̸� ����), �� �ν����� ����
	public void addRoom(String roomName, int roomType) throws Exception
	{	// roomName : ���� �̸�, roomType : �� Ÿ��(���ν�����) ��ȣ(0~2)
		
		boolean success = false;	// �� �߰� ���� �Ǵ��� ���� ����
		
		if (roomType <= 0 || roomType >= 4)	// �Է¹��� roomType�� 0 ����, 4 �̻��� ���
			throw new Exception("�߸��� �� Ÿ��");
		
		for (int i = 0; i < room.length; i++)	// ���� �߰��� �ڸ� ã��
		{
			if (room[i] == null) {	// �� ������ ã���� ���(�� �߰� ����)
				room[i] = new Room(roomName, --roomType);
				// roomType�� �����ڰ� �Է��ϴ� ���ڿ� ���� ���� �ϴ� ���ڰ� 1 ���̳��Ƿ�
				// �̸� --������ ���� 1 ���ҽ�Ų ���� ����
				// ex) 1�ν��� ���� 1 ���� �Է� -> ���� ���� �Ű����� ���� 0
				
				roomCount++;		// ���� ������ �� ���� ����
				success = true;		// �� �߰� ����
				break;
			}
			else if (room[i].getRoomName().equals(roomName))	// �̹� �ִ� ���� ���
				throw new Exception("�̹� ���� ������");
		}
		
		if (!success)	// success�� false�̸�
			throw new Exception("�� �߰� ���� ����");
	}
	
	// �� ���� �޼ҵ� - � ���� ��������(�� �̸����� Ž��)
	public void deleteRoom(String roomName) throws Exception
	{
		boolean success = false;
		
		for (int i = 0; i < roomCount; i++)	// (roomCount-1)�� ����� �˻�
		{
			if (room[i].getRoomName().equals(roomName))	// ���� ã���� ���
			{
				room[i] = null;		// �ش� ��ü�� ���� ���� ����
				roomCount--;		// ���� �����Ǿ��ִ� �� ���� ����
				success = true;		// �� ���� ����
				break;
			}
		}
		
		if (!success)	// ������ ���� ���� ���
			throw new Exception("�� ���� ����");
	}
	
	// ��� �� �� ã�� �޼ҵ� - Ư�� Ÿ���� �� �� ��� ã��(���ڿ� ��ȯ)
	public String searchRoom(int roomType) throws Exception
	{
		String result = "";		// ����� ���ڿ��� ��ȯ�ϱ� ����
		
		roomType--;		// ���� ���� �ϴ� �ε����� ���߱� ���� 1 ����
		
		if (!(roomType >= 0 && roomType <= 2))	// �Է¹��� �� Ÿ�� ��ȣ�� 0~2 ���̰� �ƴ� ���
		{
			throw new Exception("�߸��� �� Ÿ��");
		}
		
		for (int i = 0; i < roomCount; i++)	// 0�� ����� (roomCount-1)�� ����� �˻�
		{
			// ã�� �� Ÿ�԰� ��ġ�ϸ鼭 ��� �ִ� ������ �� �̸��� ���ڿ��� ����
			if (room[i].getRoomType() == roomType && room[i].getState() == false)
			{
				result += room[i].getRoomName() + '\n';
			}
		}
		
		if (result == "")	// ���� �ƹ� �� �浵 ã�� ������ ���
			throw new Exception("���� �� �� ����");
		
		return result;
	}
}
