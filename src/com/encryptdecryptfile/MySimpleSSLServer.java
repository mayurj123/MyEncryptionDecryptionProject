package com.encryptdecryptfile;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class MySimpleSSLServer extends Thread {
	SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	SSLServerSocket sslserversocket;

	public MySimpleSSLServer(int port) {
		try {
			sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private void saveFile(SSLSocket sslsocket) throws IOException {

		DataInputStream dis = new DataInputStream(sslsocket.getInputStream());
		FileOutputStream fos = new FileOutputStream("test.txt");
		byte[] buffer = new byte[4096];

		int filesize = 128; // Send file size in separate msg
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			totalRead += read;
			remaining -= read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}

		fos.close();
		dis.close();
	}

	public static void main(String[] arstring) {
		MySimpleSSLServer mySSLServer = new MySimpleSSLServer(1988);
		mySSLServer.start();

	}
}