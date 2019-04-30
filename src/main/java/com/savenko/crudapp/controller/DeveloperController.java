package src.main.java.com.savenko.crudapp.controller;

import src.main.java.com.savenko.crudapp.model.Account;
import src.main.java.com.savenko.crudapp.model.Developer;
import src.main.java.com.savenko.crudapp.model.Skill;
import src.main.java.com.savenko.crudapp.repository.AccountRepository;
import src.main.java.com.savenko.crudapp.repository.DeveloperRepository;
import src.main.java.com.savenko.crudapp.repository.SkillRepository;
import src.main.java.com.savenko.crudapp.repository.io.IOProvider;
import src.main.java.com.savenko.crudapp.repository.io.JavaIOAccountRepositoryImpl;
import src.main.java.com.savenko.crudapp.repository.io.JavaIODeveloperRepositoryImpl;
import src.main.java.com.savenko.crudapp.repository.io.JavaIOSkillRepositoryImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeveloperController {

    DeveloperRepository developerRepository;
    SkillRepository skillRepository;
    AccountRepository accountRepository;

    public DeveloperController() {
        developerRepository = new JavaIODeveloperRepositoryImpl();
        skillRepository = new JavaIOSkillRepositoryImpl();
        accountRepository = new JavaIOAccountRepositoryImpl();
    }

    public boolean validateId(String message) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher m = pattern.matcher(message);
        return m.matches();
    }

    private boolean validateConsoleInput(String[] words) {
        try {
            boolean validLength = (words.length == 3);
            boolean validFirstNAme = !words[0].equals("");
            boolean validLastName = !words[1].equals("");
            boolean istrue = validLength & validFirstNAme & validLastName;
            return istrue;
        } catch (Exception e){
            return false;}
    }

    private Long getNextId(String fileName) {
        Long id ;
        Long max = 0L;
        try (BufferedReader reader = IOProvider.getReader(fileName)) {

            while (reader.ready()) {
                String line = reader.readLine();
                if (line.split(",").length >= 2) {

                    id = Long.parseLong(line.substring(0, line.indexOf(',')));
                    if (id > max) {
                        max = id;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return 1L;
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (max + 1);

    }

    private Set<Skill> getSkills(BufferedReader consoleReader) throws IOException {
        String line;
        String[] consoleData;
        Set<Skill> skillSet;

        System.out.println("| Skill_ID  |  Skill_Name |");
        System.out.println("------------------------");
        skillRepository.getAll().stream().forEach(skill -> System.out.println("|  " + skill.getId() + "         |  " + skill.getName()));




        skillSet = new LinkedHashSet<>(); // сет скилов для девелопера

        while (true) {
            System.out.println("Введите через пробел id скилов девелопера");
            line = consoleReader.readLine();
            consoleData = line.split(" ");//массив с  id скилов
            boolean isValid = true;// все id скилов - нумерик

            for (int i = 0; i < consoleData.length; i++) {
                isValid &= validateId(consoleData[i]);
            }
            //System.out.println("isValid = " + isValid);
            if (isValid) {

                   for (int i = 0; i < consoleData.length; i++) {
                    Skill skill = skillRepository.getById(Long.parseLong(consoleData[i]));
                    if (skill != null) {
                        skillSet.add(skill);
                    }
                }
            break;}
            else {
                System.out.println("Id скилов введены неверно");
                System.out.println();
            }

        }
        return skillSet;
    }

    private Account getAccount(BufferedReader consoleReader) throws IOException {
        String line;
        Account account;

        System.out.println("| Account_ID  |  Account_Data |  Status");
        System.out.println("------------------------");
        accountRepository.getAll().stream().forEach(acc -> System.out.println("|  " + acc.getId() + "         |  " + acc.getAccountData()+
                "   |   " + acc.getStatus().name()));

while (true) {

    System.out.println("Введите id аккаунта девелопера");

    line = consoleReader.readLine();
try {
    Long id = Long.parseLong(line);
    account = accountRepository.getById(id);
    if (account!=null)    {break;}
    else continue;
} catch (Exception e) {
    System.out.println("Id введен неверно");
    System.out.println();
}


}

        return account;
    }

    public void save(BufferedReader consoleReader, String menu) throws IOException {
        Developer developer = null;
        Account account;
        Set<Skill> skillSet;


        while (true) {
            developer = new Developer();

            System.out.println("Введите через пробел данные девелопера: " + "" +
                    "\n имя фамилия специализация." +
                    "\n 0 - выход в меню девов");

            String line = consoleReader.readLine();
            if (line.equals("0")) {
                System.out.println(menu);
                return;
            }
            String[] consoleData = line.split(" ");

            if(validateConsoleInput(consoleData)) {
                Long id = getNextId(JavaIODeveloperRepositoryImpl.FILE_NAME);
                String fName = consoleData[0];
                String lName = consoleData[1];
                String spec = consoleData[2];
                developer.setId(id);
                developer.setFirstName(fName);
                developer.setLastName(lName);
                developer.setSpecialisation(spec);

                skillSet = getSkills(consoleReader);
                account = getAccount(consoleReader);


                developer.setSkills(skillSet);
                developer.setAccount(account);

                developerRepository.save(developer);
                System.out.println("Девелопер " + fName + " " + lName + " записан в хранилище");
            } else {
                System.out.println("Данные введены неверно. Try again.");
            }
            System.out.println();
        }


    }

    public void getById(BufferedReader consoleReader, String menu) throws IOException {

        while (true) {
            System.out.println("Введите id девелопера, 0 - выход в меню девов");
            String consoleInput = consoleReader.readLine();

            if (consoleInput.equals("0")){
                System.out.println(menu);
                return;
            }

            if (validateId(consoleInput)) {
                Developer developer = developerRepository.getById(Long.parseLong(consoleInput));
                if (developer != null) {
                    System.out.println("Девелопер с id = " + developer.getId() + " : " + developer.getFirstName() + " " +
                            developer.getLastName());
                }
            } else {
                System.out.println("Введен неверный формат id");
            }
        }
    }

    public void update(BufferedReader consoleReader, String menu) throws IOException {
        while (true) {
            Developer developer = null;
            System.out.println("Введите id девелопера" +
                    "\n0 - выход в меню девов");
            String consoleInput = consoleReader.readLine();


            if (consoleInput.equals("0")) {
                System.out.println(menu);
                return;
            }

            if (validateId(consoleInput)) {
                Long id = Long.parseLong(consoleInput);
                developer = developerRepository.getById(id);

            } else {
                System.out.println("Ввод id не коректен");
            }

            if (developer != null) {

                System.out.println("Dev id = " + developer.getId() + " : " + developer.getFirstName() + " " +
                        developer.getLastName() + " will be updated");
                System.out.println("Введите через пробел новые данные девелопера: " + "" +
                        "\n имя фамилия специализация.");

                String line = consoleReader.readLine();
                String[] consoleData = line.split(" ");

                if(validateConsoleInput(consoleData)) {
                    // id не меняем
                    String fName = consoleData[0];
                    String lName = consoleData[1];
                    String spec = consoleData[2];
                    developer.setFirstName(fName);
                    developer.setLastName(lName);
                    developer.setSpecialisation(spec);

                    Set<Skill> skillSet = getSkills(consoleReader);
                    Account account = getAccount(consoleReader);


                    developer.setSkills(skillSet);
                    developer.setAccount(account);

                    developerRepository.update(developer);

                    System.out.println("Developer ID = " + developer.getId() + " is updated");

                } else {
                    System.out.println("Данные введены неверно. Try again.");
                }
                System.out.println();

            }
        }
    }

    public void delete(BufferedReader consoleReader, String menu) throws IOException {

        while (true) {
            System.out.println("Введите id девелопера, 0 - выход в меню девов");
            String id = consoleReader.readLine();

            if (id.equals("0")){
                System.out.println(menu);
                return;
            }

            if (validateId(id)) {
                Developer dev = developerRepository.getById(Long.parseLong(id));
                if (dev != null) {
                    System.out.println("Девелопер с id = " + dev.getId() + " " + dev.getFirstName() + " " +
                            dev.getLastName() + " удален");
                    developerRepository.delete(dev.getId());
                }
            } else {
                System.out.println("Введен неверный формат id");
            }
        }
    }

    public void getAll(String menu) {
        System.out.println("dev_id  devFirstName DevLastName  DevSpec");
        developerRepository.getAll().stream().forEach(dev -> System.out.println(dev.getId() + " " +
                dev.getFirstName() + " " + dev.getLastName() + " " + dev.getSpecialisation()));
        System.out.println();
        System.out.println(menu);
    }
}
