package src.main.java.com.savenko.crudapp.model;


import java.util.Set;

public class Developer {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialisation;
    private Set<Skill> skills;
    private Account account;

    public Developer() {
    }

    public Developer(Long id, String firstName, String lastName, String specialisation, Set<Skill> skills, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialisation = specialisation;
        this.skills = skills;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
