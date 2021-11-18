package cz.cs.restapi.service;

import cz.cs.restapi.call.caller.AccountsCaller;
import cz.cs.restapi.call.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/*
I am using RestTemplate to call CS API as it is part of Spring framework.
Other options are:
 - javax.ws.rs.*  (used in CS documentation)
 - java.net.http.* (newer, use that if I have time)
 */

@Service
public class AccountsResourceService {

    private final AccountsCaller accountsCaller;

    @Autowired
    public AccountsResourceService(AccountsCaller accountsCaller){
        this.accountsCaller = accountsCaller;
    }

    //get ONLY list of accounts (without metadata like pageNumber etc.)
    public List<Account> getAccountsList(){
        ResponseEntity<AccountsFullRes> accountsFullRes = accountsCaller.getAccountsFullRes(0);
        HttpStatus httpStatus = accountsFullRes.getStatusCode();
        List<Account> accounts;
        //think better about this logic in general, so far handling null pointer in case there is error or no body sent back
        if (httpStatus.value() == 200) {
            accounts = accountsFullRes.getBody().getAccounts();
            int nextPage = accountsFullRes.getBody().getNextPage();
            while (nextPage != 0){
                accountsFullRes = accountsCaller.getAccountsFullRes(nextPage);
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
        ResponseEntity<TransactionsFullRes> transactionsFullRes = accountsCaller.getTransactionsFullRes(accountNumber, 0);
        HttpStatus httpStatus = transactionsFullRes.getStatusCode();
        List<Transaction> transactions;
        if (httpStatus.value() == 200){
            transactions = transactionsFullRes.getBody().getTransactions();
            int nextPage = transactionsFullRes.getBody().getNextPage();
            while (nextPage != 0) {
                transactionsFullRes = accountsCaller.getTransactionsFullRes(accountNumber,nextPage);
                if (transactionsFullRes.getStatusCode().value() == 200) {
                    transactions.addAll(transactionsFullRes.getBody().getTransactions());
                    nextPage = transactionsFullRes.getBody().getNextPage();
                }
            }
            HashSet<String> accountNumberOfSenders = transactions.stream()
                                                                 .filter(Objects::nonNull)
                                                                 .map(transaction -> transaction.getSender().getAccountNumber())
                                                                 .collect(Collectors.toCollection(HashSet::new));

            accountNumberOfSenders.remove(null);
            /*
            Map<String, String> a = transactions.stream()
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toMap(t -> t.getSender().getName(), v -> v.getSender().getAccountNumber()));
            */
            return accountNumberOfSenders;
        } else {
            return null;
        }
    }

}
