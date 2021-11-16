package cz.cs.restapi.service;

import cz.cs.restapi.call.AccountsResource;
import cz.cs.restapi.dto.AccountsListsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendersService {

    private final AccountsResource accountsResource;

    public SendersService(AccountsResource accountsResource){
        this.accountsResource = accountsResource;
    }

    public ResponseEntity<Object> getSendersList(AccountsListsDTO accountsLists){
        if (accountsLists.getAccountsRequired().isEmpty()
            || accountsLists.getAccountsOptional().isEmpty()) {
            String responseBody = "One or both collections in request body are empty";
            return new ResponseEntity(responseBody, HttpStatus.OK);
        } else {
            //implement both cases OK and NO_CONTENT if I dont find matching senders
            List<String> requiredAccountsListNoDupl = accountsLists.getAccountsRequired().stream().distinct().collect(Collectors.toList());
            List<String> optionalAccountsListNoDupl = accountsLists.getAccountsOptional().stream().distinct().collect(Collectors.toList());
            getMatchingSenders(requiredAccountsListNoDupl, optionalAccountsListNoDupl);

            HashSet<String> accountNumberOfSenders = accountsResource.getAccountNumberOfSenders("000000-4420550349");

            //CONTINUE WITH LOGIC

            return new ResponseEntity("all ok", HttpStatus.OK);
        }
    }

    //be beware of arguments order when calling this method -> need to improve
    private void getMatchingSenders(List<String> requiredAccountsList, List<String> optionalAccountsList){

    }

}
