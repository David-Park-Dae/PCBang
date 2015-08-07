package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import client.Member;

public class Resttimer {
	JLabel jlbResttime;
	JLabel jlbLivetime;
	Member member;
	PLabel pLabel;// 바뀌는 시간을 그릴 라벨
	Timer timer;// util의 Timer Class
	String id; // 타이머를 쓰고 있는 클라이언트 아이디

	// Timer클래스를 생성할 때 넣을 변수들
	// new Timer(TimeTask , delay, period)
	// TimeTask : 할 일을 지정. 안에 run메소드를 override하여 넣는다.
	// delay : 언제시작할지 정해준다. 2000->2초 뒤에 시작
	// period : 어느 간격으로 run을 실행시킬지 지정한다.
	// period = 1000 =>1초마다 수행, resttime을 초단위로 깎음
	// period = 1000*60 => 1분마다 수행, resttime을 분단위로 깎음
	private int delay = 0;
	private int period = 1000;
	private int resttime;

	public Resttimer() {
	}

	// 혹시나몰라서 넣어뒀음. 쓸라면 쓸 시간표기 변수들
	SimpleDateFormat formatter = new SimpleDateFormat("M월 d일 K시 m분", Locale.KOREA);
	String currentTime = formatter.format(new Date());

	public int getResttime() {
		return resttime;
	}

	public void setResttime(int resttime) {
		this.resttime = resttime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// timer 시작 메소드
	public void start(PLabel label) {
		this.pLabel = label;

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println(resttime);
				pLabel.setMessageLine3(transTime(resttime));
				substractTime();
			}
		}, delay, period);
	}

	public void start(Member member, JLabel lbresttime, JLabel lbLivetime) {
		this.jlbResttime = lbresttime;
		this.jlbLivetime = lbLivetime;
		this.resttime = member.getRestTime();

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				//resttime이 0이하이면 db에 0으로 저장시키고 시스템 종료
				if (resttime <= 0) {
					// 1. 남은 시간 저장
					member.setRestTime(0); //맴버의 resttime을 resttimer의 남은시간으로 저장
					member.restTimeSave();
					timer.cancel(); //타이머 시간줄이기 캔슬
					// 2. 종료신호 서버에 전송
					ObjectClient.sendObject(member);
					// 3. 컴퓨터 종료
					System.out.println("컴퓨터종료");
//					ClientExit.exit();
					System.out.println("시스템 종료");
				} else {
					//아니라면, resttime과 livetime을 계속 표기
					System.out.println(resttime);
					jlbResttime.setText(transTime(resttime));
					jlbLivetime.setText(currentTime);
					substractTime();
				}
			}
		}, delay, period);
	}

	// Timer 종료 메소드
	public void cancelTimer() {
		System.out.println("종료 resttime : " + resttime);
		timer.cancel();
	}

	// resttime을 1씩 줄이는 메소드
	private void substractTime() {
		if (resttime > 0) {
			resttime -= 1;
		} else {
			timer.cancel();
		}
	}

	// resttime을 시/분 단위로 리턴해주는 메소드
	private String transTime(int resttime) {
		int hour = resttime / 60;
		int min = resttime % 60;
		String timeMessage;
		if (hour > 0) {
			timeMessage = hour + "시간 " + min + "분";
			return timeMessage;
		} else {
			timeMessage = min + "분";
			return timeMessage;
		}
	}
	
	private void shutdown(){
	}
}