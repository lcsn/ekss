package util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.Transaction;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class WebServiceHelper {

	private static WebServiceHelper wsHelper;

	public WebServiceHelper() {
	}

	public void doPostXML(String path, Object postObject) throws Exception {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Transaction.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(postObject, sw);
        String result = sw.toString();
		ClientRequest request = new ClientRequest(path);
		request.body(MediaType.TEXT_XML, result);
		ClientResponse<String> response = request.post(String.class);
		handleResponse(response);
	}

	private void handleResponse(ClientResponse<String> response) throws IOException {
		// HTTP-Code 200 means everything is fine!
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

		String output;
		System.out.println("Response: ");
		while ((output = br.readLine()) != null) {
			System.out.print(output);
		}
		System.out.println();
	}

	public static WebServiceHelper getInstance() {
		if (wsHelper == null) {
			wsHelper = new WebServiceHelper();
		}
		return wsHelper;
	}

}
