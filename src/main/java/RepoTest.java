import model.Skill;

import repository.io.JavaIOSkillRepositoryImpl;



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

       printAll();

        ioSkillRepository.delete(1L);

        // показать оставшиеся

        printAll();

        Skill skillUpdate = new Skill(3L, "Python");

        ioSkillRepository.update(skillUpdate);

        printAll();

    }

    private static void printAll() {
        System.out.println();
        new JavaIOSkillRepositoryImpl().getAll().stream().forEach(skill -> System.out.println(skill));
    }

}

