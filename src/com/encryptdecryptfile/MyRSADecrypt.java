package com.encryptdecryptfile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class MyRSADecrypt extends Thread {
	SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	SSLServerSocket sslserversocket;

	public MyRSADecrypt(int port) {
		try {
			sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MyRSADecrypt() {
	}

	public void run() {
		while (true) {
			try {
				SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
				saveFile(sslsocket);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	@SuppressWarnings("resource")
	private void saveFile(SSLSocket clientSock) throws IOException {

		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
		System.out.println("hi");

		String aesKeyFilePath = "D:\\encryptdecryptdata\\encryptedDataFile.txt";
		File f = new File(aesKeyFilePath);
		MyRSADecrypt rsa = new MyRSADecrypt();
		byte[] bytes = new byte[(int) f.length()];
		new FileInputStream(f).read(bytes);
		byte[] decryptedData = null;
		try {
			decryptedData = rsa.decrypt(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String decryptedDataFilePath = "D:\\encryptdecryptdata\\decryptedDataFile.txt";
		FileOutputStream fos = new FileOutputStream(new File(decryptedDataFilePath));
		fos.write(decryptedData);
		fos.close();
		dis.close();
	}

	public static void main(String[] args) throws Exception {
		MyRSADecrypt rsa = new MyRSADecrypt(1988);
		rsa.start();

		/*
		 * String aesKeyFilePath = "test.txt"; File f = new
		 * File(aesKeyFilePath);
		 * 
		 * 
		 * byte[] bytes = new byte[(int) f.length()]; new
		 * FileInputStream(f).read(bytes); byte[] decryptedData =
		 * rsa.decrypt(bytes);
		 * 
		 * String decryptedDataFilePath =
		 * "D:\\encryptdecryptdata\\decryptedDataFile.txt"; FileOutputStream fos
		 * = new FileOutputStream(new File(decryptedDataFilePath));
		 * fos.write(decryptedData); fos.close();
		 */
	}

	public byte[] decrypt(byte[] data) throws Exception {
		Cipher cipher;
		byte[] decryptedData = null;
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey());
		decryptedData = cipher.doFinal(data);
		System.out.println("Private Key Generated " + new String(decryptedData));
		return decryptedData;
	}

	private PrivateKey loadPrivateKey() throws Exception {
		String privateKeyFilePath = "D://encryptdecryptdata//privateKey.txt";
		File filePrivateKey = new File(privateKeyFilePath);
		FileInputStream fis = new FileInputStream(filePrivateKey);
		byte[] encodedPrivateKey = new byte[fis.available()];
		fis.read(encodedPrivateKey);
		// System.out.println("bytes are " + new String(encodedPrivateKey));
		fis.close();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		System.out.println("Private Key Generated");
		return privateKey;
	}
}
