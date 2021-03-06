package cz.cs.restapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {

    private String accountNumber;
    private String bankCode;
    private String transparencyFrom;
    private String transparencyTo;
    private String publicationTo;
    private String actualizationDate;
    private BigDecimal balance;
    private String currency;
    private String name;
    private String description;
    private String note;
    private String iban;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTransparencyFrom() {
        return transparencyFrom;
    }

    public void setTransparencyFrom(String transparencyFrom) {
        this.transparencyFrom = transparencyFrom;
    }

    public String getTransparencyTo() {
        return transparencyTo;
    }

    public void setTransparencyTo(String transparencyTo) {
        this.transparencyTo = transparencyTo;
    }

    public String getPublicationTo() {
        return publicationTo;
    }

    public void setPublicationTo(String publicationTo) {
        this.publicationTo = publicationTo;
    }

    public String getActualizationDate() {
        return actualizationDate;
    }

    public void setActualizationDate(String actualizationDate) {
        this.actualizationDate = actualizationDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
