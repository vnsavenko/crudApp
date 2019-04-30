package src.main.java.com.savenko.crudapp.controller;

import src.main.java.com.savenko.crudapp.model.Skill;
import src.main.java.com.savenko.crudapp.repository.io.IOProvider;
import src.main.java.com.savenko.crudapp.repository.io.JavaIOSkillRepositoryImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.com.savenko.crudapp.repository.io.JavaIOSkillRepositoryImpl.FILE_NAME;

public class SkillController {

    private JavaIOSkillRepositoryImpl skillRepository;

    public SkillController() {
        skillRepository = new JavaIOSkillRepositoryImpl();
    }

    private boolean validateLine(String line) {
        String[] words = line.split(" ");
        try{
            return validateId(words[0])&&(!words[1].equals(""));}
        catch (Exception e) {
            return false;
        }
    }

    //idAutoIncrement
    private Long getNextId() {
        Long id = 0L;
        Long max = 0L;
        try (BufferedReader reader = IOProvider.getReader(FILE_NAME)) {

            while (reader.ready()) {
                String line = reader.readLine();
                if (line.split(",").length ==2) {

                    id = Long.parseLong(line.substring(0, line.indexOf(',')));
                    if (id > max) {
                        max = id;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (max + 1);
    }

    public boolean validateId(String message) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher m = pattern.matcher(message);
        return m.matches();
    }

    public void save(BufferedReader consoleReader, String menu) throws IOException {

        while (true) {
            System.out.println("Введите наименование скила, 0 - выход в меню скилов");

            String skillName = consoleReader.readLine();

            if (skillName.equals("0")) {
                System.out.println(menu);
                return;
            }
            Long id = getNextId();
            skillRepository.save(new Skill(id, skillName));
            System.out.println("Скилл " + skillName + " добавлен в хранилище");

        }
    }

    public void getById(BufferedReader consoleReader, String menu) throws IOException {
        while (true) {
            System.out.println("Введите id скила, 0 - выход в меню скилов");
            String skillId = consoleReader.readLine();

            if (skillId.equals("0")){
                System.out.println(menu);
                return;
            }

            if (validateId(skillId)) {
                Skill skill = skillRepository.getById(Long.parseLong(skillId));
                if (skill != null) {
                    System.out.println("Скилл с id = " + skill.getId() + " : " + skill.getName());
                }
            } else {
                System.out.println("Введен неверный формат id");
            }
        }
    }

    public void update(BufferedReader consoleReader, String menu) throws IOException {
        while (true) {
            System.out.println("Введите через пробел: id новое_имя_скилла . 0 - выход в меню скилов");
            String line = consoleReader.readLine();

            if (line.equals("0")){
                System.out.println(menu);
                return;
            }

            if (validateLine(line)) {
                Long id = Long.parseLong(line.substring(0, line.indexOf(' ')));
                String skillName = line.substring(line.indexOf(' ') + 1);
                Skill skill = new Skill(id,skillName);
                skillRepository.update(skill);

            } else {
                System.out.println("Ввод не коректен, необходимо: id новое_имя_скилла");
            }
        }
    }

    public void delete(BufferedReader consoleReader, String menu) throws IOException {
        while (true) {
            System.out.println("Введите id скила, 0 - выход в меню скилов");
            String skillId = consoleReader.readLine();

            if (skillId.equals("0")){
                System.out.println(menu);

                return;
            }

            if (validateId(skillId)) {
                Skill skill = skillRepository.getById(Long.parseLong(skillId));
                if (skill != null) {
                    System.out.println("Скилл с id = " + skill.getId() + " удален");
                    skillRepository.delete(skill.getId());
                }
            } else {
                System.out.println("Введен неверный формат id");
            }
        }
    }

    public void getAll() {
        System.out.println("skill_id  skill_name");
        skillRepository.getAll().stream().forEach(skill -> System.out.println(skill.getId() + " " + skill.getName()));
    }
}
