package util;

public class SignalDetector {
	private SignalDetector(){}
	
	public static String objectIs(String tag) {
		int dot = tag.indexOf('.');
		int sign = tag.indexOf('@');
		return tag.substring(dot + 1, sign);
	}
	
	public static String signalIs(String tag){
		int sign = tag.indexOf('@');
		return tag.substring(0, sign);
	}
	
	public static String chargeMoneyIs(String tag){
		int sign = tag.indexOf('@');
		return tag.substring(sign+1, tag.length());
	}
}
