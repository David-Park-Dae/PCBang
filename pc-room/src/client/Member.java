package client;

import java.io.Serializable;

import util.ClientExit;
import util.SqlUtil;

public class Member implements Serializable {

	private int restTime;

	String id;
	String name;
	String seatNumber;

	public Member(String seatNumber, String id, String name, int restTime) {
		this.id = id;
		this.name = name;
		this.seatNumber = seatNumber;
		this.setRestTime(restTime);
	}

	public String getId() {
		return id;
	}
	
	public String getName(){
		return name;
	}

	public int getRestTime() {
		return restTime;
	}

	public void setRestTime(int restTime) {
		this.restTime = restTime;
	}
	
	public String getSeatNumber(){
		return seatNumber;
	}

	public void restTimeSave() {
		SqlUtil.restTimeSave(this);
	}

}