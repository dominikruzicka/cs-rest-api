package cz.cs.restapi.service;

import cz.cs.restapi.call.model.Account;
import cz.cs.restapi.dto.AccountDTO;
import cz.cs.restapi.error.ErrorResponseCatalogue;
import cz.cs.restapi.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService {

    private final AccountMapper accountMapper;
    private final AccountsResourceService accountsResourceService;

    @Autowired
    public AccountsService(AccountMapper taskMapper, AccountsResourceService accountsResourceService) {
        this.accountMapper = taskMapper;
        this.accountsResourceService = accountsResourceService;
    }

    public ResponseEntity<Object> getAllAccountsList(){
        List<Account> accounts = accountsResourceService.getAccountsList();
        if (accounts != null) {
             List<AccountDTO> accountDTOS = accountMapper.map(accounts);
             return new ResponseEntity(accountDTOS, HttpStatus.OK);
        } else{
            return new ResponseEntity(ErrorResponseCatalogue.err1, HttpStatus.BAD_REQUEST);
        }
    }

}
