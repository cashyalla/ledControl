package com.cashyalla.home.led.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 암호화 클래스
 */
public class SHA256 {

	private static final String salt = "h+ld[I|^6mXCsdY(";
	
    public static String hashString(String str) throws Exception {
        String hash;

        try {
            byte[] b = hashByte(1000, str, salt.getBytes());

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
            }

            hash = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new Exception("암호화 작업중 에러가 발생하였습니다.");
        }

        return hash;
    }

    public static byte[] hashByte(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = null;
        byte[] hash = null;

        try {
            digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(salt);
            hash = digest.digest(password.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < iterationNb; i++) {
            digest.reset();
            hash = digest.digest(hash);
        }

        return hash;
    }

}