package cz.cs.restapi.call;

import cz.cs.restapi.call.caller.AccountsCaller;
import cz.cs.restapi.call.model.*;
import cz.cs.restapi.service.AccountsResourceService;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

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
        //prepare variables
        Account account1 = new Account("123");
        Account account2 = new Account("456");
        Account account3 = new Account("789");
        ArrayList<Account> accounts = new ArrayList<>(Arrays.asList(account1, account2, account3));
        AccountsFullRes accountsFullRes = new AccountsFullRes(0, accounts);
        ResponseEntity<AccountsFullRes> resEntAccountsFullRes = new ResponseEntity(accountsFullRes, HttpStatus.OK);

        //mock reply from method getAccountsFullRes() used in getAccountsList()
        doReturn(resEntAccountsFullRes).when(accountsCaller).getAccountsFullRes(0);

        //call tested method
        List<Account> accountsActual = accountsResourceService.getAccountsList();

        List<Account> accountsExpected = List.of(account1,account2,account3);

        assertTrue(accountsActual.size() == accountsExpected.size() && accountsActual.containsAll(accountsExpected));
    }

    @Test
    @DisplayName("Test should pass when getAccountNumberOfSenders() returns correct account number of senders based on provided transparent account")
    void getAccountNumberOfSendersTest(){
        //prepare variables
        Sender sender1 = new Sender("123");
        Transaction transaction1 = new Transaction(sender1);
        Sender sender2 = new Sender("456");
        Transaction transaction2 = new Transaction(sender2);
        ArrayList<Transaction> transactions =  new ArrayList<>(Arrays.asList(transaction1, transaction2));
        TransactionsFullRes transactionsFullRes = new TransactionsFullRes(0, transactions);
        ResponseEntity<TransactionsFullRes> resEntTransactionsFullRes = new ResponseEntity(transactionsFullRes, HttpStatus.OK);

        //mock reply from method getAccountsFullRes() used in getAccountsList()
        doReturn(resEntTransactionsFullRes).when(accountsCaller).getTransactionsFullRes("001", 0);

        //call tested method
        Set<String> transactionsActual = accountsResourceService.getAccountNumberOfSenders("001");

        Set<String> transactionsExpected = Set.of("123", "456");

        assertTrue(transactionsActual.size() == transactionsExpected.size() && transactionsActual.containsAll(transactionsExpected));
    }

}
