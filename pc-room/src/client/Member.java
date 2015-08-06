package client;

import java.util.Date;

public class Member {
	
	private long restTime;
	
	String id;
	String name;
	int seatNumber;
	
	
	public Member(String id, String name, long restTime) {
		this.id = id;
		this.name = name;
		this.restTime = restTime;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public long getRestTime() {
		return restTime;
	}

	public void setRestTime(long restTime) {
		this.restTime = restTime;
	}
	
	
}