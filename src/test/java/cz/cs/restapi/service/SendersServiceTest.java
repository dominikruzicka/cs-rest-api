package cz.cs.restapi.service;

import cz.cs.restapi.dto.AccountsListsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    @DisplayName("Test should pass when correct List of senders' names is returnes")
    void getSendersNamesListTest(){

        //prepare input
        AccountsListsDTO accountsListsDTO = new AccountsListsDTO();
        accountsListsDTO.setAccountsRequired(List.of("1010", "2020", "3030"));
        accountsListsDTO.setAccountsOptional(List.of("4040", "5050"));

        //prepare mock reply - senders account
        Map<String, String> sendersAccMap1 = Map.ofEntries(
                entry("1111", "Jiri Kuzelka"),
                entry("2222", "Jan Novak"),
                entry("3333", "Lukas Zahrada"),
                entry("4444", "Ludek Potmesil")
        );
        Map<String, String> sendersAccMap2 = Map.ofEntries(
                entry("2222", "Jan Novak"),
                entry("3333", "Lukas Zahrada"),
                entry("4444", "Ludek Potmesil"),
                entry("5555", "Premek Podlaha")
                );
        Map<String, String> sendersAccMap3 = Map.ofEntries(
                entry("3333", "Lukas Zahrada"),
                entry("4444", "Ludek Potmesil"),
                entry("5555", "Premek Podlaha"),
                entry("6666", "Norbert Prihoda")
                );
        Map<String, String> sendersAccMap4 = Map.ofEntries(
                entry("3333", "Lukas Zahrada"),
                entry("4444", "Ludek Potmesil"),
                entry("5555", "Premek Podlaha")
        );
        Map<String, String> sendersAccMap5 = Map.ofEntries(
                entry("6666", "Zbynek Hulcicka"),
                entry("7777", "Herbert Mechura")
                );

        doReturn(sendersAccMap1).when(accountsResourceService).getAccountsSendersMap("1010");
        doReturn(sendersAccMap2).when(accountsResourceService).getAccountsSendersMap("2020");
        doReturn(sendersAccMap3).when(accountsResourceService).getAccountsSendersMap("3030");
        doReturn(sendersAccMap4).when(accountsResourceService).getAccountsSendersMap("4040");
        doReturn(sendersAccMap5).when(accountsResourceService).getAccountsSendersMap("5050");

        //call tested method
        ResponseEntity<Object> responseEntity = sendersService.getSendersNamesList(accountsListsDTO);
        List<String> actualAccountsNames = (ArrayList) responseEntity.getBody();

        //expected result
        List<String> expectedAccountsNames = new ArrayList<>(Arrays.asList("Lukas Zahrada","Ludek Potmesil"));

        //false result
        List<String> falseAccountsNames = new ArrayList<>(Arrays.asList("Ludek Potmesil"));

        assertTrue(actualAccountsNames.size() == expectedAccountsNames.size() & actualAccountsNames.equals(expectedAccountsNames));
        assertFalse(falseAccountsNames.size() == expectedAccountsNames.size());
        assertFalse( falseAccountsNames.equals(expectedAccountsNames));
    }

}
