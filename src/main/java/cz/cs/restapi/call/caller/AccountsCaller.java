package cz.cs.restapi.call.caller;

import cz.cs.restapi.call.model.AccountsFullRes;
import cz.cs.restapi.call.model.TransactionsFullRes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountsCaller {

    private static final String API_KEY_VALUE = System.getenv("API-KEY-VALUE");
    private static final String API_KEY_HEADER_NAME = "WEB-API-key";
    private static final String URL_BASE = "https://www.csas.cz/webapi/api/v3/transparentAccounts/";
    private static final int PAGE_SIZE = 2000; //not sure if it is ideal but want to reduce calls to CS API

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<AccountsFullRes> getAccountsFullRes(int pageNumber){
        ResponseEntity<AccountsFullRes> accountsFullRes = null;
        try {
            String urlAccounts = URL_BASE + "?page=" + pageNumber + "&size=" + PAGE_SIZE;
            accountsFullRes = restTemplate.exchange(urlAccounts, HttpMethod.GET, getHTTPEntity(), AccountsFullRes.class);
        } catch (HttpStatusCodeException exception){
            exception.printStackTrace();
        }
        return accountsFullRes;
    }

    public ResponseEntity<TransactionsFullRes> getTransactionsFullRes(String accountNumber, int pageNumber) {
        ResponseEntity<TransactionsFullRes> transactionsFullRes = null;
        try {
            String urlTransactions = URL_BASE + accountNumber + "/transactions/" + "?page=" + pageNumber + "&size=" + PAGE_SIZE; //?page=0&size=25&filter=example
            transactionsFullRes = restTemplate.exchange(urlTransactions, HttpMethod.GET, getHTTPEntity(), TransactionsFullRes.class);
        } catch (HttpStatusCodeException exception){
            exception.printStackTrace();
        }
        return transactionsFullRes;
    }

    private HttpEntity<String> getHTTPEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(API_KEY_HEADER_NAME, API_KEY_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }
}
