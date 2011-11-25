package rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Account;
import model.SmartAccount;
import controller.service.AccountService;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members
 * table.
 */
@Path("/accounts")
@RequestScoped
public class AccountResourceRESTService {

	@Inject
	private AccountService accountService;
	
	@GET
	@Produces("text/xml")
	public List<Account> listAllSmartAccounts() {
		try {
			return accountService.findAllAccounts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Account lookupAccountById(@PathParam("id") long id) {
		try {
			return accountService.findAccountById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/{bankCode:[0-9][0-9]*/accountNumber:[0-9][0-9]*}")
	@Produces("text/xml")
	public Account lookupAccountByBankCodeAndAccountNumber(@PathParam("bankCode") String bankCode, @PathParam("accountNumber") String accountNumber) {
		try {
			return accountService.findAccountByAccountNumberAndBankcode(accountNumber, bankCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
