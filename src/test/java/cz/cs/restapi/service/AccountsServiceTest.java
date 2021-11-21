package cz.cs.restapi.service;

import cz.cs.restapi.call.model.Account;
import cz.cs.restapi.dto.AccountDTO;
import cz.cs.restapi.mapper.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

public class AccountsServiceTest {

    AccountMapper accountMapper;
    AccountsResourceService accountsResourceService;
    AccountsService accountsService;

    @BeforeEach
    void setup(){
        accountMapper = Mockito.mock(AccountMapper.class);
        accountsResourceService = Mockito.mock(AccountsResourceService.class);
        accountsService = new AccountsService(accountMapper,accountsResourceService);
    }

    @Test
    @DisplayName("Should return correct ResponseEntity<Object> with mapped DTO as body in it")
    void getAllAccountsListTest(){

        //prepare mock reply
        Account account1 = new Account("123");
        Account account2 = new Account("456");
        List<Account> accountsList = new ArrayList<>(Arrays.asList(account1, account2));
        doReturn(accountsList).when(accountsResourceService).getAccountsList();

        AccountDTO accountDTO1 = new AccountDTO();
        accountDTO1.setAccountNumber("123");
        AccountDTO accountDTO2 = new AccountDTO();
        accountDTO2.setAccountNumber("456");
        List<AccountDTO> accountDTOS = List.of(accountDTO1,accountDTO2);
        doReturn(accountDTOS).when(accountMapper).map(accountsList);

        //call tested method
        ResponseEntity<Object> actualResponseEntity = accountsService.getAllAccountsList();

        //expected result
        ResponseEntity<Object> expectedResponseEntity = new ResponseEntity<>(accountDTOS,HttpStatus.OK);

         //assert
        assertTrue(actualResponseEntity.equals(expectedResponseEntity));
    }

}
