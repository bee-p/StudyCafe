// UI Ŭ����

import java.util.Scanner;			// �Է� �ޱ� ����

// ���� ������� ���� import
import java.io.FileInputStream;		// ����Ʈ ������ �Է�(ObjectInputStream�� ����)
import java.io.FileOutputStream;	// ����Ʈ ������ ���(ObjectOutputStream�� ����)
import java.io.ObjectInputStream;	// ��ü ���� �Է�
import java.io.ObjectOutputStream;	// ��ü ���� ���

// ���α׷� ���۽� Management ��ü ������ ��� �ҷ�����,
// ���α׷� ����� ��������� Management ��ü ������ dat ���Ͽ� ��� �����Ѵ�.
// �̿��� Management ��ü �ʵ�, Room ��ü �迭, 
// ������ Room ��ü ����, �̿� ���Ե� User ���� ��θ� �ǹ��Ѵ�.

class UI {
	public static void main(String[] args)
	{
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		Management management = new Management();	// ���� �ý��� ��ü ����
		
		try {	// ���� �о����
			in = new ObjectInputStream(new FileInputStream("StudyCafe_Manager.dat"));
			management = (Management) in.readObject();
		}
		catch (java.io.FileNotFoundException fnfe) {	// ������ ���� ���
			System.out.println("�ҷ��� ������ �����ϴ�.");
			System.out.println("���͵� ī�並 ���� �����մϴ�.");
		}
		catch (java.io.EOFException eofe) {		// ���̻� ���� ��ü�� ���� ���(�ӽ�)
			System.out.println("��� ������ �� �о����ϴ�.");
		}
		catch (java.io.IOException ioe) {
			System.out.println("������ ���� �� �����ϴ�.");
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("�ش� Ŭ������ �������� �ʽ��ϴ�.");
		}
		finally {
			try {	// �Է� ��Ʈ�� �ݱ�
				in.close();
			}
			catch (Exception e) {}
		}
		
		Scanner scan = new Scanner(System.in);		// �Է��� �ޱ� ���� ��ĳ�� ��ü
		
		int mainChoice;				// (����ȭ��)���� ��ȣ�� ���� ����
		int managerChoice;			// (�����ڸ��)���� ��ȣ�� ���� ����
		int userChoice;				// (����ڸ��)���� ��ȣ�� ���� ����
		
		String roomName;			// �� �̸�
		int roomType;				// �� Ÿ��
		User user = new User();		// ����� ������ �Է¹ޱ� ���� �ӽ� ��ü ����
		
		String roomList;			// �� ����� ���ڿ��� ����ϱ� ���� �ӽ� ����
		int cash;					// ����ڰ� ������ ���� �˷��ֱ� ���� ����
		int[] price	= new int[3];	// �ܰ� ���� ������ ���� ����
		int date;					// ���ϴ� ��¥�� �Է¹ޱ� ���� ����
		
		while(true)
		{	// ���� �޴� ���
			System.out.println("\n�ȳ��ϼ���. � ��带 �����Ͻðڽ��ϱ�?");
			System.out.println("�Ʒ� �޴��� ���� ���ϴ� ����� ���ڸ� �Է��Ͻʽÿ�.");
			System.out.println("1. ������ ���");
			System.out.println("2. ����� ���");
			System.out.println("3. ����\n");
			
			try {	// ������ �ƴϰų�, ���� ������ ��� �Է� try-catch
				mainChoice = scan.nextInt();
			}
			catch (java.util.InputMismatchException e) {
				scan = new Scanner(System.in);	// Scanner ���׸� �ذ��ϱ� ���� �ڵ�
				System.out.println("�߸��� �Է��Դϴ�.");
				continue;
			}
			
			if (mainChoice == 1)			// ������ ���
			{
				while (true)
				{	// ������ �޴� ���
					System.out.println("\n������ ��� �Դϴ�.");
					System.out.println("���ϴ� �������� ���ڸ� �Է��Ͻʽÿ�.");
					System.out.println("1. �� �����(�߰�)");
					System.out.println("2. �� �����(����)");
					System.out.println("3. �� �� ã��");
					System.out.println("4. �� Ÿ�Ժ� �ܰ� �����ϱ�");
					System.out.println("5. �� Ÿ�Ժ� �ܰ� Ȯ���ϱ�");
					System.out.println("6. �Ϻ� ���� Ȯ���ϱ�");
					System.out.println("7. �̹� �� ���� Ȯ���ϱ�");
					System.out.println("8. ������ ��� ����\n");
					
					try {	// ������ �ƴϰų�, ���� ������ ��� �Է� try-catch
						managerChoice = scan.nextInt();
					}
					catch (java.util.InputMismatchException e) {
						scan = new Scanner(System.in);	// Scanner ���׸� �ذ��ϱ� ���� �ڵ�
						System.out.println("�߸��� �Է��Դϴ�.");
						continue;
					}
					
					if (managerChoice == 1)			// �� �߰�
					{
						System.out.println("�߰��� �� �̸��� �Է��Ͻʽÿ�.");
						
						roomName = scan.next();	// �� �̸� �Է� �ޱ�
						
						System.out.println("�ش� ���� �� �ν� �Դϱ�? (1~3)");
						System.out.println("ex) 1�ν��� ���: 1 �Է�");
						
						try {	
							roomType = scan.nextInt();	// �� Ÿ�� �Է¹ޱ�
							
							management.addRoom(roomName, roomType);	// �� ����� ����
						}
						catch (java.util.InputMismatchException e) {	// ������ �ƴϰų�, ���� ������ ��� �Է� catch
							scan = new Scanner(System.in);	// Scanner ���׸� �ذ��ϱ� ���� �ڵ�
							System.out.println("�߸��� �Է��Դϴ�.");
							continue;
						}
						catch (Exception e)	// �� �߰��� �ϴ� �������� �߻��� �ͼ��� ó��
						{
							// �� �ͼ��� ����, �׿� ���� �ȳ� �޽��� ���
							if (e.getMessage().equals("�߸��� �� Ÿ��"))
							{
								System.out.println("���� Ÿ���� �ùٸ��� �ʽ��ϴ�.");
							}
							else if (e.getMessage().equals("�̹� ���� ������"))
							{
								System.out.println("�ش� ���� �̹� �����մϴ�.");
							}
							else	// ���� �߰��� ������ �� ã�� ���
							{
								System.out.println("���̻� ���� �߰��� �� �����ϴ�.");
							}
							
							continue;
						}
						
						System.out.println("�� �߰��� �Ϸ��Ͽ����ϴ�.");
					}
					else if (managerChoice == 2)	// �� ����
					{
						System.out.println("������ �� �̸��� �Է��Ͻʽÿ�.");
						
						roomName = scan.next();	// �� �̸� �Է� �ޱ�
						
						try {
							management.deleteRoom(roomName);	// �� ����� ����
						}
						catch (Exception e)	// �� ���Ÿ� �ϴ� �������� �߻��� �ͼ��� ó��
						{
							System.out.println("������ ���� �����ϴ�.");
							continue;
						}
						
						System.out.println("�� ���Ÿ� �Ϸ��Ͽ����ϴ�.");
					}
					else if (managerChoice == 3)	// �� �� ã��
					{
						System.out.println("�� �ν��� �� ���� Ž���Ͻðڽ��ϱ�?");
						System.out.println("ex) 1�ν��� �� ���� ���ϴ� ���: 1 �Է�");
						
						try {	
							roomType = scan.nextInt();	// �� Ÿ�� �Է� �ޱ�
						}
						catch (java.util.InputMismatchException e) {	// ������ �ƴϰų�, ���� ������ ��� �Է� catch
							scan = new Scanner(System.in);	// Scanner ���׸� �ذ��ϱ� ���� �ڵ�
							System.out.println("�߸��� �Է��Դϴ�.");
							continue;
						}
						
						try {
							roomList = management.searchRoom(roomType);
						}
						catch (Exception e)		// �� �� ã�� �������� �߻��� �ͼ���
						{
							if (e.getMessage().equals("�߸��� �� Ÿ��"))
							{
								System.out.println("���� Ÿ���� �ùٸ��� �ʽ��ϴ�.");
							}
							else	// �ƹ� �� �浵 ã�� ���� ���
							{
								System.out.println("���� �����ִ� �� ���� �����ϴ�.");
							}
							
							continue;
						}
						
						System.out.println("�� �� Ž�� ����Դϴ�.");
						System.out.println(roomList);
					}
					else if (managerChoice == 4)	// Ÿ�Ժ� �ܰ� ����
					{
						try {
							// �迭�� �ܰ� �Է�(1�ν�~3�ν� ����)
							for (int i = 0; i < price.length; i++)
							{
								System.out.println((i+1) + "�ν� �ܰ��� �Է��Ͻʽÿ�. (���� ���ڸ� �Է�)");
								price[i] = scan.nextInt();
							}
						}
						catch (java.util.InputMismatchException e) {	// ������ �ƴϰų�, ���� ������ ��� ���� �Է� �޾��� ���
							System.out.println("�߸��� �Է��Դϴ�.");
						}
						
						management.setRoomTypePrice(price);		// �ܰ� ����
						
						System.out.println("�ܰ� ������ �Ϸ��߽��ϴ�.");
						
					}
					else if (managerChoice == 5)	// Ÿ�Ժ� �ܰ� Ȯ��
					{
						System.out.println("�ܰ� ����Դϴ�.");
						for (int i = 0; i < price.length; i++)
						{
							price[i] = management.getRoomTypePrice()[i];
							System.out.println((i+1) + "�ν� �ð��� �ܰ�: " + price[i] + "��");
						}
					}
					else if (managerChoice == 6)	// �Ϻ�(Ư�� ��¥) ���� Ȯ��
					{
						System.out.println("������ Ȯ���� ��¥�� �Է��Ͻʽÿ�. (���ڸ� �Է��� ��)");
						System.out.println("ex) 10���� ������ Ȯ���ϰ� �ʹٸ� 10 �Է�");
						
						try {	
							date = scan.nextInt();	// ���ϴ� ��¥ �Է� �ޱ�
						}
						catch (java.util.InputMismatchException e) {	// ������ �ƴϰų�, ���� ������ ��� �Է� catch
							scan = new Scanner(System.in);	// Scanner ���׸� �ذ��ϱ� ���� �ڵ�
							System.out.println("�߸��� �Է��Դϴ�.");
							continue;
						}
						
						try {	// �ش� ��¥�� ���� ���
							System.out.println(date + "���� ������ " + management.getIncome(date) + "�� �Դϴ�.");
						}
						catch (Exception e)	// �������� �ʴ� ��¥�� �Է����� ���
						{
							System.out.println("�߸��� ��¥�Դϴ�.");
						}
					}
					else if (managerChoice == 7)	// �̹� �� ���� Ȯ��(�̹� ��, ���ݱ����� ����)
					{
						System.out.println("�̹� ���� ������ " + management.getTotalIncome() + "�� �Դϴ�.");
					}
					else if (managerChoice == 8)	// ����
					{
						System.out.println("������ ��带 �����մϴ�.");
						break;		// ���� while�� ���������� ��
					}
					else	// �������� ���� ��ȣ�� �Է����� ���
					{		// ������ �߸��� ���� �Է� �޾��� ����� �ͼ��� ó���� �� �־����Ƿ�,
							// �̴� ���� try-catch���� ������ ����
						System.out.println("�������� �ʴ� ������ �Դϴ�.");
					}
					
				}
			}
			else if (mainChoice == 2)	// ����� ���
			{
				while (true)
				{	// ����� �޴� ���
					System.out.println("\n����� ��� �Դϴ�.");
					System.out.println("���ϴ� �������� ���ڸ� �Է��Ͻʽÿ�.");
					System.out.println("1. üũ��");
					System.out.println("2. üũ�ƿ� �� ��� ����");
					System.out.println("3. ����� ��� ����\n");
					
					try {	// ������ �ƴϰų�, ���� ������ ��� �Է� try-catch
						userChoice = scan.nextInt();	// ����� ������ �Է¹ޱ�
					}
					catch (java.util.InputMismatchException e) {
						scan = new Scanner(System.in);	// Scanner ���׸� �ذ��ϱ� ���� �ڵ�
						System.out.println("�߸��� �Է��Դϴ�.");
						continue;
					}
					
					if (userChoice == 1)	// üũ��
					{
						// (1) ���ϴ� Ÿ���� �� �� ��� �����ֱ�
						System.out.println("�� �ν��� ���Ͻʴϱ�?");
						System.out.println("ex) 1�ν��� ���ϴ� ���: 1 �Է�");
						
						try {	
							roomType = scan.nextInt();	// �� Ÿ�� �Է� �ޱ�
						}
						catch (java.util.InputMismatchException e) {	// ������ �ƴϰų�, ���� ������ ��� �Է� catch
							scan = new Scanner(System.in);	// Scanner ���׸� �ذ��ϱ� ���� �ڵ�
							System.out.println("�߸��� �Է��Դϴ�.");
							continue;
						}
						
						try {
							roomList = management.searchRoom(roomType);
						}
						catch (Exception e)		// �� �� ����� �ҷ����� ���� �߻��� �ͼ���
						{
							if (e.getMessage().equals("�߸��� �� Ÿ��"))
							{
								System.out.println("���� Ÿ���� �ùٸ��� �ʽ��ϴ�.");
							}
							else	// �ƹ� �� �浵 ã�� ���� ���
							{
								System.out.println("���� �����ִ� �� ���� �����ϴ�.");
							}
							
							continue;
						}
						// ���������� �ҷ��� �� ��� ���ڿ� ���
						System.out.println("���� üũ�� ������ �� ��� �Դϴ�.");
						System.out.println(roomList);
						
						
						// (2) ���ϴ� �� �̸� �Է��� üũ�� ����(����� ���� �Է�)
						System.out.println("üũ���� �� �̸��� �Է����ֽʽÿ�.");
						roomName = scan.next();
						
						System.out.println("������� �̸��� �����ֽʽÿ�.");
						System.out.println("�ο��� ���� ���̶��, ��ǥ�� �� ���� �̸��� �����ֽʽÿ�.");
						user.setUserName(scan.next());
						
						System.out.println("������� ��ȭ��ȣ�� �����ֽʽÿ�.");
						System.out.println("�ο��� ���� ���̶��, ��ǥ�� �� ���� ��ȭ��ȣ�� �����ֽʽÿ�.");
						System.out.println("ex) 010-1234-5678");
						user.setUserPhone(scan.next());
						
						try {	// �ش� ���� ã�� �� �濡 ���� ���� �ְ� üũ���ϱ�
							management.getSelectRoom(roomName).checkIn(user);
						}
						catch (Exception e)	// �� ã��, üũ�� �������� �߻��� �ͼ��� ó��
						{
							if (e.getMessage().equals("�� ã�� ����"))
							{
								System.out.println("�ش� ���� �������� �ʽ��ϴ�.");
							}
							else if (e.getMessage().equals("üũ�� ����"))
							{
								System.out.println("�ش� ���� üũ���� �Ұ����մϴ�.");
							}
							
							continue;
						}
						
						// üũ�� ���� �� �ȳ� �޽���(��� Ȯ��)
						System.out.println(roomName + "�� üũ���� �Ϸ��߽��ϴ�.");
						try {
							System.out.println("��ϵ� �̸�: " + management.getSelectRoom(roomName).getUser().getUserName());
							System.out.println("��ϵ� ��ȭ��ȣ: " + management.getSelectRoom(roomName).getUser().getUserPhone());
							System.out.println("üũ�� �ð�(��¥): " + management.getSelectRoom(roomName).getStartTimeFormat());
						}
						catch (Exception e)	// �濡 ����� ������ �ҷ����� ���� �߻��ϴ� �ͼ��� ó��
						{
							if (e.getMessage().equals("�� ã�� ����"))
							{
								System.out.println("�ش� ���� �������� �ʽ��ϴ�.");
							}
							else	// �� �� �ͼ���
							{
								System.out.println("������ �ҷ����µ� �����߽��ϴ�.");
							}
							
							continue;
						}
					}
					else if (userChoice == 2)	// üũ�ƿ� �� ��� ����
					{
						System.out.println("üũ�ƿ��� �� �̸��� �Է����ֽʽÿ�.");
						roomName = scan.next();
						
						try {	// ���� �޼ҵ带 ���� ���� �ݾ� ����, üũ�ƿ� ó��
							cash = management.getSelectRoom(roomName).pay(management);
						}
						catch (Exception e)	// üũ�ƿ��� �������� ��� �ͼ��� ó��
						{
							if (e.getMessage().equals("�� ã�� ����"))
							{
								System.out.println("�ش� ���� �������� �ʽ��ϴ�.");
							}
							else if (e.getMessage().equals("üũ�ƿ� ����"))
							{
								System.out.println("üũ�ƿ��� �����߽��ϴ�.");
							}
							
							continue;
						}
						
						System.out.println(cash + "���� �����߽��ϴ�.");	// ����ڰ� ������ �ݾ� ǥ��
						System.out.println("üũ�ƿ��� �Ϸ��߽��ϴ�. �̿����ּż� �����մϴ�.");
					}
					else if (userChoice == 3)	// ����� ��� ����
					{
						System.out.println("����� ��带 �����մϴ�.");
						break;		// ���� while���� ���������� ��
					}
					else	// �������� ���� ��ȣ�� �Է����� ���
					{		// ������ �߸��� ���� �Է� �޾��� ����� �ͼ��� ó���� �� �־����Ƿ�,
							// �̴� ���� try-catch���� ������ ����
						System.out.println("�������� �ʴ� ������ �Դϴ�.");
					}
				}
			}
			else if (mainChoice == 3)	// ���� �޴� ����
			{
				try {	// ���� ����(���, ����)
					out = new ObjectOutputStream(new FileOutputStream("StudyCafe_Manager.dat"));
					out.writeObject(management);
					
					// ���۸��Ǿ� ���� ��ϵ��� ���� �����͸� ��� ��Ʈ���� ��� ���
					out.flush();
				}
				catch (java.io.IOException ioe) {
					System.out.println("���Ϸ� ����� �� �����ϴ�.");
				}
				finally {
					try {	// ��� ��Ʈ�� �ݱ�
						out.close();
					}
					catch (Exception e) {}
				}
				
				System.out.println("�����մϴ�.");
				break;
			}
			else	// �������� ���� ��ȣ�� �Է����� ���
			{		// ������ �߸��� ���� �Է� �޾��� ����� �ͼ��� ó���� �� �־����Ƿ�,
					// �� ���, ���� try-catch���� ��������� ����
				System.out.println("�������� �ʴ� ������ �Դϴ�.");
			}
		}
	}
}