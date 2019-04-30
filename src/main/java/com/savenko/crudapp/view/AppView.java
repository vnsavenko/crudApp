package src.main.java.com.savenko.crudapp.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppView {
    public static final String MENU= "*** В главном меню ***" +
            "\n1. Process developers;" +
            "\n2. Process accounts;" +
            "\n3. Process skills;" +
            "\n0. Выйти из программы";


    DeveloperView developerView;
    AccountView accountView;
    SkillView skillView;
    private BufferedReader consoleReader;

    public AppView() {
        //System.out.println(MENU);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        developerView = new DeveloperView();
        accountView = new AccountView();
        skillView = new SkillView();
    }


    public void process() throws IOException {
        while (true) {
            System.out.println(MENU);
            String messageFromConsole = consoleReader.readLine();
                switch (messageFromConsole) {
                    case "1":
                        developerView.processDeveloperFromConsole();
                        break;
                    case "2":
                        accountView.processAccountFromConsole();
                        break;
                    case "3":
                        skillView.processSkillFromConsole();
                        break;
                    case "0":
                        //case "00":
                        consoleReader.close();
                        System.exit(0);

                    default:
                        System.out.println("Введено неверное число (0 - 3), попробуйте еще.");
                }


        }
    }
    }

