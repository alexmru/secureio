package test.secure;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SecureIOStream secureIOStream = new SecureIOStream(null, null);
		secureIOStream.send(3);
		System.out.println(secureIOStream.receive());
	}

}
