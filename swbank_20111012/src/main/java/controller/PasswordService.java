package controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import sun.misc.BASE64Encoder;

@Singleton
@Startup
public class PasswordService {

	private static PasswordService instance;
	
	public synchronized String encrypt(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(src.getBytes("UTF-8"));
			byte[] raw = md5.digest();
			String hash = new BASE64Encoder().encode(raw);
			return hash;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "-1";
	}
	
	public static PasswordService getInstance() {
		if(instance == null) {
			instance = new PasswordService();
		}
		return instance;
	}
	
}
