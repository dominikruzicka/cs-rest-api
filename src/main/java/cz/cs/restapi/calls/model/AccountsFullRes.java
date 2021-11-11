package cz.cs.restapi.calls.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountsFullRes {

    private Long pageNumber;
    private Long pageCount;
    private Long pageSize;
    private Long recordCount;
    private Long nextPage;
    private ArrayList<Accounts> accountsArrayList;

    public AccountsFullRes() {
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getNextPage() {
        return nextPage;
    }

    public void setNextPage(Long nextPage) {
        this.nextPage = nextPage;
    }

    public ArrayList<Accounts> getAccountsArrayList() {
        return accountsArrayList;
    }

    public void setAccountsArrayList(ArrayList<Accounts> accountsArrayList) {
        this.accountsArrayList = accountsArrayList;
    }

    @Override
    public String toString() {
        return "AccountsRes{" +
                "pageNumber=" + pageNumber +
                ", pageCount=" + pageCount +
                ", pageSize=" + pageSize +
                ", recordCount=" + recordCount +
                ", nextPage=" + nextPage +
                ", accountsArrayList=" + accountsArrayList +
                '}';
    }
}
