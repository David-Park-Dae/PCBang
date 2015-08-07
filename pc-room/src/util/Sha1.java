package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1 {
	public static String setSha1(String s) {
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++)
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
}
