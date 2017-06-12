package com.encryptdecryptfile;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MySimpleSSLClient {

	SSLSocket sslsocket;
	SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

	public MySimpleSSLClient(String host, int port, String encryptedDataFilePath) {
		try {
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

	public static void main(String[] args) {
		new MySimpleSSLClient("localhost", 1988, "cat.jpg");
	}
}