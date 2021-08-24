package br.com.ecommerce.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.ecommerce.client.domain.Client;
import br.com.ecommerce.client.domain.IClientRepository;

@Service
public class ClientService {

	@Autowired
	@Qualifier("ClientRepository")
	private IClientRepository clientRepository;
	
	public List<Client> findClientsByIds(List<String> ids) {
		return clientRepository.findClientsByIds(ids);
	}
	
	public boolean clientExists(String clientId) {
		return clientRepository.clientExists(clientId);
	}
}
