package com.encryptdecryptfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyAESDecrypt {
	public static void main(String[] args) throws Exception {
		MyAESDecrypt aes = new MyAESDecrypt();
		String encryptedDataFile = "D:\\encryptdecryptdata\\encryptedDataFile.txt";
		
		File dataFile = new File(encryptedDataFile);
		FileInputStream fis = new FileInputStream(dataFile);
		
		byte[] dataBytes = new byte[fis.available()];
		fis.read(dataBytes);
		fis.close();
		
		byte[] decryptedData = aes.decrypt(dataBytes);
		String decryptedDataFilePath = "D:\\encryptdecryptdata\\decryptedDataFile.txt";
		FileOutputStream fos = new FileOutputStream(new File(decryptedDataFilePath));
		fos.write(decryptedData);
		fos.close();
	}

	public byte[] decrypt(byte[] text) throws Exception {
		SecretKeySpec spec = getKeySpec();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, spec);
		return cipher.doFinal(text);
	}

	private SecretKeySpec getKeySpec() {
		// TODO Auto-generated method stub
		return null;
	}
}
