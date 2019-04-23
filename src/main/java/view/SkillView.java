package view;

import controller.SkillController;
import model.Skill;
import repository.io.IOProvider;
import repository.io.JavaIOSkillRepositoryImpl;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static repository.io.JavaIOSkillRepositoryImpl.FILE_NAME;

public class SkillView {

    public static final String MENU_SKILLS = "*** В главном меню скилов ***" +
            "\n1. Добавить skill;" +
            "\n2. Получить скилл" +
            "\n3. Редактировать скилл" +
            "\n4. Удалить скилл" +
            "\n5. Вывести список скилов" +
            "\n0. Выйти из меню";



    private BufferedReader consoleReader;
    private SkillController skillController;

    public SkillView() {
        System.out.println(MENU_SKILLS);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        skillController = new SkillController();
    }

    public void processSkillFromConsole() throws IOException {
        while (true) {
            String messageFromConsole = consoleReader.readLine();
            boolean isValid = skillController.validateId(messageFromConsole);
            if (isValid) {
                int menuItem = Integer.parseInt(messageFromConsole);
                switch (menuItem) {
                    case 1:
                        skillController.addSkill(consoleReader,MENU_SKILLS);
                        break;
                    case 2:
                        skillController.readSkill(consoleReader,MENU_SKILLS);
                        break;
                    case 3:
                        skillController.updateSkill(consoleReader,MENU_SKILLS);
                        break;
                    case 4:
                        skillController.deleteSkill(consoleReader,MENU_SKILLS);
                        break;
                    case 5:
                        skillController.readAllSkills();
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



}
