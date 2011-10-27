package controller.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.BankConstants;

@Singleton
@ApplicationScoped
@Startup
public class BankInformationService {

	private static final int ACCOUNTNUMBER_SIZE = 9;

	@Inject
	@Category("bankinformationservice")
	private Logger log;
	
	private static int accountCounter = 1;
	
	private static BankInformationService instance;
	
	private String bankName = "No Name";
	private String bankCode = "11235813";
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void loadBankInformation() {
//		log.info("loading bankinformations");
		Properties defaultProps = new Properties();
		defaultProps.put(BankConstants.BANK_NAME_KEY, bankName);
		defaultProps.put(BankConstants.BANK_CODE_KEY, bankCode);
		Properties infs = new Properties(defaultProps);
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("bank.properties");
			infs.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} 
		this.bankName = infs.getProperty(BankConstants.BANK_NAME_KEY);
		this.bankCode = infs.getProperty(BankConstants.BANK_CODE_KEY);
		log.info("Bank: " + bankName);
		log.info("BLZ: " + bankCode);
	}

	@Named
	@Produces
	public String getBankName() {
		return bankName;
	}
	
	@Named
	@Produces
	public String getBankCode() {
		return bankCode;
	}
	
	@Named
	@Produces
	public String getNewAccountNumber() {
		log.info("getNewAccountNumber");
//		accountCounter++;
		String accNo = Integer.toString(accountCounter);
		for (int i = ACCOUNTNUMBER_SIZE - accNo.length(); i > 0; i--) {
			accNo = '0' + accNo;
		}
		
		return accNo;
	}
	
	public void incrementAccountCounter() {
		accountCounter++;
	}
	
	public static BankInformationService getInstance() {
		if(instance == null) {
			instance = new BankInformationService();
		}
		return instance;
	}
	
}
