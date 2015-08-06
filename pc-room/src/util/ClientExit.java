package util;

import java.io.IOException;
import java.io.OutputStream;

public class ClientExit {
	public static void exit() {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
			OutputStream os = process.getOutputStream();
			os.write("shutdown -s -f \n\r".getBytes());
			os.close();
			process.waitFor();
		} catch (IOException e) {
			System.err.println("runtimeExec IOException: " + e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
