import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;

import test.secure.SecureIOStream;

public class SecureIOStreamTest {
	@Test
	public void testSecureIOStream() throws IOException {
		Random random = new Random();
		byte[] originalArray = new byte[8];
		random.nextBytes(originalArray);

		SecureIOStream secureIOStream = new SecureIOStream(null, null);

		// Send bytes
		for (byte b : originalArray) {
			secureIOStream.send(b);
		}

		byte[] receivedArray = new byte[8];

		// Receive bytes
		for (int i = 0; i < 8; i++) {
			receivedArray[i] = (byte) secureIOStream.receive();
		}

		// Assert equality
		assertArrayEquals(originalArray, receivedArray);
	}
}
