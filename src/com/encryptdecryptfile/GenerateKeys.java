package com.encryptdecryptfile;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class GenerateKeys {
	public static void main(String[] args) {
		GenerateKeys generateKeys = new GenerateKeys();
		String filePath="D://keys.txt";
		String publicFilePath="D://encryptdecryptdata//Publickey.txt";
		String privateFilePath="D://encryptdecryptdata//privatekey.txt";
		try {
			generateKeys.generateAESKey(filePath);
			
			generateKeys.generateKeyPair(publicFilePath, privateFilePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateAESKey(String filePath) throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256);
		SecretKey skey = keyGen.generateKey();
		byte[] raw = skey.getEncoded();
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(raw);
		fos.close();
	}
	public void generateKeyPair(String publicFilePath, String privateFilePath) throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGen.initialize(1024, random);
		KeyPair generatedKeyPair = keyGen.genKeyPair();
		savePublicKey(generatedKeyPair.getPublic(), publicFilePath);
		savePrivateKey(generatedKeyPair.getPrivate(), privateFilePath);
	}

	public void savePublicKey(PublicKey key, String filePath) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key.getEncoded());
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
		System.out.println("public key genreated");
	}

	private void savePrivateKey(PrivateKey key, String filePath) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key.getEncoded());
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(pkcs8EncodedKeySpec.getEncoded());
		fos.close();
		System.out.println("PRAIVATE key genreated");
	}
}
