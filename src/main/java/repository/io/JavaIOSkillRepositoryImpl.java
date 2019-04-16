package repository.io;

import controller.IOProvider;
import model.Skill;
import repository.SkillRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JavaIOSkillRepositoryImpl implements SkillRepository {

    public static final String FILE_NAME = "skills.txt";

    private BufferedWriter getWriter(boolean append) throws IOException {
        return IOProvider.getWriter(FILE_NAME, append);
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return IOProvider.getReader(FILE_NAME);
    }

    private List<String> readToArrayList() {
        String s;
        List<String> list = new ArrayList<>();
        try(BufferedReader reader = getReader()) {
            while(reader.ready())
            {
                s = reader.readLine(); //читаем строку
                if (!s.equals("")) {
                    list.add(s);  // добавляем непустую запись в arrayList
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void save(Skill skill) {

        try (BufferedWriter writer = getWriter(true))
        {
            String s = skill.toString();
            writer.newLine();
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Long id) {



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
        String s;
        Long skillId;
        String skillName;

        try(BufferedReader reader = getReader()){

            while (reader.ready()) {
                s = reader.readLine();
               if (!s.equals("")) {
                    skillId = Long.parseLong(s.substring(0, s.indexOf(',')));
                    skillName = s.substring(s.indexOf(',')+1);
                    if (id.equals(skillId)) {
                        skill = new Skill();
                        skill.setId(skillId);
                        skill.setName(skillName);
                    }
               }
            }

            if (skill==null) System.out.println("Нет объекта с id = " + id);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     return skill;
    }

    public List<Skill> getAll() {
        List<Skill> skills = new ArrayList<>();
        String s;
        Long skillId;
        String skillName;
        Skill skill;

        try(BufferedReader reader = getReader()){

            while (reader.ready()) {
                s = reader.readLine();
                if (!s.equals("")) {
                    skillId = Long.parseLong(s.substring(0, s.indexOf(',')));
                    skillName = s.substring(s.indexOf(',')+1);

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
