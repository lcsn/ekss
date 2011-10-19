package controller.handler;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import model.Account;
import model.Transaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import controller.service.GenericService;

@Named("bankingHandler")
@SessionScoped
@Stateful
public class BankingHandler extends GenericService {

	@Inject
	@Category("bankinghandler")
	private Logger log;

	@Produces
	@Named
	public List<Transaction> getTransactions() {
		return null;
	}

	@Produces
	@Named
	public List<Account> getAccounts() {
		return null;
	}
	
}
