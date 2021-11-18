package cz.cs.restapi.call.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountsFullRes {

    private int pageNumber;
    private int pageCount;
    private int pageSize;
    private int recordCount;
    private int nextPage;
    private ArrayList<Account> accounts;

    public AccountsFullRes() {
    }

    public AccountsFullRes(int nextPage, ArrayList<Account> accounts) {
        this.nextPage = nextPage;
        this.accounts = accounts;
    }



    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public ArrayList<Account> getAccounts() {
            return accounts;
    }

   public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "AccountsRes{" +
                "pageNumber=" + pageNumber +
                ", pageCount=" + pageCount +
                ", pageSize=" + pageSize +
                ", recordCount=" + recordCount +
                ", nextPage=" + nextPage +
                ", accountsArrayList=" + accounts +
                '}';
    }
}
