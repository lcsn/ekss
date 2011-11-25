package controller.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import model.BankInformation;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.BankConstants;

@Singleton
@ApplicationScoped
@Startup
public class BankInformationService extends GenericService {

	private static final int ACCOUNTNUMBER_SIZE = 9;

	@Inject
	@Category("bankinformationservice")
	private Logger log;

	public static int accountCounter = 1;
	
	private static BankInformationService instance;
	
	private String bankName = "No Name";
	private String bankCode = "11235813";
	
//	@Asynchronous
//	@Schedule(second="*/5", minute="*",hour="*", persistent=false)
//	private void sayHello() {
//		System.out.println("server says hello.");
//	}
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void loadBankInformation() {
		log.info("loading bankinformations");
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
		
		BankInformation bi = findInformationByName("maxAccountNumber");
		
		accountCounter = Integer.valueOf(bi.getValue());
		log.info("Current max. AccountNumber: " + accountCounter);
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
	
	public BankInformation findInformationById(Long id) {
		log.info("findInformationById");
		Query q = em.createNamedQuery(BankInformation.FIND_INFORMATION_BY_ID);
		q.setParameter("id", id);
		return (BankInformation) q.getSingleResult();
	}
	
	public BankInformation findInformationByName(String name) {
		log.info("findInformationByName");
		Query q = em.createNamedQuery(BankInformation.FIND_INFORMATION_BY_NAME);
		q.setParameter("name", name);
		return (BankInformation) q.getSingleResult();
	}
	
	public BankInformation createBankInformation(BankInformation bi) {
		log.info("createBankInformation");
		em.persist(bi);
		em.flush();
		return em.find(BankInformation.class, bi.getId());
	}
	
	public BankInformation updateBankInformation(BankInformation bi) {
		log.info("updateBankInformation");
		if(bi.getId() == null) {
			log.warn("Information is not persistent.");
		}
		em.merge(bi);
		return em.find(BankInformation.class, bi.getId());
	}
	
	public static BankInformationService getInstance() {
		if(instance == null) {
			instance = new BankInformationService();
		}
		return instance;
	}

}
