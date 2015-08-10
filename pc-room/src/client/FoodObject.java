package client;

import java.io.Serializable;

public class FoodObject implements Serializable {
	private String seatNumber;
	private Object[][] foodInfo;

	public FoodObject(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public void setFoodInfo(Object[][] foodInfo) {
		this.foodInfo = foodInfo;
	}

	public Object[][] getFoodInfo() {
		return foodInfo;
	}

	public String getSeatNumber() {
		return seatNumber;
	}
}
