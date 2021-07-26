package br.com.ecommerce.mock.product;

import org.springframework.core.io.ClassPathResource;

import br.com.ecommerce.mock.util.ResourceReader;

public class ClientApiResourcesMock {

	private static final String CLIENT_PATH = "mocks/client/";
	
	private ClientApiResourcesMock() {}

	public static final String clientsArrayResponseJSON =
			ResourceReader.asString(new ClassPathResource(CLIENT_PATH + "payload-success-response_CLIENTS.json"));
}
