package cz.cs.restapi.call;

import cz.cs.restapi.call.model.Account;
import cz.cs.restapi.call.model.AccountsFullRes;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AccountsResource {

    private final String apiKeyValue = System.getenv("API-KEY-VALUE");
    private final String apiKeyHeaderName = "WEB-API-key";
    private final String url = "https://www.csas.cz/webapi/api/v3/transparentAccounts";

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<AccountsFullRes> getAccountsFullRes(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(apiKeyHeaderName, apiKeyValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<AccountsFullRes> accountsFullRes = null;
        try {
            accountsFullRes = restTemplate.exchange(url, HttpMethod.GET, entity, AccountsFullRes.class);
        } catch (HttpStatusCodeException exception){
            exception.printStackTrace();
        }
        return accountsFullRes;
    }

    //get ONLY list of accounts (without metadata like pageNumber etc.)
    public List<Account> getAccountsList(){
        ResponseEntity<AccountsFullRes> accountsFullRes = getAccountsFullRes();
        HttpStatus httpStatus = accountsFullRes.getStatusCode();
        //think better about this logic in general, so far handling null pointer in case there is error or no body sent back
        if (httpStatus.value() == 200) {
            return accountsFullRes.getBody().getAccounts();
        } else {
            return null;
        }
    }

}
