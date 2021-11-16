package cz.cs.restapi.call;

import cz.cs.restapi.call.model.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
I am using RestTemplate to call CS API as it is part of Spring framework.
Other options are:
 - javax.ws.rs.*  (used in CS documentation)
 - java.net.http.* (newer, use that if I have time)
 */

@Service
public class AccountsResource {

    private static final String API_KEY_VALUE = System.getenv("API-KEY-VALUE");
    private static final String API_KEY_HEADER_NAME = "WEB-API-key";
    private static final String URL_BASE = "https://www.csas.cz/webapi/api/v3/transparentAccounts/";
    private static final int PAGE_SIZE = 2000; //not sure if it is ideal but want to reduce calls to CS API

    private RestTemplate restTemplate = new RestTemplate();

    //get ONLY list of accounts (without metadata like pageNumber etc.)
    public List<Account> getAccountsList(){
        ResponseEntity<AccountsFullRes> accountsFullRes = getAccountsFullRes(0);
        HttpStatus httpStatus = accountsFullRes.getStatusCode();
        List<Account> accounts;
        //think better about this logic in general, so far handling null pointer in case there is error or no body sent back
        if (httpStatus.value() == 200) {
            accounts = accountsFullRes.getBody().getAccounts();
            int nextPage = accountsFullRes.getBody().getNextPage();
            while (nextPage != 0 || accountsFullRes.getBody().getPageCount()-1 != accountsFullRes.getBody().getPageNumber()) { //conditions are REDUNDANT, but are here to be sure program will not end in infinite loop in case nextPage datatype changes from int to Long, then it will not return 0 but NULL instead
                accountsFullRes = getAccountsFullRes(nextPage);
                if (accountsFullRes.getStatusCode().value() == 200) {
                    accounts.addAll(accountsFullRes.getBody().getAccounts());
                    nextPage = accountsFullRes.getBody().getNextPage();
                }
            }
            return accounts;
        } else {
            return null;
        }
    }

    public HashSet<String> getAccountNumberOfSenders(String accountNumber){
        ResponseEntity<TransactionsFullRes> transactionsFullRes = getTransactionsFullRes(accountNumber, 0);
        HttpStatus httpStatus = transactionsFullRes.getStatusCode();
        List<Transaction> transactions;
        if (httpStatus.value() == 200){
            transactions = transactionsFullRes.getBody().getTransactions();
            int nextPage = transactionsFullRes.getBody().getNextPage();
            while (nextPage != 0 || transactionsFullRes.getBody().getPageCount()-1 != transactionsFullRes.getBody().getPageNumber()) { //conditions are REDUNDANT, but are here to be sure program will not end in infinite loop in case nextPage datatype changes from int to Long, then it will not return 0 but NULL instead
                transactionsFullRes = getTransactionsFullRes(accountNumber,nextPage);
                if (transactionsFullRes.getStatusCode().value() == 200) {
                    transactions.addAll(transactionsFullRes.getBody().getTransactions());
                    nextPage = transactionsFullRes.getBody().getNextPage();
                }
            }
            HashSet<String> accountNumberOfSenders = transactions.stream().map(transaction -> transaction.getSender().getAccountNumber()).collect(Collectors.toCollection(HashSet::new));
            accountNumberOfSenders.remove(null);
            return accountNumberOfSenders;
        } else {
            return null;
        }
    }

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
