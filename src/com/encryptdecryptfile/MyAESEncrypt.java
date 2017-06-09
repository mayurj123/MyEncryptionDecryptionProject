package com.encryptdecryptfile;

import java.io.File;
import java.io.FileInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyAESEncrypt {
	public static void main(String[] args) throws Exception {
		MyAESEncrypt aes = new MyAESEncrypt();
		String dataFilePath = "D:\\data\\dataFile.txt";
		File dataFile = new File(dataFilePath);
		FileInputStream fis = new FileInputStream(dataFile);
		byte[] dataBytes = new byte[fis.available()];
		fis.read(dataBytes);
		fis.close();
		byte[] encryptedData = aes.encrypt(dataBytes);
	}

	public byte[] encrypt(byte[] text) throws Exception {
		SecretKeySpec spec = getKeySpec();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		return cipher.doFinal(text);
	}

	private SecretKeySpec getKeySpec() {
		// TODO Auto-generated method stub
		return null;
	}
}
