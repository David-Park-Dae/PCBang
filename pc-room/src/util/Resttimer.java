package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Resttimer{
	//바뀌는 시간을 그릴 라벨
	PLabel label;
	//util의 Timer Class
	Timer timer;
	
	//혹시나몰라서 넣어뒀음. 쓸라면 쓸 시간표기 변수들
	SimpleDateFormat formatter = new SimpleDateFormat("HH분 mm시 ss초", Locale.KOREA);
	Date currentTime = new Date();
	String dTime = formatter.format(currentTime);
	
	
	//Timer클래스를 생성할 때  넣을 변수들
	//new Timer(TimeTask , delay, period)
	//TimeTask : 할 일을 지정. 안에 run메소드를 override하여 넣는다.
	//delay : 언제시작할지 정해준다. 2000->2초 뒤에 시작
	//period : 어느 간격으로 run을 실행시킬지 지정한다.
	//period = 1000 =>1초마다 수행, resttime을 초단위로 깎음
	//period = 1000*60 => 1분마다 수행, resttime을 분단위로 깎음
	private int delay=0;
	private int period=1000;
	private int resttime;

	public int getResttime() {
		return resttime;
	}

	public void setResttime(int resttime) {
		this.resttime = resttime;
	}

	
	//timer 시작 메소드
	public void start(PLabel label) {
		this.label = label;
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println(resttime);
				label.setMessageLine3(transTime(resttime));
				substractTime();
			}
		}, delay, period);
	}
	
	//Timer 종료 메소드
	public void cancelTimer(){
		System.out.println("종료 resttime : "+resttime);
		timer.cancel();
	}
	
	//resttime을 1씩 줄이는 메소드
	private void substractTime(){
		resttime -= 1;
	}
	
	//resttime을 시/분 단위로 리턴해주는 메소드
	private String transTime(int resttime){
		int hour = resttime/60;
		int min = resttime%60;
		String timeMessage;
		if(hour>0){
			timeMessage = hour + "시간 " + min +"분";
			return timeMessage;
		}else{
			timeMessage = min +"분";
			return timeMessage;
		}
	}
}