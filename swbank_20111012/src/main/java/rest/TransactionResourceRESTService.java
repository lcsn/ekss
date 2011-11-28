package rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Transaction;
import controller.service.TransactionService;

@Path("/transactions")
@RequestScoped
public class TransactionResourceRESTService {

	@Inject
	private TransactionService transactionService;
	
	@GET
	@Path("/{userId}/{timestamp}")
	@Produces(MediaType.TEXT_XML)
	public Transaction lookupTransactionByIdReturnAsXML(@PathParam("userId") Long userId, @PathParam("timestamp") String timestamp) {
		try {
			return transactionService.findTransactionByTransactionNumber(userId+"/"+timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/{userId}/{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public Transaction lookupTransactionByIdReturnAsJSON(@PathParam("userId") Long userId, @PathParam("timestamp") String timestamp) {
		try {
			return transactionService.findTransactionByTransactionNumber(userId+"/"+timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/create/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTransactionViaJSON(Transaction transaction) {
		try {
//			The id is 0 and thats some to-json converting issue.
			transaction.setId(null);
			transactionService.createTransaction(transaction);
			// process transaction here
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = transaction + " has been created";
		return Response.status(200).entity(result).build();
	}
	
	@POST
	@Path("/create/xml")
	@Consumes(MediaType.TEXT_XML)
	public Response createTransactionViaXML(Transaction transaction) {
		try {
			transactionService.createTransaction(transaction);
			// process transaction here
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = transaction + " has been created";
		return Response.status(200).entity(result).build();
	}
	
}
