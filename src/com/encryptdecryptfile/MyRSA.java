package com.encryptdecryptfile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MyRSA {
	// private Socket s;
	SSLSocket sslsocket;
	SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

	public MyRSA(String host, int port, String encryptedDataFilePath) {
		try {
			// s = new Socket(host, port);
			sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", port);
			sendFile(encryptedDataFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendFile(String file) throws IOException {
		DataOutputStream dos = new DataOutputStream(sslsocket.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];

		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}

		fis.close();
		dos.close();
	}

	public MyRSA() {
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		MyRSA rsa = new MyRSA();
		String aesKeyFilePath = "D:\\encryptdecryptdata\\fileToBeEncrypted.txt";
		File f = new File(aesKeyFilePath);

		byte[] bytes = new byte[(int) f.length()];
		new FileInputStream(f).read(bytes);
		byte[] encryptedData = rsa.encrypt(bytes);

		String encryptedDataFilePath = "D:\\encryptdecryptdata\\encryptedDataFile.txt";
		FileOutputStream fos = new FileOutputStream(new File(encryptedDataFilePath));
		fos.write(encryptedData);
		fos.close();

		new MyRSA("localhost", 1988, encryptedDataFilePath);
	}

	public byte[] encrypt(byte[] data) throws Exception {
		Cipher cipher;
		byte[] decryptedData = null;
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey());
		decryptedData = cipher.doFinal(data);
		return decryptedData;
	}

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
}
