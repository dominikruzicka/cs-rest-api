package cz.cs.restapi.service;

import cz.cs.restapi.dto.AccountsListsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SendersService {

    private final AccountsResourceService accountsResourceService;

    public SendersService(AccountsResourceService accountsResourceService){
        this.accountsResourceService = accountsResourceService;
    }

    public ResponseEntity<Object> getSendersNamesList(AccountsListsDTO accountsLists){
        if (accountsLists.getAccountsRequired().isEmpty()
            || accountsLists.getAccountsOptional().isEmpty()) {
            String responseBody = "One or both collections in request body are empty";
            return new ResponseEntity(responseBody, HttpStatus.OK);
        } else {
            List<String> requiredAccountsListNoDupl = accountsLists.getAccountsRequired().stream().distinct().collect(Collectors.toList());
            List<String> optionalAccountsListNoDupl = accountsLists.getAccountsOptional().stream().distinct().collect(Collectors.toList());

            Map<String,String> requiredAccountsSendersMap = getRequiredAccountsSendersMap(requiredAccountsListNoDupl);

            if(requiredAccountsSendersMap.isEmpty()){
                String responseBody = "No senders found"; //in reality it can be also caused by missing values for accounts or names in CS reponse
                return new ResponseEntity(responseBody, HttpStatus.OK);
            }

            Map<String,String> optionalAccountsSendersMap = getOptionalAccountsSendersMap(optionalAccountsListNoDupl);

            //REDUCING requiredAccountsSendersMap only to matching KEYs
            requiredAccountsSendersMap = requiredAccountsSendersMap.entrySet().stream()
                                                                              .filter(e -> optionalAccountsSendersMap.containsKey(e.getKey()))
                                                                              .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));

            List<String> sendersNamesList = new ArrayList<>(requiredAccountsSendersMap.values());

            if(sendersNamesList.isEmpty()){
                String responseBody = "No senders found";
                return new ResponseEntity(responseBody, HttpStatus.OK);
            } else {
                return new ResponseEntity(sendersNamesList, HttpStatus.OK);
            }
        }
    }

    private Map<String,String> getRequiredAccountsSendersMap(List<String> requiredAccountsListNoDupl){

        Map<String,String> accountsSendersBase = new HashMap<>();
        Map<String,String> accountsSendersIncrement = new HashMap<>();
        Boolean wasComparedMinOnceFlag = false;
        Boolean wasSwapped = false; // The whole swapping logic is applied in order to make less iterations when comparing xxxBase and XXXIncrement

        for(int i = 0; i < requiredAccountsListNoDupl.size(); i++) {
            if(accountsSendersBase.isEmpty() && wasComparedMinOnceFlag == false) {
                accountsSendersBase = accountsResourceService.getAccountsSendersMap(requiredAccountsListNoDupl.get(i));
                continue;
            }
            if(accountsSendersBase.isEmpty() & wasComparedMinOnceFlag == true){
                break; //dont have to call CS anymore, there was no intersection between two accounts
            }
            if(wasSwapped == true) { //in case of call of retainAll method on HashSets was swapped, I need to save xxxIncrement into xxxBase so I dont overwrite it when calling CS again
                accountsSendersBase = accountsSendersIncrement;
            }
            accountsSendersIncrement = accountsResourceService.getAccountsSendersMap(requiredAccountsListNoDupl.get(i));
            if(accountsSendersBase.size() <= accountsSendersIncrement.size()){
                Map<String, String> finalAccountsSendersIncrement = accountsSendersIncrement;
                accountsSendersBase = accountsSendersBase.entrySet().stream()
                                                                    .filter(e -> finalAccountsSendersIncrement.containsKey(e.getKey()))
                                                                    .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
                wasComparedMinOnceFlag = true;
                wasSwapped = false;
            }else {
                Map<String, String> finalAccountsSendersBase = accountsSendersBase;
                accountsSendersIncrement = accountsSendersIncrement.entrySet().stream()
                                                                              .filter(e -> finalAccountsSendersBase.containsKey(e.getKey()))
                                                                              .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
                wasComparedMinOnceFlag = true;
                wasSwapped = true;
            }
        }
        return accountsSendersBase;
    }

    private Map<String,String> getOptionalAccountsSendersMap(List<String> optionalAccountsListNoDupl){
        Map<String,String> accountsSendersTotalMap = new HashMap<>();
        Map<String,String> accountsSendersInOneCallMap;
        for(String account : optionalAccountsListNoDupl){
            accountsSendersInOneCallMap = accountsResourceService.getAccountsSendersMap(account);
            accountsSendersTotalMap.putAll(accountsSendersInOneCallMap); //using putAll() for I dont suppose VALUE would be changed when two KEYs are matching (supposing that one account number has one owner)
        }
        return accountsSendersTotalMap;
    }



}
