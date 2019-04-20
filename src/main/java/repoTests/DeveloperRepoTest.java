package repoTests;

import model.Account;
import model.AccountStatus;
import model.Developer;
import model.Skill;
import repository.io.JavaIOAccountRepositoryImpl;
import repository.io.JavaIODeveloperRepositoryImpl;
import repository.io.JavaIOSkillRepositoryImpl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;

public class DeveloperRepoTest {
    public static void main(String[] args) {
        JavaIOSkillRepositoryImpl skillRepository = new JavaIOSkillRepositoryImpl();
        JavaIODeveloperRepositoryImpl developerRepository = new JavaIODeveloperRepositoryImpl();
        JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl();

        Account account = new Account(1L,"BLABLA", AccountStatus.ACTIVE);
        Set<Skill> skills= new LinkedHashSet<>();

//        Skill skill1 = new Skill(1L, "SQL");
//        Skill skill2 = new Skill(2L, "JavaScript");
//        Skill skill3 = new Skill(3L, "HTML");
//
//
//        skills.add(skill1);
//        skills.add(skill2);
//        skills.add(skill3);
//
//        skillRepository.save(skill1);
//        skillRepository.save(skill2);
//        skillRepository.save(skill3);

//        accountRepository.save(account);
//
//        Developer developer = new Developer();
//        developer.setId(1L);
//        developer.setFirstName("Vladimir");
//        developer.setLastName("Savenko");
//        developer.setSpecialisation("JavaDeveloper");
//        developer.setSkills(skills);
//        developer.setAccount(account);


        // developerRepository.save(developer);

        System.out.println(developerRepository.getById(1L).toString());
      //  System.out.println(developerRepository.getById(2L).toString());
        //System.out.println(developerRepository.getById(3L).toString());

        System.out.println(developerRepository.getAll().toString());

       // developerRepository.delete(1L);

        //System.out.println(developerRepository.getAll().toString());

        Developer devUpdate = developerRepository.getById(1L);
        devUpdate.setFirstName("VALDEMAR");
        developerRepository.update(devUpdate);

        System.out.println();
        System.out.println(developerRepository.getAll().toString());







    }
}
