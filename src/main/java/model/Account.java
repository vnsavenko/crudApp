package model;

public class Account {
    private int account_id;
    private String account_number;

    public Account() {
    }

    public Account(int account_id, String account_number, AccountStatus accountStatus) {
        this.account_id = account_id;
        this.account_number = account_number;
        this.accountStatus = accountStatus;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    private AccountStatus accountStatus;

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", account_number='" + account_number + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }
}

