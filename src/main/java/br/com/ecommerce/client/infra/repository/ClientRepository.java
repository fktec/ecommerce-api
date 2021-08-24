package br.com.ecommerce.client.infra.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.ecommerce.client.domain.Client;
import br.com.ecommerce.client.domain.IClientRepository;
import br.com.ecommerce.mock.product.ClientApiResourcesMock;
import br.com.ecommerce.mock.util.JsonFormatterComplete;

@Repository(value = "ClientRepository")
public class ClientRepository implements IClientRepository {

	@Override
	public List<Client> findClientsByIds(List<String> ids) {
		/**
		 *  MOCK
		 */
		String clientsArrayJson = ClientApiResourcesMock.clientsArrayResponseJSON;
		
		@SuppressWarnings("unchecked")
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

	@Override
	public boolean clientExists(String clientId) {
		/**
		 *  MOCK
		 */
		if (clientId != null && !clientId.isEmpty()) {
			List<Client> clients = findClientsByIds(Arrays.asList(clientId));
			return clients != null && !clients.isEmpty();
		}
		return false;
	}

}
