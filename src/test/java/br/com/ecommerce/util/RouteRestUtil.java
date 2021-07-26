package br.com.ecommerce.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

public class RouteRestUtil {
	
	private TestRestTemplate restTemplate;
	private String uri;
	private HttpMethod httpMethod;
	private Object payload;
	private Map<String, String> pathParams;
	private MultiValueMap<String, String> queryParams; 
	
	private RouteRestUtil (TestRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public static RouteRestUtil of(TestRestTemplate restTemplate) {
		return new RouteRestUtil(restTemplate);
	}
	
	public RouteRestUtil uri(String uri) {
		this.uri = uri;
		return this;
	}
	
	public RouteRestUtil httpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
		return this;
	}

	public RouteRestUtil payload(Object payload) {
		this.payload = payload;
		return this;
	}

	public RouteRestUtil pathParam(String key, String value) {
		if (this.pathParams == null) this.pathParams = new HashMap<>();
		this.pathParams.put(key, value);
		return this;
	}
	
	public RouteRestUtil queryParams(String key, String value) {
		if (this.queryParams == null) this.queryParams = new LinkedMultiValueMap<String, String>();
		this.queryParams.add(key, value);
		return this;
	}
	
	public ResponseEntity<Object> getReturn() {
		return getReturn(Object.class);
	}
	
	public <T> ResponseEntity<T> getReturn(Class<T> returnType) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

		HttpHeaders headers = null;
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> httpEntity = null;
		
		if (!ObjectUtils.isEmpty(payload))
			httpEntity = new HttpEntity<>(payload, headers);
		
		
		if (!ObjectUtils.isEmpty(queryParams)) {
			uriBuilder.queryParams(queryParams);
		}
		
		if (!ObjectUtils.isEmpty(pathParams)) {
			return restTemplate.exchange(uriBuilder.buildAndExpand(pathParams).toUri(), httpMethod, httpEntity, returnType);
		}
		return restTemplate.exchange(uriBuilder.toUriString(), httpMethod, httpEntity, returnType);
	}
}
