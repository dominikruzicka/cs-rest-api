package cz.cs.restapi.service;

import cz.cs.restapi.call.AccountsResource;
import cz.cs.restapi.dto.AccountsListsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

            Set<String> requiredAccountNumberOfSenders = getRequiredAccountNumberOfSenders(requiredAccountsListNoDupl);

            if(requiredAccountNumberOfSenders.isEmpty()){
                String responseBody = "No senders found"; //in reality it can be also caused by missing values for accounts in CS reponse
                return new ResponseEntity(responseBody, HttpStatus.OK);
            }

            Set<String> optionalAccountNumberOfSenders = getOptionalAccountNumberOfSenders(optionalAccountsListNoDupl);

            Set<String> intersectionOfReqAndOpt = new HashSet<>(requiredAccountNumberOfSenders); //Copying Set is done only for better readability, retainAll can be also called straight on "requiredAccountNumberOfSenders", just in my opinion could bring confusion for other developers
            intersectionOfReqAndOpt.retainAll(optionalAccountNumberOfSenders);

            if(intersectionOfReqAndOpt.isEmpty()){
                String responseBody = "No senders found";
                return new ResponseEntity(responseBody, HttpStatus.OK);
            } else {
                return new ResponseEntity(intersectionOfReqAndOpt, HttpStatus.OK);
            }
        }
    }

    private Set<String> getRequiredAccountNumberOfSenders(List<String> requiredAccountsListNoDupl){

        HashSet<String> accountNumberOfSendersBase = new HashSet<>();
        HashSet<String> accountNumberOfSendersIncrement = new HashSet<>();
        Boolean wasComparedMinOnceFlag = false;
        Boolean wasSwapped = false; // The whole swapping logic is applied in order to make less iterations when comparing xxxBase and XXXIncrement

        for(int i = 0; i < requiredAccountsListNoDupl.size(); i++) {
            if(accountNumberOfSendersBase.isEmpty() & wasComparedMinOnceFlag == false) {
                accountNumberOfSendersBase = accountsResource.getAccountNumberOfSenders(requiredAccountsListNoDupl.get(i));
                continue;
            }
            if(accountNumberOfSendersBase.isEmpty() & wasComparedMinOnceFlag == true){
                break; //dont have to call CS anymore, there was no intersection between two accounts
            }
            if(wasSwapped == true) { //in case of call of retainAll method on HashSets was swapped, I need to save xxxIncrement into xxxBase so I dont overwrite it when calling CS again
                accountNumberOfSendersBase = accountNumberOfSendersIncrement;
            }
            accountNumberOfSendersIncrement = accountsResource.getAccountNumberOfSenders(requiredAccountsListNoDupl.get(i));
            if(accountNumberOfSendersBase.size() < accountNumberOfSendersIncrement.size()){
                accountNumberOfSendersBase.retainAll(accountNumberOfSendersIncrement);
                wasComparedMinOnceFlag = true;
                wasSwapped = false;
            }else {
                accountNumberOfSendersIncrement.retainAll(accountNumberOfSendersBase);
                wasComparedMinOnceFlag = true;
                wasSwapped = true;
            }
        }
        return accountNumberOfSendersBase;
    }

    private Set<String> getOptionalAccountNumberOfSenders(List<String> optionalAccountsListNoDupl){
        Set<String> accountNumberOfSendersTotal = new HashSet<>();
        Set<String> accountNumberOfSendersInOneCall;
        for(String account : optionalAccountsListNoDupl){
            accountNumberOfSendersInOneCall = accountsResource.getAccountNumberOfSenders(account);
            accountNumberOfSendersTotal.addAll(accountNumberOfSendersInOneCall);
        }
        return accountNumberOfSendersTotal;
    }



}
