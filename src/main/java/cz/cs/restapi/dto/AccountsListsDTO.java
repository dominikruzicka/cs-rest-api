package cz.cs.restapi.dto;

import java.util.List;

public class AccountsListsDTO {

    private List<String> accountsRequired;
    private List<String> accountsOptional;

    public List<String> getAccountsRequired() {
        return accountsRequired;
    }

    public void setAccountsRequired(List<String> accountsRequired) {
        this.accountsRequired = accountsRequired;
    }

    public List<String> getAccountsOptional() {
        return accountsOptional;
    }

    public void setAccountsOptional(List<String> accountsOptional) {
        this.accountsOptional = accountsOptional;
    }
}
