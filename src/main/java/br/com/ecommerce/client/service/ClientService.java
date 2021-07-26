package br.com.ecommerce.client.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ecommerce.client.domain.Client;
import br.com.ecommerce.mock.product.ClientApiResourcesMock;
import br.com.ecommerce.mock.util.JsonFormatterComplete;

@Service
public class ClientService {

	@SuppressWarnings("unchecked")
	public List<Client> findClientsByIds(List<String> ids) {
		String clientsArrayJson = ClientApiResourcesMock.clientsArrayResponseJSON;
		
		List<Map<String, Object>> clientsMap = JsonFormatterComplete.jsonToObject(clientsArrayJson, List.class);
		List<Client> clients = clientsMap.stream()
			.map(v -> JsonFormatterComplete.jsonToObject(JsonFormatterComplete.objectToJson(v), Client.class))
			.collect(Collectors.toList());

		if (clients != null && !clients.isEmpty()) {
			return clients.stream()
					.filter(p -> ids.contains(p.getId()))
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	public boolean clientExists(String clientId) {
		List<Client> clients = findClientsByIds(Arrays.asList(clientId));
		return clients != null && !clients.isEmpty();
	}
}
