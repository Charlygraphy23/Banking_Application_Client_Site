package sample.model;

public class Funds {
    private String date,accountType,accountno,userAccountno;
    private int credit,debit,totalammount;

    public Funds() {
    }

    public Funds(String userAccountno,String date,String accountType,String accountno, int credit, int debit, int totalammount) {
        this.date = date;
        this.userAccountno=userAccountno;
        this.accountType=accountType;
        this.accountno=accountno;
        this.credit = credit;
        this.debit = debit;
        this.totalammount = totalammount;
    }

    public String getUserAccountno() {
        return userAccountno;
    }

    public void setUserAccountno(String userAccountno) {
        this.userAccountno = userAccountno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public int getTotalammount() {
        return totalammount;
    }

    public void setTotalammount(int totalammount) {
        this.totalammount = totalammount;
    }
}
