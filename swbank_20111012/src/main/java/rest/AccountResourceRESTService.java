package rest;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Account;
import controller.service.AccountService;

@Path("/accounts")
@RequestScoped
public class AccountResourceRESTService {

	@Inject
	private AccountService accountService;
	
	@GET
	@Produces(MediaType.TEXT_XML)
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
	@Produces(MediaType.TEXT_XML)
	public Account lookupAccountByIdReturnAsXML(@PathParam("id") long id) {
		try {
			return accountService.findAccountById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account lookupAccountByIdReturnAsJSON(@PathParam("id") long id) {
		try {
			return accountService.findAccountById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/create/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAccountViaJSON(Account account) {
		try {
			accountService.createAccount(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = account + " has been created";
		return Response.status(200).entity(result).build();
	}
	
	@POST
	@Path("/create/xml")
	@Consumes(MediaType.TEXT_XML)
	public Response createAccountViaXML(Account account) {
		try {
			accountService.createAccount(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = account + " has been created";
		return Response.status(200).entity(result).build();
	}
	
	@PUT
	@Path("/{bankCode}/{accountNumber}/debit")
	public Response debitMoney(@PathParam("bankCode") String bankCode, @PathParam("accountNumber") String accountNumber, @FormParam("amount") String amount) {
		try {
			Account account = accountService.findAccountByBankCodeAndAccountNumber(bankCode, accountNumber);
			account.debit(new BigDecimal(amount));
			accountService.updateAccount(account);
			String result = "From " + account + " has been debited " + amount + " €";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PUT
	@Path("/{bankCode}/{accountNumber}/add")
	public Response addMoney(@PathParam("bankCode") String bankCode, @PathParam("accountNumber") String accountNumber, @FormParam("amount") String amount) {
		try {
			Account account = accountService.findAccountByBankCodeAndAccountNumber(bankCode, accountNumber);
			account.add(new BigDecimal(amount));
			accountService.updateAccount(account);
			String result = "To " + account + " has been added " + amount + " €";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
