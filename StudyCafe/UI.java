// UI 클래스

import java.util.Scanner;			// 입력 받기 위함

// 파일 입출력을 위한 import
import java.io.FileInputStream;		// 바이트 데이터 입력(ObjectInputStream에 쓰임)
import java.io.FileOutputStream;	// 바이트 데이터 출력(ObjectOutputStream에 쓰임)
import java.io.ObjectInputStream;	// 객체 파일 입력
import java.io.ObjectOutputStream;	// 객체 파일 출력

// 프로그램 시작시 Management 객체 정보를 모두 불러오고,
// 프로그램 종료시 현재까지의 Management 객체 정보를 dat 파일에 모두 저장한다.
// 이에는 Management 객체 필드, Room 객체 배열, 
// 각각의 Room 객체 정보, 이에 포함된 User 정보 모두를 의미한다.

class UI {
	public static void main(String[] args)
	{
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		Management management = new Management();	// 관리 시스템 객체 생성
		
		try {	// 파일 읽어오기
			in = new ObjectInputStream(new FileInputStream("StudyCafe_Manager.dat"));
			management = (Management) in.readObject();
		}
		catch (java.io.FileNotFoundException fnfe) {	// 파일이 없는 경우
			System.out.println("불러올 파일이 없습니다.");
			System.out.println("스터디 카페를 새로 시작합니다.");
		}
		catch (java.io.EOFException eofe) {		// 더이상 읽을 객체가 없는 경우(임시)
			System.out.println("모든 정보를 다 읽었습니다.");
		}
		catch (java.io.IOException ioe) {
			System.out.println("파일을 읽을 수 없습니다.");
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("해당 클래스가 존재하지 않습니다.");
		}
		finally {
			try {	// 입력 스트림 닫기
				in.close();
			}
			catch (Exception e) {}
		}
		
		Scanner scan = new Scanner(System.in);		// 입력을 받기 위한 스캐너 객체
		
		int mainChoice;				// (메인화면)선택 번호를 받을 변수
		int managerChoice;			// (관리자모드)선택 번호를 받을 변수
		int userChoice;				// (사용자모드)선택 번호를 받을 변수
		
		String roomName;			// 방 이름
		int roomType;				// 방 타입
		User user = new User();		// 사용자 정보를 입력받기 위한 임시 객체 변수
		
		String roomList;			// 방 목록을 문자열로 출력하기 위한 임시 변수
		int cash;					// 사용자가 지불한 돈을 알려주기 위한 변수
		int[] price	= new int[3];	// 단가 관련 연산을 위한 변수
		int date;					// 원하는 날짜를 입력받기 위한 변수
		
		while(true)
		{	// 메인 메뉴 출력
			System.out.println("\n안녕하세요. 어떤 모드를 실행하시겠습니까?");
			System.out.println("아래 메뉴를 보고 원하는 모드의 숫자를 입력하십시오.");
			System.out.println("1. 관리자 모드");
			System.out.println("2. 사용자 모드");
			System.out.println("3. 종료\n");
			
			try {	// 정수가 아니거나, 정수 범위를 벗어난 입력 try-catch
				mainChoice = scan.nextInt();
			}
			catch (java.util.InputMismatchException e) {
				scan = new Scanner(System.in);	// Scanner 버그를 해결하기 위한 코드
				System.out.println("잘못된 입력입니다.");
				continue;
			}
			
			if (mainChoice == 1)			// 관리자 모드
			{
				while (true)
				{	// 관리자 메뉴 출력
					System.out.println("\n관리자 모드 입니다.");
					System.out.println("원하는 선택지의 숫자를 입력하십시오.");
					System.out.println("1. 방 만들기(추가)");
					System.out.println("2. 방 지우기(삭제)");
					System.out.println("3. 빈 방 찾기");
					System.out.println("4. 방 타입별 단가 수정하기");
					System.out.println("5. 방 타입별 단가 확인하기");
					System.out.println("6. 일별 수입 확인하기");
					System.out.println("7. 이번 달 수입 확인하기");
					System.out.println("8. 관리자 모드 종료\n");
					
					try {	// 정수가 아니거나, 정수 범위를 벗어난 입력 try-catch
						managerChoice = scan.nextInt();
					}
					catch (java.util.InputMismatchException e) {
						scan = new Scanner(System.in);	// Scanner 버그를 해결하기 위한 코드
						System.out.println("잘못된 입력입니다.");
						continue;
					}
					
					if (managerChoice == 1)			// 방 추가
					{
						System.out.println("추가할 방 이름을 입력하십시오.");
						
						roomName = scan.next();	// 방 이름 입력 받기
						
						System.out.println("해당 방은 몇 인실 입니까? (1~3)");
						System.out.println("ex) 1인실일 경우: 1 입력");
						
						try {	
							roomType = scan.nextInt();	// 방 타입 입력받기
							
							management.addRoom(roomName, roomType);	// 방 만들기 실행
						}
						catch (java.util.InputMismatchException e) {	// 정수가 아니거나, 정수 범위를 벗어난 입력 catch
							scan = new Scanner(System.in);	// Scanner 버그를 해결하기 위한 코드
							System.out.println("잘못된 입력입니다.");
							continue;
						}
						catch (Exception e)	// 방 추가를 하는 과정에서 발생한 익셉션 처리
						{
							// 각 익셉션 구분, 그에 따른 안내 메시지 출력
							if (e.getMessage().equals("잘못된 방 타입"))
							{
								System.out.println("방의 타입이 올바르지 않습니다.");
							}
							else if (e.getMessage().equals("이미 방이 존재함"))
							{
								System.out.println("해당 방이 이미 존재합니다.");
							}
							else	// 방을 추가할 공간을 못 찾은 경우
							{
								System.out.println("더이상 방을 추가할 수 없습니다.");
							}
							
							continue;
						}
						
						System.out.println("방 추가를 완료하였습니다.");
					}
					else if (managerChoice == 2)	// 방 삭제
					{
						System.out.println("제거할 방 이름을 입력하십시오.");
						
						roomName = scan.next();	// 방 이름 입력 받기
						
						try {
							management.deleteRoom(roomName);	// 방 지우기 실행
						}
						catch (Exception e)	// 방 제거를 하는 과정에서 발생한 익셉션 처리
						{
							System.out.println("제거할 방이 없습니다.");
							continue;
						}
						
						System.out.println("방 제거를 완료하였습니다.");
					}
					else if (managerChoice == 3)	// 빈 방 찾기
					{
						System.out.println("몇 인실인 빈 방을 탐색하시겠습니까?");
						System.out.println("ex) 1인실인 빈 방을 원하는 경우: 1 입력");
						
						try {	
							roomType = scan.nextInt();	// 방 타입 입력 받기
						}
						catch (java.util.InputMismatchException e) {	// 정수가 아니거나, 정수 범위를 벗어난 입력 catch
							scan = new Scanner(System.in);	// Scanner 버그를 해결하기 위한 코드
							System.out.println("잘못된 입력입니다.");
							continue;
						}
						
						try {
							roomList = management.searchRoom(roomType);
						}
						catch (Exception e)		// 빈 방 찾는 과정에서 발생한 익셉션
						{
							if (e.getMessage().equals("잘못된 방 타입"))
							{
								System.out.println("방의 타입이 올바르지 않습니다.");
							}
							else	// 아무 빈 방도 찾지 못한 경우
							{
								System.out.println("현재 남아있는 빈 방이 없습니다.");
							}
							
							continue;
						}
						
						System.out.println("빈 방 탐색 결과입니다.");
						System.out.println(roomList);
					}
					else if (managerChoice == 4)	// 타입별 단가 수정
					{
						try {
							// 배열에 단가 입력(1인실~3인실 순서)
							for (int i = 0; i < price.length; i++)
							{
								System.out.println((i+1) + "인실 단가를 입력하십시오. (가격 숫자만 입력)");
								price[i] = scan.nextInt();
							}
						}
						catch (java.util.InputMismatchException e) {	// 정수가 아니거나, 정수 범위에 벗어난 수를 입력 받았을 경우
							System.out.println("잘못된 입력입니다.");
						}
						
						management.setRoomTypePrice(price);		// 단가 수정
						
						System.out.println("단가 수정을 완료했습니다.");
						
					}
					else if (managerChoice == 5)	// 타입별 단가 확인
					{
						System.out.println("단가 목록입니다.");
						for (int i = 0; i < price.length; i++)
						{
							price[i] = management.getRoomTypePrice()[i];
							System.out.println((i+1) + "인실 시간당 단가: " + price[i] + "원");
						}
					}
					else if (managerChoice == 6)	// 일별(특정 날짜) 수입 확인
					{
						System.out.println("수입을 확인할 날짜를 입력하십시오. (숫자만 입력할 것)");
						System.out.println("ex) 10일의 수입을 확인하고 싶다면 10 입력");
						
						try {	
							date = scan.nextInt();	// 원하는 날짜 입력 받기
						}
						catch (java.util.InputMismatchException e) {	// 정수가 아니거나, 정수 범위를 벗어난 입력 catch
							scan = new Scanner(System.in);	// Scanner 버그를 해결하기 위한 코드
							System.out.println("잘못된 입력입니다.");
							continue;
						}
						
						try {	// 해당 날짜의 수입 출력
							System.out.println(date + "일의 수입은 " + management.getIncome(date) + "원 입니다.");
						}
						catch (Exception e)	// 존재하지 않는 날짜를 입력했을 경우
						{
							System.out.println("잘못된 날짜입니다.");
						}
					}
					else if (managerChoice == 7)	// 이번 달 수입 확인(이번 달, 지금까지의 수입)
					{
						System.out.println("이번 달의 수입은 " + management.getTotalIncome() + "원 입니다.");
					}
					else if (managerChoice == 8)	// 종료
					{
						System.out.println("관리자 모드를 종료합니다.");
						break;		// 현재 while문 빠져나오게 함
					}
					else	// 선택지에 없는 번호를 입력했을 경우
					{		// 위에서 잘못된 수를 입력 받았을 경우의 익셉션 처리를 해 주었으므로,
							// 이는 따로 try-catch문을 쓰지는 않음
						System.out.println("존재하지 않는 선택지 입니다.");
					}
					
				}
			}
			else if (mainChoice == 2)	// 사용자 모드
			{
				while (true)
				{	// 사용자 메뉴 출력
					System.out.println("\n사용자 모드 입니다.");
					System.out.println("원하는 선택지의 숫자를 입력하십시오.");
					System.out.println("1. 체크인");
					System.out.println("2. 체크아웃 및 요금 지불");
					System.out.println("3. 사용자 모드 종료\n");
					
					try {	// 정수가 아니거나, 정수 범위를 벗어난 입력 try-catch
						userChoice = scan.nextInt();	// 사용자 선택지 입력받기
					}
					catch (java.util.InputMismatchException e) {
						scan = new Scanner(System.in);	// Scanner 버그를 해결하기 위한 코드
						System.out.println("잘못된 입력입니다.");
						continue;
					}
					
					if (userChoice == 1)	// 체크인
					{
						// (1) 원하는 타입의 빈 방 목록 보여주기
						System.out.println("몇 인실을 원하십니까?");
						System.out.println("ex) 1인실을 원하는 경우: 1 입력");
						
						try {	
							roomType = scan.nextInt();	// 방 타입 입력 받기
						}
						catch (java.util.InputMismatchException e) {	// 정수가 아니거나, 정수 범위를 벗어난 입력 catch
							scan = new Scanner(System.in);	// Scanner 버그를 해결하기 위한 코드
							System.out.println("잘못된 입력입니다.");
							continue;
						}
						
						try {
							roomList = management.searchRoom(roomType);
						}
						catch (Exception e)		// 빈 방 목록을 불러오는 도중 발생한 익셉션
						{
							if (e.getMessage().equals("잘못된 방 타입"))
							{
								System.out.println("방의 타입이 올바르지 않습니다.");
							}
							else	// 아무 빈 방도 찾지 못한 경우
							{
								System.out.println("현재 남아있는 빈 방이 없습니다.");
							}
							
							continue;
						}
						// 성공적으로 불러온 방 목록 문자열 출력
						System.out.println("현재 체크인 가능한 방 목록 입니다.");
						System.out.println(roomList);
						
						
						// (2) 원하는 방 이름 입력해 체크인 진행(사용자 정보 입력)
						System.out.println("체크인할 방 이름을 입력해주십시오.");
						roomName = scan.next();
						
						System.out.println("사용자의 이름을 적어주십시오.");
						System.out.println("인원이 여러 명이라면, 대표자 한 분의 이름만 적어주십시오.");
						user.setUserName(scan.next());
						
						System.out.println("사용자의 전화번호를 적어주십시오.");
						System.out.println("인원이 여러 명이라면, 대표자 한 분의 전화번호만 적어주십시오.");
						System.out.println("ex) 010-1234-5678");
						user.setUserPhone(scan.next());
						
						try {	// 해당 방을 찾아 그 방에 유저 정보 넣고 체크인하기
							management.getSelectRoom(roomName).checkIn(user);
						}
						catch (Exception e)	// 방 찾기, 체크인 과정에서 발생한 익셉션 처리
						{
							if (e.getMessage().equals("방 찾기 실패"))
							{
								System.out.println("해당 방이 존재하지 않습니다.");
							}
							else if (e.getMessage().equals("체크인 실패"))
							{
								System.out.println("해당 방은 체크인이 불가능합니다.");
							}
							
							continue;
						}
						
						// 체크인 성공 후 안내 메시지(등록 확인)
						System.out.println(roomName + "에 체크인을 완료했습니다.");
						try {
							System.out.println("등록된 이름: " + management.getSelectRoom(roomName).getUser().getUserName());
							System.out.println("등록된 전화번호: " + management.getSelectRoom(roomName).getUser().getUserPhone());
							System.out.println("체크인 시각(날짜): " + management.getSelectRoom(roomName).getStartTimeFormat());
						}
						catch (Exception e)	// 방에 저장된 정보를 불러오는 도중 발생하는 익셉션 처리
						{
							if (e.getMessage().equals("방 찾기 실패"))
							{
								System.out.println("해당 방이 존재하지 않습니다.");
							}
							else	// 그 외 익셉션
							{
								System.out.println("정보를 불러오는데 실패했습니다.");
							}
							
							continue;
						}
					}
					else if (userChoice == 2)	// 체크아웃 및 요금 지불
					{
						System.out.println("체크아웃할 방 이름을 입력해주십시오.");
						roomName = scan.next();
						
						try {	// 지불 메소드를 통해 지불 금액 저장, 체크아웃 처리
							cash = management.getSelectRoom(roomName).pay(management);
						}
						catch (Exception e)	// 체크아웃에 실패했을 경우 익셉션 처리
						{
							if (e.getMessage().equals("방 찾기 실패"))
							{
								System.out.println("해당 방이 존재하지 않습니다.");
							}
							else if (e.getMessage().equals("체크아웃 실패"))
							{
								System.out.println("체크아웃에 실패했습니다.");
							}
							
							continue;
						}
						
						System.out.println(cash + "원을 지불했습니다.");	// 사용자가 지불한 금액 표시
						System.out.println("체크아웃을 완료했습니다. 이용해주셔서 감사합니다.");
					}
					else if (userChoice == 3)	// 사용자 모드 종료
					{
						System.out.println("사용자 모드를 종료합니다.");
						break;		// 현재 while문만 빠져나오게 함
					}
					else	// 선택지에 없는 번호를 입력했을 경우
					{		// 위에서 잘못된 수를 입력 받았을 경우의 익셉션 처리를 해 주었으므로,
							// 이는 따로 try-catch문을 쓰지는 않음
						System.out.println("존재하지 않는 선택지 입니다.");
					}
				}
			}
			else if (mainChoice == 3)	// 메인 메뉴 종료
			{
				try {	// 파일 쓰기(기록, 저장)
					out = new ObjectOutputStream(new FileOutputStream("StudyCafe_Manager.dat"));
					out.writeObject(management);
					
					// 버퍼링되어 아직 기록되지 않은 데이터를 출력 스트림에 모두 기록
					out.flush();
				}
				catch (java.io.IOException ioe) {
					System.out.println("파일로 기록할 수 없습니다.");
				}
				finally {
					try {	// 출력 스트림 닫기
						out.close();
					}
					catch (Exception e) {}
				}
				
				System.out.println("종료합니다.");
				break;
			}
			else	// 선택지에 없는 번호를 입력했을 경우
			{		// 위에서 잘못된 수를 입력 받았을 경우의 익셉션 처리를 해 주었으므로,
					// 이 경우, 따로 try-catch문을 사용하지는 않음
				System.out.println("존재하지 않는 선택지 입니다.");
			}
		}
	}
}