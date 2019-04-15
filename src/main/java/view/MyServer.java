package view;

import controller.DeveloperControllerImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyServer {
    DeveloperControllerImpl developerController = new DeveloperControllerImpl();

    public void process() throws Exception{

        System.out.println("Введите операцию: get - получить список девов, 'x' - выход" );
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        do {
            String s = reader.readLine();

            if ("get".equals(s)) {
                developerController.getAllDevelopers();

            } else if ("x".equals(s)){
                System.out.println("Выход");
                reader.close();
                System.exit(0);
            } else System.out.println("Введите что нибудь из предложенного");


        } while (true);



    }
}
