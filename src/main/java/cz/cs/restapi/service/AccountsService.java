package cz.cs.restapi.service;

import cz.cs.restapi.call.AccountsResource;
import cz.cs.restapi.call.model.Account;
import cz.cs.restapi.dto.AccountDTO;
import cz.cs.restapi.error.ErrorResponseCatalogue;
import cz.cs.restapi.mapper.AccountMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService {

    private final AccountMapper accountMapper;
    private final AccountsResource accountsResource;

    public AccountsService(AccountMapper taskMapper, AccountsResource accountsResource) {
        this.accountMapper = taskMapper;
        this.accountsResource = accountsResource;
    }

    public ResponseEntity<Object> getAllAccountsList(){
        List<Account> accounts = accountsResource.getAccountsList();
        if (accounts != null) {
             List<AccountDTO> accountDTOS = accountMapper.map(accounts);
             return new ResponseEntity(accountDTOS, HttpStatus.OK);
        } else{
            return new ResponseEntity(ErrorResponseCatalogue.err1, HttpStatus.BAD_REQUEST);
        }
    }

}
