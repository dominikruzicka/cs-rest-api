package cz.cs.restapi.calls;

import cz.cs.restapi.calls.model.AccountsFullRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AccountsResource {

    private final String apiKeyValue = System.getenv("API-KEY-VALUE");
    private final String apiKeyHeaderName = "WEB-API-key";
    private final String url = "https://www.csas.cz/webapi/api/v3/transparentAccounts";

    @Autowired
    private RestTemplate restTemplate;

    public AccountsFullRes getAccountsFullRes(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(apiKeyHeaderName, apiKeyValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //AccountsFullRes accountsFullRes = restTemplate.getForObject(url, entity, AccountsFullRes.class);
        ResponseEntity<AccountsFullRes> response = restTemplate.exchange(url, HttpMethod.GET, entity, AccountsFullRes.class);
        // add some error handling: response.getStatusCode()
        return response.getBody();
    }

}
