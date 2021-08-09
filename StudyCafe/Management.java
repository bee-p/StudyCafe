// 관리 시스템 클래스

import java.io.Serializable;		// 객체 파일 입출력을 위한 인터페이스
import java.io.ObjectInputStream;	// 객체 파일 입력
import java.io.ObjectOutputStream;	// 객체 파일 출력

// 방 타입의 종류는 세 가지이며, 1인실, 2인실, 3인실로 고정함
class Management implements Serializable {
	private Room[] room = new Room[50];			// Room 객체 배열 생성
	private int roomCount = 0;					// 현재 생성된 방의 개수
	
	private int[] income = new int[31];			// 수익 저장 배열(월 단위)
	private int[] roomTypePrice = new int[3];	// 방 타입별 단가 저장 배열
	// 0번 인덱스에 1인실 단가 ~ 2번 인덱스에 3인실 단가 저장하는 방식
	
	
	// 생성자
	Management() {}		// 인수 없는 생성자 명시
	Management(int[] roomTypePrice)	// 방 타입별 단가를 받아 초기화
	{
		// income 배열은 0으로 자동 초기화 되므로 따로 초기화 하지 않음
		for (int i = 0; i < this.roomTypePrice.length; i++)
			this.roomTypePrice[i] = roomTypePrice[i];
	}
	
	// Serializable 인터페이스 구현
	private void writeObject(ObjectOutputStream out) throws java.io.IOException
	{
		out.defaultWriteObject();
	}
	private void readObject(ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	// Room 객체 배열 필드에 대한 getter, setter 메소드
	// 객체 배열 전체 반환
	public Room[] getRoom()
	{
		return room;
	}
	// 방 이름을 매개변수로 받고, 그 방을 찾아 반환(특정 객체 반환)
	public Room getSelectRoom(String roomName) throws Exception
	{
		for (int i = 0; i < room.length; i++)	// 방 탐색(방 이름 기준으로)
		{
			if (room[i] == null) // 아예 없는 공간일 경우 넘김
				continue;
			else if (room[i].getRoomName().equals(roomName))	// 원하는 방을 찾았을 경우
			{
				return room[i];	// 해당 방 객체 반환
			}
		}
		// 입력된 방 이름과 동일한 이름을 가진 방을 찾지 못했을 경우
		throw new Exception("방 찾기 실패");
	}

	public void setRoom(Room[] room)
	{
		this.room = room;
	}
	
	
	// roomCount 필드에 대한 getter, setter 메소드
	public int getRoomCount()
	{
		return roomCount;
	}
	public void setRoomCount(int roomCount)
	{
		this.roomCount = roomCount;
	}
	
	
	// income 필드에 대한 getter, setter 메소드
	public int getIncome(int dateIndex)	throws Exception // 특정 일자의 수익 반환
	{	// dateIndex가 1~31 범위에 있지 않을시 익셉션 발생
		if (!(dateIndex >= 1 && dateIndex <= 31))
			throw new Exception();
		
		// 날짜는 1~31 범위의 숫자이므로 1 감소시켜 인덱스로 활용함
		return income[--dateIndex];
	}
	public int getTotalIncome()		// 현재까지 income 배열에 저장된 총 수익 반환
	{
		int sum = 0;	// 합계를 구할 변수
		
		for (int i : income)	// income에 있는 요소를 차례로 i에 넣기
			sum += i;
		
		return sum;
	}
	
	public void setIncome(int dateIndex, int money)	// 해당 날짜에 저장된 수익 변경
	{	// 날짜는 1~31 범위의 숫자이므로 1 감소시켜 인덱스로 활용함
		income[--dateIndex] = money;
	}
	public void plusIncome(int dateIndex, int money) // 해당 날짜에 수익 plus
	{	// 날짜는 1~31 범위의 숫자이므로 1 감소시켜 인덱스로 활용함
		income[--dateIndex] += money;
	}
	
	
	// roomTypePrice 필드에 대한 getter, setter 메소드
	public int[] getRoomTypePrice()
	{
		return roomTypePrice;
	}
	public void setRoomTypePrice(int[] price)
	{
		for (int i = 0; i < roomTypePrice.length; i++)
			roomTypePrice[i] = price[i];
	}
	
	
	
	// ----------------- 그 외 기능 메소드 ----------------- \\
	
	
	
	// 방 추가 메소드 - 어떤 방을 추가할지(방 이름 지정), 몇 인실인지 지정
	public void addRoom(String roomName, int roomType) throws Exception
	{	// roomName : 방의 이름, roomType : 방 타입(몇인실인지) 번호(0~2)
		
		boolean success = false;	// 방 추가 성공 판단을 위한 변수
		
		if (roomType <= 0 || roomType >= 4)	// 입력받은 roomType이 0 이하, 4 이상일 경우
			throw new Exception("잘못된 방 타입");
		
		for (int i = 0; i < room.length; i++)	// 방을 추가할 자리 찾기
		{
			if (room[i] == null) {	// 빈 공간을 찾았을 경우(방 추가 수행)
				room[i] = new Room(roomName, --roomType);
				// roomType은 관리자가 입력하는 숫자와 실제 들어가야 하는 숫자가 1 차이나므로
				// 미리 --연산을 통해 1 감소시킨 값을 넣음
				// ex) 1인실을 원해 1 숫자 입력 -> 실제 들어가는 매개변수 값은 0
				
				roomCount++;		// 현재 생성된 방 개수 증가
				success = true;		// 방 추가 성공
				break;
			}
			else if (room[i].getRoomName().equals(roomName))	// 이미 있는 방일 경우
				throw new Exception("이미 방이 존재함");
		}
		
		if (!success)	// success가 false이면
			throw new Exception("방 추가 공간 없음");
	}
	
	// 방 삭제 메소드 - 어떤 방을 삭제할지(방 이름으로 탐색)
	public void deleteRoom(String roomName) throws Exception
	{
		boolean success = false;
		
		for (int i = 0; i < roomCount; i++)	// (roomCount-1)번 방까지 검사
		{
			if (room[i].getRoomName().equals(roomName))	// 방을 찾았을 경우
			{
				room[i] = null;		// 해당 객체에 대한 참조 삭제
				roomCount--;		// 현재 생성되어있는 방 개수 감소
				success = true;		// 방 제거 성공
				break;
			}
		}
		
		if (!success)	// 제거할 방이 없는 경우
			throw new Exception("방 제거 실패");
	}
	
	// 모든 빈 방 찾기 메소드 - 특정 타입의 빈 방 모두 찾기(문자열 반환)
	public String searchRoom(int roomType) throws Exception
	{
		String result = "";		// 결과를 문자열로 반환하기 위함
		
		roomType--;		// 실제 들어가야 하는 인덱스로 맞추기 위해 1 감소
		
		if (!(roomType >= 0 && roomType <= 2))	// 입력받은 방 타입 번호가 0~2 사이가 아닐 경우
		{
			throw new Exception("잘못된 방 타입");
		}
		
		for (int i = 0; i < roomCount; i++)	// 0번 방부터 (roomCount-1)번 방까지 검사
		{
			// 찾는 방 타입과 일치하면서 비어 있는 상태의 방 이름을 문자열에 넣음
			if (room[i].getRoomType() == roomType && room[i].getState() == false)
			{
				result += room[i].getRoomName() + '\n';
			}
		}
		
		if (result == "")	// 만일 아무 빈 방도 찾지 못했을 경우
			throw new Exception("현재 빈 방 없음");
		
		return result;
	}
}
