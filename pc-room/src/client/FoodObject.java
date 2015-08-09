package client;

public class FoodObject {
	private String seatNumber;
	private Object[][] foodInfo;

	public FoodObject(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public void setFoodInfo(Object[][] foodInfo) {
		for(int i=0; i<foodInfo.length; i++) {
			System.out.print("주문 할 음식 : ");
			for(int j=0; j<foodInfo[i].length; j++) {
				System.out.print(foodInfo[i][j]+" ");
			}
			System.out.println();
		}
		
		this.foodInfo = foodInfo;
	}
	
	
	
}
