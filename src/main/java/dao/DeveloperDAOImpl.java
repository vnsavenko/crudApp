package dao;

import model.Account;
import model.Developer;
import model.Skill;

import java.util.Arrays;
import java.util.List;

public class DeveloperDAOImpl implements DeveloperDAO{


    public void addDeveloper(Developer developer) {

    }

    public void updateDeveloper(int id) {

    }

    public void removeDeveloper(int id) {

    }

    public Developer getDeveloperById(int id) {
        return null;
    }



    private List<Developer> developers = Arrays.asList(new Developer(1, new Skill(1, "Java"), new Account()),
            new Developer(2, new Skill(2, "php"), new Account()));


    public List<Developer> getDevelopers(){
        return developers;
    }


}
