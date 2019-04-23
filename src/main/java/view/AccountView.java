package view;

import model.Skill;
import repository.io.IOProvider;
import repository.io.JavaIOAccountRepositoryImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static repository.io.JavaIOSkillRepositoryImpl.FILE_NAME;

public class AccountView {

    public static final String MENU_SKILLS = "*** В главном меню скилов ***" +
            "\n1. Добавить skill;" +
            "\n2. Получить скилл" +
            "\n3. Редактировать скилл" +
            "\n4. Удалить скилл" +
            "\n5. Вывести список скилов" +
            "\n0. Выйти из меню";



    private BufferedReader consoleReader;

    private JavaIOAccountRepositoryImpl accountRepository;

    public AccountView() {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        accountRepository = new JavaIOAccountRepositoryImpl();
    }

    public void processAccountFromConsole() throws IOException {
        while (true) {
            String messageFromConsole = consoleReader.readLine();
            boolean isValid = validateId(messageFromConsole);
            if (isValid) {
                int menuItem = Integer.parseInt(messageFromConsole);
                switch (menuItem) {
                    case 1:
                        addAccount();
                        break;
                    case 2:
                        readAccount();
                        break;
                    case 3:
                        updateAccount();
                        break;
                    case 4:
                        deleteAccount();
                        break;
                    case 5:
                        readAllAccounts();
                        break;
                    case 0:
                        consoleReader.close();
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

    private void addAccount() throws IOException {

        while (true) {
            System.out.println("Введите наименование скила, 0 - выход в меню скилов");

            String skillName = consoleReader.readLine();

            if (skillName.equals("0")) {
                System.out.println(MENU_SKILLS);
                return;
            }
            Long id = getNextId();
            accountRepository.save(new Skill(id, skillName));
            System.out.println("Скилл " + skillName + " добавлен в хранилище");

        }
    }

    private void readAccount() throws IOException {
        while (true) {
            System.out.println("Введите id скила, 0 - выход в меню скилов");
            String skillId = consoleReader.readLine();

            if (skillId.equals("0")){
                System.out.println(MENU_SKILLS);
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

    private void updateAccount() throws IOException {
        while (true) {
            System.out.println("Введите через пробел: id новое_имя_скилла . 0 - выход в меню скилов");
            String line = consoleReader.readLine();

            if (line.equals("0")){
                System.out.println(MENU_SKILLS);
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

    private void deleteAccount() throws IOException {
        while (true) {
            System.out.println("Введите id скила, 0 - выход в меню скилов");
            String skillId = consoleReader.readLine();

            if (skillId.equals("0")){
                System.out.println(MENU_SKILLS);

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

    private void readAllAccounts() {
        System.out.println("skill_id  skill_name");
        skillRepository.getAll().stream().forEach(skill -> System.out.println(skill.getId() + " " + skill.getName()));
    }

}
