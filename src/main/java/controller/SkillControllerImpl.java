package controller;

import model.Skill;
import repository.io.JavaIOSkillRepositoryImpl;

public class SkillControllerImpl implements SkillController{

    JavaIOSkillRepositoryImpl ioSkillRepository = new JavaIOSkillRepositoryImpl();
    @Override
    public void save(Skill skill) {
        ioSkillRepository.save(skill);
    }
}
