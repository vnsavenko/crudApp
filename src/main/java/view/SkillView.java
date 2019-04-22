package view;

import model.Skill;
import repository.io.IOProvider;
import repository.io.JavaIOSkillRepositoryImpl;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static repository.io.JavaIOSkillRepositoryImpl.FILE_NAME;

public class SkillView {

    private static final String TO_SKILL_MENU = "В главном меню скилов";
    public static final String MENU = "1. Добавить skill;" +
            "\n2. Получить скилл" +
            "\n3. Редактировать скилл" +
            "\n4. Удалить скилл" +
            "\n5. Вывести список скилов" +
            "\n0. Выйти из меню";



    private BufferedReader reader;
    private JavaIOSkillRepositoryImpl skillRepository;

    public SkillView() {
        System.out.println(MENU);
        reader = new BufferedReader(new InputStreamReader(System.in));
        skillRepository = new JavaIOSkillRepositoryImpl();
    }

    public void processMessage() throws IOException {
        while (true) {
            String messageFromConsole = reader.readLine();
            boolean isValid = validateId(messageFromConsole);
            if (isValid) {
                int menuItem = Integer.parseInt(messageFromConsole);
                switch (menuItem) {
                    case 1:
                        addSkill();
                        break;
                    case 2:
                        readSkill();
                        break;
                    case 3:
                        updateSkill();
                        break;
                    case 4:
                        deleteSkill();
                        break;
                    case 5:
                        readAllSkills();
                        break;
                    case 0:
                        reader.close();
                        System.exit(0);

                    default:
                        System.out.println("Введено неверное число (0 - 5), попробуйте еще.");
                }
            } else System.out.println("Введено не число, попробуйте еще.");

        }
    }

    private boolean validateId(String message) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher m = pattern.matcher(message);
        return m.matches();
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

    private void addSkill() throws IOException {

    while (true) {
    System.out.println("Введите наименование скила, 0 - выход в меню скилов");

    String skillName = reader.readLine();

    if (skillName.equals("0")) {
        System.out.println(TO_SKILL_MENU);
        System.out.println(MENU);
        return;
    }
            Long id = getNextId();
            skillRepository.save(new Skill(id, skillName));
            System.out.println("Скилл " + skillName + " добавлен в хранилище");

    }
}

    private void readSkill() throws IOException {
        while (true) {
            System.out.println("Введите id скила, 0 - выход в меню скилов");
            String skillId = reader.readLine();

            if (skillId.equals("0")){
                System.out.println(TO_SKILL_MENU);
                System.out.println(MENU);
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

    private void updateSkill() throws IOException {
        while (true) {
            System.out.println("Введите через пробел: id новое_имя_скилла . 0 - выход в меню скилов");
            String line = reader.readLine();

            if (line.equals("0")){
                System.out.println(TO_SKILL_MENU);
                System.out.println(MENU);
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

    private void deleteSkill() throws IOException {
        while (true) {
            System.out.println("Введите id скила, 0 - выход в меню скилов");
            String skillId = reader.readLine();

            if (skillId.equals("0")){
                System.out.println(TO_SKILL_MENU);
                System.out.println(MENU);

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

    private void readAllSkills() {
        System.out.println("skill_id  skill_name");
        skillRepository.getAll().stream().forEach(skill -> System.out.println(skill.getId() + " " + skill.getName()));
    }

}
