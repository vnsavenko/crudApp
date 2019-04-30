package src.main.java.com.savenko.crudapp.model;

public class Account {
    private Long id;

    private String accountData;

    private AccountStatus status;

    public Account() {
    }

    public Account(Long id, String accountData, AccountStatus status) {
        this.id = id;
        this.accountData = accountData;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountData() {
        return accountData;
    }

    public void setAccountData(String accountData) {
        this.accountData = accountData;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        switch (status){
            case "ACTIVE":
                this.status = AccountStatus.ACTIVE;
            break;
            case  "BANNED":
                this.status = AccountStatus.BANNED;
                break;
            case "DELETED":
                this.status = AccountStatus.DELETED;
                break;
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountData='" + accountData + '\'' +
                ", status=" + status +
                '}';
    }
}

