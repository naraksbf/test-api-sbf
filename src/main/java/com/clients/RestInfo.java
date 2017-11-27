package com.clients;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.model.AccountInfo;
import com.tokens.AuthTokenInfo;

public class RestInfo {
	
	public static final String REST_SERVICE_URI = "http://192.168.100.202:8082";
	public static final String AUTH_SERVER_URI = "http://192.168.100.202:8081/oauth/token";

	public static final String QPM_PASSWORD_GRANT = "grant_type=password&client_id=WingClientApp&client_secret=P@ssword&scope=accountinfo+repayment,read+write&username=wingcambodia&password=12345678";
	//public static final String QPM_PASSWORD_GRANT = "grant_type=password&client_id=WingClientApp&scope=accountinfo%20repayment%20read%20write&username=wingcambodia&password=12345678";

	public static final String QPM_ACCESS_TOKEN = "?access_token=";

	/*
	 * Prepare HTTP Headers.
	 */
	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	/*
	 * Add HTTP Authorization header, using Basic-Authentication to send
	 * client-credentials.
	 */
	private static HttpHeaders getHeadersWithClientCredentials() {
		String plainClientCredentials = "my-trusted-client:secret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}

	/*
	 * Send a POST request [on /oauth/token] to get an access-token, which will then
	 * be send with each request.
	 */
	@SuppressWarnings({ "unchecked" })
	private static AuthTokenInfo sendTokenRequest() {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI + QPM_PASSWORD_GRANT, HttpMethod.POST,
				request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		AuthTokenInfo tokenInfo = null;

		if (map != null) {
			tokenInfo = new AuthTokenInfo();
			tokenInfo.setAccess_token((String) map.get("access_token"));
			tokenInfo.setToken_type((String) map.get("token_type"));
			tokenInfo.setRefresh_token((String) map.get("refresh_token"));
			tokenInfo.setExpires_in((int) map.get("expires_in"));
			tokenInfo.setScope((String) map.get("scope"));
			System.out.println(tokenInfo);
			// System.out.println("access_token ="+map.get("access_token")+",
			// token_type="+map.get("token_type")+",
			// refresh_token="+map.get("refresh_token")
			// +", expires_in="+map.get("expires_in")+", scope="+map.get("scope"));;
		} else {
			System.out.println("No account exist----------");

		}
		return tokenInfo;
	}

	/*
	 * Send a GET request to get list of Account users.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void accountUsers(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");

		System.out.println("\nTesting Account API-----------");
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<List> response = restTemplate.exchange(
				REST_SERVICE_URI + "/accountinfo/0833330000031" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
				HttpMethod.GET, request, List.class);
		List<LinkedHashMap<String, Object>> accountinfoMap = (List<LinkedHashMap<String, Object>>) response.getBody();

		if (accountinfoMap != null) {
			for (LinkedHashMap<String, Object> map : accountinfoMap) {
				System.out.println("User : ccy=" + map.get("ccy") + ", accountNumber=" + map.get("accountNumber")
						+ ", relatedId=" + map.get("relatedId") + ", fullName=" + map.get("fullName"));
				;
			}
		} else {
			System.out.println("No account exist----------");
		}
	}

	/*
	 * Send a GET request to get a specific user.
	 */
	private static void getAccount(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting getAccInfo API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<AccountInfo> response = restTemplate.exchange(
				REST_SERVICE_URI + "/accountinfo/0833330000031" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
				HttpMethod.GET, request, AccountInfo.class);
		AccountInfo accountInfo = response.getBody();
		System.out.println(accountInfo);
	}

	/*
	 * Send a DELETE request to delete a specific user.
	 */

	public static void main(String args[]) {
		AuthTokenInfo tokenInfo = sendTokenRequest();
		accountUsers(tokenInfo);

		getAccount(tokenInfo);
		
	}

}
