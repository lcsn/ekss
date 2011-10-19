package controller.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

	@Inject
	@Category("bankinformationservice")
	private Logger log;
	
	private static BankInformationService instance;
	
	private String bankName = "S & W Bank";
	private String bankCode = "200 100 20";
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void loadBankInformation() {
//		log.info("loading bankinformations");
		Properties defaultProps = new Properties();
		defaultProps.put(BankConstants.BANK_NAME_KEY, bankName);
		defaultProps.put(BankConstants.BANK_CODE_KEY, bankCode);
		Properties infs = new Properties(defaultProps);
		try {
			infs.load(new FileReader(new File(/*"C:/bank.properties")));*/new URI("file:///resources/bank.properties"))));
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} 
		catch (URISyntaxException e) {
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
	
	public static BankInformationService getInstance() {
		if(instance == null) {
			instance = new BankInformationService();
		}
		return instance;
	}
	
}
