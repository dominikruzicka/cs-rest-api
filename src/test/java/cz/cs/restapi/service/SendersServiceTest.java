package cz.cs.restapi.service;

import cz.cs.restapi.dto.AccountsListsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

public class SendersServiceTest {

    SendersService sendersService;
    AccountsResourceService accountsResourceService;

    @BeforeEach
    public void setup(){
        accountsResourceService = Mockito.mock(AccountsResourceService.class);
        sendersService = new SendersService(accountsResourceService);
    }

    @Test
    @DisplayName("Test should pass when correct List of Senders is returned")
    void getSendersListTest(){

        //prepare input
        AccountsListsDTO accountsListsDTO = new AccountsListsDTO();
        accountsListsDTO.setAccountsRequired(List.of("1010", "2020", "3030"));
        accountsListsDTO.setAccountsOptional(List.of("4040", "5050"));

        //prepare mock reply - senders account
        HashSet<String> senders1 = new HashSet<>(Arrays.asList("1111","2222","3333","4444"));
        HashSet<String> senders2 = new HashSet<>(Arrays.asList("2222","3333","4444","5555"));
        HashSet<String> senders3 = new HashSet<>(Arrays.asList("3333","4444","5555","6666"));
        HashSet<String> senders4 = new HashSet<>(Arrays.asList("3333","4444","5555"));
        HashSet<String> senders5 = new HashSet<>(Arrays.asList("6666","7777","8888"));

        doReturn(senders1).when(accountsResourceService).getAccountNumberOfSenders("1010");
        doReturn(senders2).when(accountsResourceService).getAccountNumberOfSenders("2020");
        doReturn(senders3).when(accountsResourceService).getAccountNumberOfSenders("3030");
        doReturn(senders4).when(accountsResourceService).getAccountNumberOfSenders("4040");
        doReturn(senders5).when(accountsResourceService).getAccountNumberOfSenders("5050");

        //call tested method
        ResponseEntity<Object> responseEntity = sendersService.getSendersList(accountsListsDTO);
        HashSet<String> actualSendersAccount = (HashSet<String>) responseEntity.getBody();

        //expected result
        HashSet<String> expectedSendersAccount = new HashSet<>(Arrays.asList("3333","4444"));

        assertTrue(actualSendersAccount.size() == expectedSendersAccount.size() && actualSendersAccount.equals(expectedSendersAccount));
    }

}
