package service;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import controller.PasswordService;

public class PasswordServiceTest extends TestCase {

	private PasswordService service;
	
	@Before
	public void setUp() {
		this.service = PasswordService.getInstance();
	}
	
	@Test
	public void testPasswordService() {
		
		int hash = service.hashCode();
		service = PasswordService.getInstance();
		assertTrue(hash == service.hashCode());
		
		String src = service.encrypt("4711");
		assertEquals(src, "tSNAtN5FZrgEyYgKoLSvXw==");
		assertFalse(src.equals("tSNAtN5FZrgEyYgKoLSvXw==1337"));
	}

}
