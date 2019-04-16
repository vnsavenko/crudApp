import model.Skill;
import repository.io.JavaIOSkillRepositoryImpl;

import java.util.List;

public class RepoTest {
    public static void main(String[] args) {


        Skill skill1 = new Skill( 1L, "Java");
        Skill skill2 = new Skill(2L, "SQL");
        Skill skill3 = new Skill(3L, "PHP");

        JavaIOSkillRepositoryImpl ioSkillRepository = new JavaIOSkillRepositoryImpl();

        ioSkillRepository.save(skill1);
        ioSkillRepository.save(skill2);
        ioSkillRepository.save(skill3);

        Skill byId = ioSkillRepository.getById(3L);

       if(byId !=null) {
           System.out.println("Объкт skill c id = " + byId.getId() + ": " + byId.toString());
       }
       else {
           System.out.println("Нет такого скила");
       }
// показать все скилы
        List<Skill> skills = ioSkillRepository.getAll();
        for (Skill s : skills) {
            System.out.println(s.toString());
        }

        System.out.println();


        // удалить скилл с id = 2
        ioSkillRepository.delete(2L);

        // показать оставшиеся
        skills = ioSkillRepository.getAll();
        for (Skill s : skills) {
            System.out.println(s.toString());
        }

        System.out.println();
        ioSkillRepository.delete(1L);

        // показать оставшиеся
        skills = ioSkillRepository.getAll();
        for (Skill s : skills) {
            System.out.println(s.toString());
        }

    }

}

