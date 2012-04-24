package util.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jboss.logging.Logger;

import sun.misc.BASE64Encoder;

public class PasswordService {

	private static Logger log = Logger.getLogger(PasswordService.class);
	
	public static synchronized String encrypt(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(src.getBytes("UTF-8"));
			byte[] raw = md5.digest();
			String hash = new BASE64Encoder().encode(raw);
			return hash;
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return "-1";
	}
	
//	public static void main(String[] args) {
//		System.out.print(PasswordService.getInstance().encrypt("4711"));
//	}
	
}
