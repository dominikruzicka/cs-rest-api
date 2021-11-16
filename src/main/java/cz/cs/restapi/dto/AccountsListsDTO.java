package cz.cs.restapi.dto;

import java.util.List;

public class AccountsListsDTO {

    /*
        Using Lists and removing duplicates later (not using Set) because I suppose iteration
        through ArrayList will be faster than HashSet. Even though in case of just few fields
        that I expect on request there is no difference.
        Could be also done using Set and converting it to List, but this seems to me as more
        transparent as all the incoming fields from request are first visible.
    */
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
