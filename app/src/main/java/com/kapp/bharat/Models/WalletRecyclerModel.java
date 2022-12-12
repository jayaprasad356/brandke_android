package com.kapp.bharat.Models;

public class WalletRecyclerModel {

    private String MobileNumber,AccountNumber,IFCSCode,DateOfBirth,BankId,Address,PinCode;

    public WalletRecyclerModel(String mobileNumber, String accountNumber, String IFCSCode, String dateOfBirth, String bankId, String address, String pinCode) {
        MobileNumber = mobileNumber;
        AccountNumber = accountNumber;
        this.IFCSCode = IFCSCode;
        DateOfBirth = dateOfBirth;
        BankId = bankId;
        Address = address;
        PinCode = pinCode;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getIFCSCode() {
        return IFCSCode;
    }

    public void setIFCSCode(String IFCSCode) {
        this.IFCSCode = IFCSCode;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getBankId() {
        return BankId;
    }

    public void setBankId(String bankId) {
        BankId = bankId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }
}
