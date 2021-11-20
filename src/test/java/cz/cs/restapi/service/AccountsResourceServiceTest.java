package cz.cs.restapi.service;

import cz.cs.restapi.call.caller.AccountsCaller;
import cz.cs.restapi.call.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

public class AccountsResourceServiceTest {

    AccountsResourceService accountsResourceService;
    AccountsCaller accountsCaller;

    @BeforeEach
    public void setup(){
        accountsCaller = Mockito.mock(AccountsCaller.class);
        accountsResourceService = new AccountsResourceService(accountsCaller);
    }

    @Test
    @DisplayName("Test should pass when getAccountsList() returns List of accounts matching predefined List of accounts")
    void getAccountsListTest(){

        //prepare mock reply for method getAccountsFullRes() used in getAccountsList()
        Account account1 = new Account("123");
        Account account2 = new Account("456");
        Account account3 = new Account("789");
        ArrayList<Account> accounts = new ArrayList<>(Arrays.asList(account1, account2, account3));
        AccountsFullRes accountsFullRes = new AccountsFullRes(0, accounts);
        ResponseEntity<AccountsFullRes> resEntAccountsFullRes = new ResponseEntity(accountsFullRes, HttpStatus.OK);

        doReturn(resEntAccountsFullRes).when(accountsCaller).getAccountsFullRes(0);

        //call tested method
        List<Account> accountsActual = accountsResourceService.getAccountsList();

        List<Account> accountsExpected = List.of(account1,account2,account3);

        assertTrue(accountsActual.size() == accountsExpected.size() && accountsActual.containsAll(accountsExpected));
    }

    @Test
    @DisplayName("Test should pass when getAccountNumberOfSenders() returns correct Map of account number of senders[K] and names[V] based on provided transparent account")
    void getAccountNumberOfSendersTest(){

        //prepare mock reply for method getAccountsFullRes() used in getAccountsList()
        Sender sender1 = new Sender("123", "Jiri Kuzelka");
        Transaction transaction1 = new Transaction(sender1);
        Sender sender2 = new Sender("456", "Jan Novak");
        Transaction transaction2 = new Transaction(sender2);
        ArrayList<Transaction> transactions =  new ArrayList<>(Arrays.asList(transaction1, transaction2));
        TransactionsFullRes transactionsFullRes = new TransactionsFullRes(0, transactions);
        ResponseEntity<TransactionsFullRes> resEntTransactionsFullRes = new ResponseEntity(transactionsFullRes, HttpStatus.OK);

        doReturn(resEntTransactionsFullRes).when(accountsCaller).getTransactionsFullRes("001", 0);

        //call tested method
        Map<String, String> actualMap = accountsResourceService.getAccountsSendersMap("001");

        //expected result
        Map<String, String> expectedMap = Map.ofEntries(
                entry("123", "Jiri Kuzelka"),
                entry("456", "Jan Novak")
        );

        //false result
        Map<String, String> falseResultMap = Map.ofEntries(
                entry("456", "Alfonz Pletivo")
        );

        assertTrue(actualMap.size() == expectedMap.size() & actualMap.equals(expectedMap));
        assertFalse(actualMap.size() == falseResultMap.size());
        assertFalse(actualMap.equals(falseResultMap));

    }

}
