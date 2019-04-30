package src.main.java.com.savenko.crudapp.view;

import src.main.java.com.savenko.crudapp.controller.SkillController;

import java.io.*;

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
      //  System.out.println(MENU_SKILLS);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        skillController = new SkillController();
    }

    public void processSkillFromConsole() throws IOException {
        System.out.println(MENU_SKILLS);
        while (true) {
            String messageFromConsole = consoleReader.readLine();
                int menuItem = Integer.parseInt(messageFromConsole);
                switch (menuItem) {
                    case 1:
                        skillController.save(consoleReader,MENU_SKILLS);
                        break;
                    case 2:
                        skillController.getById(consoleReader,MENU_SKILLS);
                        break;
                    case 3:
                        skillController.update(consoleReader,MENU_SKILLS);
                        break;
                    case 4:
                        skillController.delete(consoleReader,MENU_SKILLS);
                        break;
                    case 5:
                        skillController.getAll();
                        break;
                    case 0:
                        return;

                    default:
                        System.out.println("Введено неверное число (0 - 5), попробуйте еще.");
                }
        }
    }



}
