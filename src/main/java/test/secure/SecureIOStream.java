package test.secure;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class SecureIOStream extends IOStream {
	private String key = "asdfasdf";
	byte[] ivBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	IvParameterSpec iv = new IvParameterSpec(ivBytes);

	public SecureIOStream(BufferedInputStream bIn, BufferedOutputStream bOut) // (, PublicKey publicKey, PrivateKey
																				// privateKey)
	{
		// TODO Auto-generated constructor stub
		super(bIn, bOut);
//		this.publicKey = publicKey;
//		this.privateKey = privateKey;
	}

	@Override
	public void send(int value) throws IOException {
		try {
			byte[] keyBytes = key.getBytes("UTF8");

			// Create a DES key specification
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);

			// Create a SecretKeyFactory for DES
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			// Generate a SecretKey object from the key specification
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

			// Create a DES cipher instance in CTR mode with NoPadding
			Cipher desCipher = Cipher.getInstance("DES/CTR/NoPadding");

			// Initialize the cipher with the key and IV
			desCipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

			// Convert the integer value to bytes
			byte[] valueBytes = new byte[4];
			valueBytes[0] = (byte) (value >>> 24);
			valueBytes[1] = (byte) (value >>> 16);
			valueBytes[2] = (byte) (value >>> 8);
			valueBytes[3] = (byte) value;

			// Encrypt the value
			byte[] encryptedData = desCipher.doFinal(valueBytes);

			// Send the encrypted data to the output stream
			bOut.write(encryptedData);
			bOut.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int receive() {
		try {
			byte[] keyBytes = key.getBytes("UTF8");

			// Create a DES key specification
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);

			// Create a SecretKeyFactory for DES
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			// Generate a SecretKey object from the key specification
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

			// Create a DES cipher instance in CTR mode with NoPadding
			Cipher desCipher = Cipher.getInstance("DES/CTR/NoPadding");

			// Initialize the cipher with the key and IV
			desCipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			// Read the encrypted data from the input stream
			byte[] encryptedData = new byte[4];
			int bytesRead = bIn.read(encryptedData);

			if (bytesRead != 4) {
				System.err.println("Received data is not enough to decrypt.");
				return -1; // Or some other error handling mechanism
			}

			// Decrypt the data
			byte[] decryptedData = desCipher.doFinal(encryptedData);

			// Convert the decrypted bytes to an integer
			int decryptedValue = ((decryptedData[0] & 0xFF) << 24) | ((decryptedData[1] & 0xFF) << 16)
					| ((decryptedData[2] & 0xFF) << 8) | (decryptedData[3] & 0xFF);

			return decryptedValue;
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // Or some other error handling mechanism
		}
	}

}
