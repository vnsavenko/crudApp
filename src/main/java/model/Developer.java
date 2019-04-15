package model;


import java.util.Set;

public class Developer {
    private int id;
    private Skill skill;
    private Account account;

    public Developer(int id, Skill skill, Account account) {
        this.id = id;
        this.skill = skill;
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", skill=" + skill +
                ", account=" + account +
                '}';
    }
}
