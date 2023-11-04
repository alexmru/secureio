package test.secure;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOStream {

	protected BufferedInputStream bIn;
	protected BufferedOutputStream bOut;

	public IOStream(BufferedInputStream bIn, BufferedOutputStream bOut) {
		String filePath = "test.data"; // Replace with the actual file path
		FileInputStream fileInputStream;
		FileOutputStream fileOutputStream;
		try {
			fileInputStream = new FileInputStream(filePath);
			fileOutputStream = new FileOutputStream(filePath);
			this.bIn = new BufferedInputStream(fileInputStream);
			this.bOut = new BufferedOutputStream(fileOutputStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void send(int value) throws IOException {
	}

	public int receive() {
		return -1;
	}
}
