// 방 객체 클래스
// 사용자 객체를 이 곳에 넣어 함께 관리/보관함

import java.util.Calendar;
import java.util.Date;				// Date 클래스 사용 - 시간(날짜) 불러오기 위함
import java.text.SimpleDateFormat;	// 시간(Date)을 포맷하기 위해 사용(형식 변환)

import java.io.Serializable;		// 객체 파일 입출력을 위한 인터페이스
import java.io.ObjectInputStream;	// 객체 파일 입력
import java.io.ObjectOutputStream;	// 객체 파일 출력

class Room implements Serializable {
	private User user = new User();	// 사용자 정보 객체
	
	private String roomName;		// 해당 방의 이름
	private int roomType;			// 해당 방의 타입
									// --> 0: 1인실, 1: 2인실, 2: 3인실
	private boolean state;			// 해당 방의 현재 상태
									// --> true: 사용 중, false: 비어있음
	private Date startTime;			// 해당 방에 입장(체크인)한 시간(날짜)
	
	// 생성자
	Room() {}	// 인수 없는 생성자 명시
	Room(String roomName, int roomType)	// 방 이름, 방 타입 받아서 생성
	{
		// state 필드는 false로 자동 초기화 되므로 따로 초기화하지 않음
		this.roomName = roomName;
		this.roomType = roomType;
	}
	
	// Serializable 인터페이스 구현
	private void writeObject(ObjectOutputStream out) throws java.io.IOException
	{
		out.defaultWriteObject();
	}
	private void readObject(ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	// user 객체에 대한 getter, setter 메소드
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	
	// roomName 필드에 대한 getter, setter 메소드
	public String getRoomName()
	{
		return roomName;
	}
	public void setRoomName(String roomName)
	{
		this.roomName = roomName;
	}
	
	// roomType 필드에 대한 getter, setter 메소드
	public int getRoomType()
	{
		return roomType;
	}
	public void setRoomType(int roomType)
	{
		this.roomType = roomType;
	}
	
	// state 필드에 대한 getter, setter 메소드
	public boolean getState()
	{
		return state;
	}
	public void setState(boolean state)
	{
		this.state = state;
	}
	
	// startTime 필드에 대한 getter, setter 메소드
	public Date getStartTime()
	{
		return startTime;
	}
	public String getStartTimeFormat()	// 시간 정보를 형식에 맞춰 문자열로 반환
	{	// 시간 형식 생성
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시 mm분");
		
		// 해당 형식으로 시간을 문자열로 반환
		return dateFormat.format(startTime);
	}
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	
	// ---------- 그 외 기능 ---------- \\
	
	// 체크인 메소드
	// 사용자가 특정 방에 입장할 때 사용
	// 사용자 정보 객체를 방 객체에 넣음
	public void checkIn(User user) throws Exception
	{
		if (getState() == false)
		{	// 그 방이 비어있을 경우
			setUser(user);						// 해당 방에 사용자 정보 넣기
			setState(true);						// 사용 중으로 방 상태 변환
			setStartTime(new Date());			// 현재 날짜와 시간을 저장한 객체 생성
		}
		else
		{	// 체크인에 실패했을 경우 익셉션 발생
			throw new Exception("체크인 실패");
		}	
	}
	
	// 지불 메소드(체크아웃 기능 포함)
	// 체크아웃(퇴장)할 때 사용 - 방 이름으로 퇴장할 방을 매니저에서 검색후 받음
	// 사용자가 지불한 값을 반환함(사용자가 UI에서 확인하기 위함)
	// 단가 불러오기, 수익 저장을 위해 관리자 객체 받아옴
	public int pay(Management mg)	throws Exception
	{
		int cash;	// 내야할 돈 저장 변수
		
		// 해당(체크아웃할) 방을 찾았을 경우
		if (getState() == true)
		{
			Date afterTime = new Date();	// 현재 시간과 날짜을 저장한 객체 생성
			
			// 입장한 시각부터 현재까지의 시간 구하기
			int keepTime = (int)(afterTime.getTime() - getStartTime().getTime());
			
			// getTime() 메소드는 Date를 ms 단위로 반환하기 때문에
			// 시간(h) 단위를 알기 위해서는 (1000 * 60 * 60)을 나누어야 한다.
			keepTime = keepTime / 1000 / 60 / 60;	// h 단위의 머무른 시간 구하기
			
			// (시간당)단가 * 머무른 시간
			cash = mg.getRoomTypePrice()[getRoomType()] * keepTime;
			
			// income(수익) 배열에 수익 저장
			Calendar cal = Calendar.getInstance();		// Date형 시간을 활용하기 위해 Calendar 객체 생성
			cal.setTime(afterTime);						// 체크아웃 일자 기준으로 수익 저장하기 위함
			int today = cal.get(Calendar.DAY_OF_MONTH);	// 체크아웃 일자 today에 저장(1일이면 1이 저장됨)
			mg.plusIncome(today, cash);					// 그 일자에 해당하는 인덱스에 수익 더하기
			
			// 방 상태를 빈 방으로 변경
			setState(false);
			
			return cash;	// 정상적인 지불값 반환
		}
		
		// 만일 체크아웃에 실패했을 경우
		throw new Exception("체크아웃 실패");
	}
}
