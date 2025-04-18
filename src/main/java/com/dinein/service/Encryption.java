package com.dinein.service;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

	 
	public static String encrypt(String data) throws Exception {
		
		String key = "1234567890123456";
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] encryptedBytes = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	// Method to decrypt data using a custom secret key
	public static String decrypt(String encryptedData) throws Exception {
		
		String key = "1234567890123456";
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
		byte[] decryptedBytes = cipher.doFinal(decodedBytes);

		return new String(decryptedBytes);
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println(Encryption.encrypt("pass"));
//	}
}
