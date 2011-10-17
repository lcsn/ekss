package controller.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import sun.misc.BASE64Encoder;

@Singleton
@Startup
public class PasswordService {

	@Inject
	@Category("swbank_20111012")
	private Logger log;
	
	private static PasswordService instance;
	
	public synchronized String encrypt(String src) {
		try {
//			log.trace("encrypting..");
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
	
	public static void main(String[] args) {
		
		System.out.print(PasswordService.getInstance().encrypt("4711"));
		
	}
	
}
