package rest.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.Transaction;
import model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class JaxRsClient {

	private String resourceURL;

	public JaxRsClient(String resourceURL) {
		this.resourceURL = resourceURL;
	}

	private void doGet(String id, String mediaType) {
		try {
			ClientRequest request = new ClientRequest(resourceURL + id);
			request.accept(mediaType);

			ClientResponse<String> response;
			response = request.get(String.class);
			handleResponse(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doPostTransactionCreateViaXML(Transaction transaction) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(Transaction.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(transaction, sw);
            String result = sw.toString();
            ClientRequest request = new ClientRequest(resourceURL + "create/xml");
			request.body(MediaType.TEXT_XML, result);
			ClientResponse<String> response = request.post(String.class);
			handleResponse(response);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doPostTransactionCreateViaJSON(Transaction transaction) {
		try {
			JSONObject json = JSONObject.fromObject(transaction);
			ClientRequest request = new ClientRequest(resourceURL + "create/json");
			request.body(MediaType.APPLICATION_JSON, json.toString());
			ClientResponse<String> response = request.post(String.class);
			handleResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doPostAccountCreate() {
	}

	private void doPutAccountDebit(String bankCode, String accountNumber, Object amount) {
		try {
			ClientRequest request = new ClientRequest(resourceURL + bankCode + "/" + accountNumber + "/debit");
			request.formParameter("amount", amount);
			ClientResponse<String> response = request.put(String.class);
			handleResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doPutAccountAdd(String bankCode, String accountNumber, Object amount) {
		try {
			ClientRequest request = new ClientRequest(resourceURL + bankCode + "/" + accountNumber + "/add");
			request.formParameter("amount", amount);
			ClientResponse<String> response = request.put(String.class);
			handleResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleResponse(ClientResponse<String> response) throws IOException {
		// HTTP-Code 200 means everything is fine!
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

		String output;
		System.out.println("Output from Server ....");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		System.out.println();
	}

	public static void main(String[] args) {
			
			System.out.println("#####ACCOUNTS#####");
			JaxRsClient client = new JaxRsClient("http://localhost:8080/swbank_20111012/rest/accounts/");
			
			client.doGet("1", MediaType.TEXT_XML);

//			Might be LazyInitializationException
//			client.doGet("1", MediaType.APPLICATION_JSON);
			
			client.doPutAccountAdd("20010010", "000000002", new BigDecimal("10.20"));

			client.doPutAccountDebit("20010010", "000000002", new BigDecimal("10.20"));
			
			
			System.out.println("#####TRANSACTIONS#####");
			JaxRsClient client1 = new JaxRsClient("http://localhost:8080/swbank_20111012/rest/transactions/");
	
			client1.doGet("3/1321015429379", MediaType.TEXT_XML);

//			Might be LazyInitializationException
//			client1.doGet("3/1321015429379", MediaType.APPLICATION_JSON);
			
			Transaction transaction = new Transaction();
			transaction.setForeignId(1337l);
			transaction.setBankCode("20010010");
			transaction.setAccountNumber("000012345");
			transaction.setFirstnameOfReceiver("Lars");
			transaction.setLastnameOfReceiver("Simon");
			transaction.setAmount(new BigDecimal("10.20"));
			
//			client1.doPostTransactionCreateViaXML(transaction);
			
			client1.doPostTransactionCreateViaJSON(transaction);
			
			
	}
}
