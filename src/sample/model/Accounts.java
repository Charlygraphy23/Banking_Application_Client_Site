package sample.model;

import java.io.InputStream;
import java.sql.Time;
import java.sql.Timestamp;

public class Accounts {
    private String accuntNo,ifscno,balance,accountType,username,password,securityQ,answar,pin;
    private InputStream photo;
    private Timestamp lastUpdate;
    private int userId;

    public Accounts() {
    }

    public Accounts(String accuntNo,String ifscno, String balance, String accountType, String username, String password, String securityQ, String answar, InputStream photo, Timestamp lastUpdate ) {
        this.accuntNo = accuntNo;
        this.ifscno = ifscno;
        this.balance = balance;
        this.accountType = accountType;
        this.username = username;
        this.password = password;
        this.securityQ = securityQ;
        this.answar = answar;
        this.photo = photo;
        this.lastUpdate=lastUpdate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccuntNo() {
        return accuntNo;
    }

    public void setAccuntNo(String accuntNo) {
        this.accuntNo = accuntNo;
    }

    public String getIfscno() {
        return ifscno;
    }

    public void setIfscno(String ifscno) {
        this.ifscno = ifscno;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQ() {
        return securityQ;
    }

    public void setSecurityQ(String securityQ) {
        this.securityQ = securityQ;
    }

    public String getAnswar() {
        return answar;
    }

    public void setAnswar(String answar) {
        this.answar = answar;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
