package server;

import java.io.Serializable;

public class Member implements Serializable {
	private String seatNum;
	private String id;
	private String name;
	private int resttime;

	public Member(String seatNum, String id, String name, int resttime) {
		this.seatNum = seatNum;
		this.id = id;
		this.name = name;
		this.resttime = resttime;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResttime() {
		return resttime;
	}

	public void setResttime(int resttime) {
		this.resttime = resttime;
	}
}
