package client;

import util.ClientExit;
import util.SqlUtil;

public class ConnectMember {
	
	private long restTime;
	
	private String 		 id;
	private String 		 username;
	private String 		 seatNumber;
	
	
	public ConnectMember(String seatNumber, String id, String username, long restTime) {
		this.id = 			id;
		this.username = 	username;
		this.seatNumber = 	seatNumber;
		this.setRestTime(restTime);
	}
	public String getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
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
	
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	
}