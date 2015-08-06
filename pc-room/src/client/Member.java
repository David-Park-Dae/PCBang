package client;

import util.ClientExit;
import util.SqlUtil;

public class Member {
	
	private long restTime;
	
	String id;
	String name;
	String seatNumber;
	
	
	public Member(String seatNumber, String id, String name, long restTime) {
		this.id = id;
		this.name = name;
		this.seatNumber = seatNumber;
		this.setRestTime(restTime);
	}
	public String getId() {
		return id;
	}
	
	public long getRestTime() {
		return restTime;
	}

	public void setRestTime(long restTime) {
		this.restTime = restTime;
	}
	
	public void restTimeSave() {
		SqlUtil.restTimeSave(this);
		ClientExit.exit();
	}
	
}