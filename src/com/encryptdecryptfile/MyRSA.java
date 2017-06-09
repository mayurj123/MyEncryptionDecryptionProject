package com.encryptdecryptfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class MyRSA {
	public static void main(String[] args) throws Exception {
		MyRSA rsa = new MyRSA();
		String aesKeyFilePath = "D:\\encryptdecryptdata\\fileToBeEncrypted1.txt";
		File f = new File(aesKeyFilePath);

		byte[] bytes = new byte[(int) f.length()];
		new FileInputStream(f).read(bytes);
		byte[] encryptedData = rsa.encrypt(bytes);

		String encryptedDataFilePath = "D:\\encryptdecryptdata\\encryptedDataFile.txt";
		FileOutputStream fos = new FileOutputStream(new File(encryptedDataFilePath));
		fos.write(encryptedData);
		fos.close();
/*
		String aesKeyFilePath1 = "D:\\encryptdecryptdata\\encryptedDataFile.txt";
		File f1 = new File(aesKeyFilePath);

		byte[] bytes1 = new byte[(int) f1.length()];
		new FileInputStream(f).read(bytes1);
		byte[] decryptedData = rsa.decrypt(bytes1);
		System.out.println("hiii");

		String decryptedDataFilePath = "D:\\encryptdecryptdata\\decryptedDataFile.txt";
		FileOutputStream fos2 = new FileOutputStream(new File(decryptedDataFilePath));
		fos2.write(decryptedData);
		fos2.close();*/

	}

	public byte[] encrypt(byte[] data) throws Exception {
		Cipher cipher;
		byte[] decryptedData = null;
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey());
		decryptedData = cipher.doFinal(data);
		return decryptedData;
	}
	/*public byte[] decrypt(byte[] data) throws Exception {
		Cipher cipher;
		byte[] decryptedData = null;
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey());
		decryptedData = cipher.doFinal(data);
		System.out.println("Private Key Generated " + new String(decryptedData));
		return decryptedData;
	}*/

	private PublicKey loadPublicKey() throws Exception {
		String publicKeyFilePath = "D://encryptdecryptdata//publicKey.txt";
		File filePublicKey = new File(publicKeyFilePath);
		FileInputStream fis = new FileInputStream(filePublicKey);
		byte[] encodedPublicKey = new byte[fis.available()];
		fis.read(encodedPublicKey);
		fis.close();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		return publicKey;
	}

	

	/*private PrivateKey loadPrivateKey() throws Exception {
		String privateKeyFilePath = "D://encryptdecryptdata//privateKey.txt";
		File filePrivateKey = new File(privateKeyFilePath);
		FileInputStream fis = new FileInputStream(filePrivateKey);
		byte[] encodedPrivateKey = new byte[fis.available()];
		fis.read(encodedPrivateKey);
		fis.close();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		return privateKey;
	}*/
}
