package controller.service;

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

@Singleton
@ApplicationScoped
@Startup
public class BankInformationService {

	@Inject
	@Category("bankinformationservice")
	private Logger log;
	
	private static BankInformationService instance;
	
	private String bankName = "No BankName";
	private String bankCode = "No BankCode";
	
	@PostConstruct
	private void loadBankInformation() {
		log.info("loading bankinformations");
//		Properties infs = 
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
