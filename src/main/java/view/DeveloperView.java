package view;

import controller.DeveloperController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeveloperView {


    public static final String MENU_DEVELOPER = "*** В главном меню девелоперов ***" +
            "\n1. Добавить developer;" +
            "\n2. Получить developer" +
            "\n3. Редактировать developer" +
            "\n4. Удалить developer" +
            "\n5. Вывести список developer" +
            "\n0. Выйти из меню";


    private BufferedReader consoleReader;

    private DeveloperController developerController;

    public DeveloperView() {
       // System.out.println(MENU_DEVELOPER);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        developerController = new DeveloperController();
    }

    public void processDeveloperFromConsole() throws IOException {
        System.out.println(MENU_DEVELOPER);
        while (true) {
            String messageFromConsole = consoleReader.readLine();


                switch (messageFromConsole) {
                    case "1":
                        developerController.save(consoleReader, MENU_DEVELOPER);
                        break;
                    case "2":
                        developerController.getById(consoleReader, MENU_DEVELOPER);
                        break;
                    case "3":
                        developerController.update(consoleReader, MENU_DEVELOPER);
                        break;
                    case "4":
                        developerController.delete(consoleReader, MENU_DEVELOPER);
                        break;
                    case "5":
                        developerController.getAll(MENU_DEVELOPER);
                        break;
                    case "0":
                        //case "00":
                       // consoleReader.close();
                       return;

                    default:
                        System.out.println("Введено неверное число (0 - 5), попробуйте еще.");
                }


        }
    }
}
