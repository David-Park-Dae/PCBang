package util;

public class SignalDetector {
	private SignalDetector(){}
	
	public static String signalIs(String tag) {
		int dot = tag.indexOf('.');
		int sign = tag.indexOf('@');
		return tag.substring(dot + 1, sign);
	}
}
