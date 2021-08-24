package br.com.ecommerce.client.domain;

import java.util.List;

public interface IClientRepository {
	
	List<Client> findClientsByIds(List<String> ids);
	public boolean clientExists(String clientId);
}
