package view;

import controller.AccountController;
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
        System.out.println(MENU_ACCOUNTS);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        accountController = new AccountController();
    }

    public void processAccountFromConsole() throws IOException {
        while (true) {
            String messageFromConsole = consoleReader.readLine();
            boolean isValid = accountController.validateId(messageFromConsole);
            if (isValid) {

                switch (messageFromConsole) {
                    case "1":
                        accountController.addAccount(consoleReader, MENU_ACCOUNTS);
                        break;
                    case "2":
                        accountController.readAccount(consoleReader, MENU_ACCOUNTS);
                        break;
                    case "3":
                        accountController.updateAccount(consoleReader, MENU_ACCOUNTS);
                        break;
                    case "4":
                        accountController.deleteAccount(consoleReader, MENU_ACCOUNTS);
                        break;
                    case "5":
                        accountController.readAllAccounts();
                       break;
                    case "0":
                    //case "00":
                        consoleReader.close();
                        System.exit(0);

                    default:
                        System.out.println("Введено неверное число (0 - 5), попробуйте еще.");
                }
            } else System.out.println("Введено не число, попробуйте еще.");

        }
    }



}
