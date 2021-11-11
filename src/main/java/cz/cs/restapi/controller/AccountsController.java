package cz.cs.restapi.controller;

import cz.cs.restapi.service.AccountsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/getAllAccountsList")
    public ResponseEntity<Object> getAllAccountsList(){
        return accountsService.getAllAccountsList();
    }
}


