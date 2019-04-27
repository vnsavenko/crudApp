package view;

import controller.AccountController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AccountView {

    public static final String MENU_ACCOUNTS = "*** В главном меню аккаунтов ***" +
            "\n1. Добавить account;" +
            "\n2. Получить account" +
            "\n3. Редактировать account" +
            "\n4. Удалить account" +
            "\n5. Вывести список account" +
            "\n0. Выйти из меню";



    private BufferedReader consoleReader;

    private AccountController accountController;

    public AccountView() {
       // System.out.println(MENU_ACCOUNTS);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        accountController = new AccountController();
    }

    public void processAccountFromConsole() throws IOException {
        System.out.println(MENU_ACCOUNTS);
        while (true) {
            String messageFromConsole = consoleReader.readLine();
                switch (messageFromConsole) {
                    case "1":
                        accountController.save(consoleReader, MENU_ACCOUNTS);
                        break;
                    case "2":
                        accountController.getById(consoleReader, MENU_ACCOUNTS);
                        break;
                    case "3":
                        accountController.update(consoleReader, MENU_ACCOUNTS);
                        break;
                    case "4":
                        accountController.delete(consoleReader, MENU_ACCOUNTS);
                        break;
                    case "5":
                        accountController.getAll();
                       break;
                    case "0":
                       return;
                    default:
                        System.out.println("Введено неверное число (0 - 5), попробуйте еще.");
                }
        }
    }
}
