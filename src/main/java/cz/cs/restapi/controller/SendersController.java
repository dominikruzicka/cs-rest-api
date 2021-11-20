package cz.cs.restapi.controller;

import cz.cs.restapi.dto.AccountsListsDTO;
import cz.cs.restapi.service.SendersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/senders")
public class SendersController {

    private final SendersService senderService;

    public SendersController(SendersService senderService){
        this.senderService = senderService;
    }

    @GetMapping("/getSendersList")
    public ResponseEntity<Object> getSendersList(@RequestBody AccountsListsDTO accountsLists){
        return senderService.getSendersNamesList(accountsLists);
    }

}
