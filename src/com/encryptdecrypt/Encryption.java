package com.encryptdecrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

class Encryption {

	public static void main(String[] args) {

		String dataToBeEncrypted;
		Scanner sc = new Scanner(System.in);

		for (int i = 0; i < 10; i++) {

			try {
				KeyGenerator keyGen = KeyGenerator.getInstance("DES");
				SecretKey secretKey = keyGen.generateKey();

				Cipher desCipher;
				desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

				System.out.println("Enter Text TO BE Encrypted ");
				dataToBeEncrypted = sc.next();

				byte[] textToBeEncryptedInByte = dataToBeEncrypted.getBytes();

				// Initialize cipher for encryption
				desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

				byte[] textEncrypted = desCipher.doFinal(textToBeEncryptedInByte);
				System.out.println("Encrypted Text Is : " + new String(textEncrypted));

				// Now Initialize cipher for Decryption
				desCipher.init(Cipher.DECRYPT_MODE, secretKey);
				byte[] textDecrypted = desCipher.doFinal(textEncrypted);

				System.out.println("Text Decryted : " + new String(textDecrypted));

			} catch (NoSuchAlgorithmException e) {
				System.out.println("Exception " + e);
			} catch (NoSuchPaddingException e) {
				System.out.println("Exception " + e);
			} catch (InvalidKeyException e) {
				System.out.println("Exception " + e);
			} catch (IllegalBlockSizeException e) {
				System.out.println("Exception " + e);
			} catch (BadPaddingException e) {
				System.out.println("Exception " + e);
			}
		}
	}

}
