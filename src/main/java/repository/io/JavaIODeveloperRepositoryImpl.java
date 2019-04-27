package repository.io;

import model.Account;
import model.Developer;
import model.Skill;
import repository.DeveloperRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JavaIODeveloperRepositoryImpl implements DeveloperRepository {

    public static final String FILE_NAME = "developers.txt";
    private static final int NUMBER_OF_FIELDS_IN_DEVELOPER = Developer.class.getDeclaredFields().length;

    private BufferedWriter getWriter(boolean append) throws IOException {
        return IOProvider.getWriter(FILE_NAME, append);
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return IOProvider.getReader(FILE_NAME);
    }

    private List<String> readToArrayList() {
        String line;
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = getReader()) {
            while (reader.ready()) {
                line = reader.readLine(); //читаем строку
                if (validateLine(line)) {
                    list.add(line);  // добавляем непустую запись в arrayList
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    private boolean validateId(String idString) {
        try {
            Long.parseLong(idString); // если из строки можно выделить число, то true
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateToken(String token) {
        return !token.equals("");
    }

    private boolean validateLine(String line) {

        String[] words = line.split(",");
        try {
            boolean isValid = (words.length == NUMBER_OF_FIELDS_IN_DEVELOPER) & validateId(words[0]);
            for (int i = 1; i < words.length; i++) {
                isValid &= validateToken(words[i]);
            }
            return isValid;
        } catch (Exception e) {
            return false;
        }
    }

    //метод для получения Set of skills для конкретного дева из файла, по списку, содержащему ids of skills
    // (developerField)
    private Set<Skill> getDevsSetOfSkills(String developerField) {
        Set<Skill> devSkills = new LinkedHashSet<>();
        JavaIOSkillRepositoryImpl skillRepository = new JavaIOSkillRepositoryImpl();
        Skill skill;
        String skillsLine = developerField.substring(developerField.indexOf('{') + 1, developerField.indexOf('}') - 1);
        String[] skillIds = skillsLine.split(":");
        for (String skl : skillIds
        ) {
            Long Id = Long.parseLong(skl);
            skill = skillRepository.getById(Id);
            devSkills.add(skill);
        }
        return devSkills;
    }

    //метод для записи в файл, режим записи будет зависеть от writer  - с добавлением в конец или с перезаписью

    private void reWrite(Developer developer, BufferedWriter writer) throws IOException {

            String skillsIds = "";

            Set<Skill> skills = developer.getSkills();
            for (Skill skill : skills
            ) {
                skillsIds = skillsIds + skill.getId() + ":";
            }
            skillsIds = "{" + skillsIds + "}";

            String developerLine = "" + developer.getId() + "," + developer.getFirstName() + "," + developer.getLastName() +
                    "," + developer.getSpecialisation() + "," + skillsIds + "," +
                    developer.getAccount().getId();


            writer.newLine();
            writer.write(developerLine);
       }

    public void save(Developer developer) {

        try (BufferedWriter writer = getWriter(true)) {

     reWrite(developer, writer);// будут добавляться в конец

        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    public void update(Developer developer) {
        List<Developer> developers = getAll();
        try (BufferedWriter writer = getWriter(false)) { // перезапись всего файла

            for (Developer dev : developers
            ) {
                if ((dev.getId() == developer.getId())) {
                    reWrite(developer, writer); //перезаписываем developer
                } else {
                    reWrite(dev, writer); // записываем прежнего dev
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        List<Developer> developers = getAll();

        try (BufferedWriter writer = getWriter(false)) {//перезапись всего файла
            for (Developer dev : developers
            ) {
                if (dev.getId() != id) {
                    reWrite(dev, writer);//перезапись всех элементов в файл, кроме элем. с переданным в метод id
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Developer getById(Long id) {

        JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl();
        Developer developer = null;
        String line;
        Long devId;
        String firstName;
        String lastName;
        String specialization;
        Set<Skill> skills;
        Account account;

        try (BufferedReader reader = getReader()) {

            while (reader.ready()) {
                line = reader.readLine();
                if (validateLine(line)) {
                    String[] developerFields = line.split(",");

                    devId = Long.parseLong(developerFields[0]); // вытаскиваем значения полей из строк файла
                    firstName = developerFields[1];
                    lastName = developerFields[2];
                    specialization = developerFields[3];
                    skills = getDevsSetOfSkills(developerFields[4]);
                    account = accountRepository.getById(Long.parseLong(developerFields[5]));


                    if (id.equals(devId)) {
                        developer = new Developer();
                        developer.setId(devId);
                        developer.setFirstName(firstName);
                        developer.setLastName(lastName);
                        developer.setSpecialisation(specialization);
                        developer.setSkills(skills);
                        developer.setAccount(account);
                    }
                }
            }

            if (developer == null) {
                System.out.println("Нет объекта с id = " + id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return developer;

    }


    public List<Developer> getAll() {


        List<Developer> developers = new ArrayList<>();
        Developer developer;
        List<Long> ids = new ArrayList<>();

        //вытаскиваем коллекцию ids из файла
        try (BufferedReader reader = getReader()) {

            while (reader.ready()) {
                String line = reader.readLine();
                if (validateLine(line)) {
                    String[] fields = line.split(",");

                    Long devId = Long.parseLong(fields[0]);
                    ids.add(devId);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //вытаскиваем коллекцию developers по коллекции ids
        ids.stream().forEach(id->developers.add(getById(id)));

        return developers;
   }


}
