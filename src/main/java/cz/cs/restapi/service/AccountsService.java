package cz.cs.restapi.service;

import cz.cs.restapi.calls.AccountsResource;
import cz.cs.restapi.calls.model.AccountsFullRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

    public ResponseEntity<Object> getAllAccountsList(){
        AccountsResource accountsResource = new AccountsResource();

        System.out.println(accountsResource.getAccountsFullRes().toString());
        //testing purpose:
        String responseBody = "Testing getAccounts method";
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

}
