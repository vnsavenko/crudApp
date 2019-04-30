package src.main.java.com.savenko.crudapp.repository.io;

import src.main.java.com.savenko.crudapp.model.Skill;
import src.main.java.com.savenko.crudapp.repository.SkillRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JavaIOSkillRepositoryImpl implements SkillRepository {

    public static final String FILE_NAME = "./src/main/resources/files/skills.txt";

    private BufferedWriter getWriter(boolean append) throws IOException {
        return IOProvider.getWriter(FILE_NAME, append);
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return IOProvider.getReader(FILE_NAME);
    }


        private List<String> readToArrayList() {
        String skillLine;
        List<String> list = new ArrayList<>();
        try(BufferedReader reader = getReader()) {
            while(reader.ready())
            {
                skillLine = reader.readLine(); //читаем строку
                if (validateLine(skillLine)) {
                    list.add(skillLine);  // добавляем непустую запись в arrayList
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
        } catch (Exception e){
            return false;
        }
    }

    private boolean validateSkill(String skillName) {
        return !skillName.equals("");
    }

    private boolean validateLine(String line) {

        String[] words = line.split(",");
        try{
            return validateId(words[0])&&validateSkill(words[1]);}
        catch (Exception e) {
            return false;
        }
    }

    public void save(Skill skill) {

        try (BufferedWriter writer = getWriter(true))
        {
            String skillLine = "" + skill.getId() + "," + skill.getName();
            writer.newLine();
            writer.write(skillLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Skill skill) {

        Long skillId;
        boolean isUpdated = false;
        List<String> list = readToArrayList();//получили ArrayList строк из файла

        try(BufferedWriter writer = getWriter(false)){
            for (String str : list
                 ) {
                skillId = Long.parseLong(str.substring(0, str.indexOf(',')));// вытаскиваем из строки id
                writer.newLine();
                if (skillId == skill.getId()){
                    writer.write(""+ skill.getId() + "," + skill.getName());
                   isUpdated = true;
                } else writer.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isUpdated) {
            System.out.println("Нет скилла с таким id: " + skill.getId());
        } else {
            System.out.println("Cкилл c id: " + skill.getId() + "обновлен");
        }


    }

    public void delete(Long id) {
        Long skillId;

        List<String> list = readToArrayList(); // получаем ArrayList из файла

        //здесь будем проходить по элементам arrayList, парсить id в строках, в случае несовпадения c удаляемым id
        //будем отправлять строку на запись в файл

        try(BufferedWriter writer = getWriter(false)) {
             for(String str : list) {
                    skillId = Long.parseLong(str.substring(0, str.indexOf(',')));// вытаскиваем из строки id
                    if (!id.equals(skillId)) {
                        writer.newLine();
                        writer.write(str);
                    }
               }
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Skill getById(Long id){
        Skill skill = null;
        String skillLine;
        Long skillId;
        String skillName;

        try(BufferedReader reader = getReader()){

            while (reader.ready()) {
                skillLine = reader.readLine();
               if (validateLine(skillLine)) {
                    skillId = Long.parseLong(skillLine.substring(0, skillLine.indexOf(',')));
                    skillName = skillLine.substring(skillLine.indexOf(',')+1);
                    if (id.equals(skillId)) {
                        skill = new Skill();
                        skill.setId(skillId);
                        skill.setName(skillName);
                    }
               }
            }

            if (skill==null) {
                System.out.println("Нет объекта с id = " + id);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     return skill;
    }

    public List<Skill> getAll() {
        List<Skill> skills = new ArrayList<>();
        String skillLine;
        Long skillId;
        String skillName;
        Skill skill;

        try(BufferedReader reader = getReader()){

            while (reader.ready()) {
                skillLine = reader.readLine();
                if (validateLine(skillLine)) {
                    skillId = Long.parseLong(skillLine.substring(0, skillLine.indexOf(',')));
                    skillName = skillLine.substring(skillLine.indexOf(',')+1);

                    skill = new Skill();
                    skill.setId(skillId);
                    skill.setName(skillName);

                    skills.add(skill);

                    }
                }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return skills;
    }
}
