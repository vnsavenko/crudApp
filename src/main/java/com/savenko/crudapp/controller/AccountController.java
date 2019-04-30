package src.main.java.com.savenko.crudapp.controller;

import src.main.java.com.savenko.crudapp.model.Account;
import src.main.java.com.savenko.crudapp.repository.AccountRepository;
import src.main.java.com.savenko.crudapp.repository.io.IOProvider;
import src.main.java.com.savenko.crudapp.repository.io.JavaIOAccountRepositoryImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.com.savenko.crudapp.repository.io.JavaIOAccountRepositoryImpl.FILE_NAME;

public class AccountController {

    public AccountController() {
        accountRepository = new JavaIOAccountRepositoryImpl();
    }

    private AccountRepository accountRepository;

    public boolean validateId(String message) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher m = pattern.matcher(message);
        return m.matches();
    }


    private boolean validateConsoleInput(String[] words) {
       try {
           boolean validLength = (words.length == 2);
           boolean validStatus = validateStatus(words[1]);
           boolean validAccountData = !words[0].equals("");
           boolean istrue = validLength & validAccountData & validStatus;
           return istrue;
       } catch (Exception e){
       return false;}
    }


    private boolean validUpdateInput(String[] input){

        try{
            return validateId(input[0]) & validateConsoleInput(new String[]{input[1], input[2]});
    } catch (Exception e) {
            return false;
        }



    }

    private boolean validateStatus(String word) {
        switch (word){
            case "1":
            case "2":
            case "3":
            case " 1":
            case " 2":
            case " 3":
                return true;
                default:
                    return false;
        }
    }

    //idAutoIncrement
    private Long getNextId() {
        Long id ;
        Long max = 0L;
        try (BufferedReader reader = IOProvider.getReader(FILE_NAME)) {

            while (reader.ready()) {
                String line = reader.readLine();
                if (line.split(",").length ==3) {

                    id = Long.parseLong(line.substring(0, line.indexOf(',')));
                    if (id > max) {
                        max = id;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (max + 1);
    }

    private String getStatusFromConsole(String consoleData) {
        String status="";
        if (!consoleData.equals(""))
        {
            switch (consoleData){
                case "1":
                case " 1":
                    status = "ACTIVE";
                    break;
                case "2":
                case " 2":
                    status = "BANNED";
                    break;
                case "3":
                case " 3":
                    status = "DELETED";
                    break;
                default:
                        System.out.println("Введен неверный статус.");
                }
        }

        return status;
    }

    public void save(BufferedReader consoleReader, String menu) throws IOException {

        while (true) {
            System.out.println("Введите через запятую данные аккаунта, статус аккаунта (1 - ACTIVE, 2 - BANNED, 3 - DELETED)" +
                    "\n 0 - выход в меню аккаунтов");

            String line = consoleReader.readLine();
            if (line.equals("0")) {
                System.out.println(menu);
                return;
            }

            String[] consoleData = line.split(",");

            if (validateConsoleInput(consoleData)) {

                Account account = new Account();
                Long id = getNextId();
                String accountData = consoleData[0];
                String accountStatus = getStatusFromConsole(consoleData[1]);
                account.setId(id);
                account.setAccountData(accountData);
                account.setStatus(accountStatus);
                accountRepository.save(account);
                System.out.println("Аккаунт c id = " + id + " добавлен в хранилище");
            } else {
                System.out.println("Данные введены неверно. Try again.");
            }
        }
    }

    public void getById(BufferedReader consoleReader, String menu) throws IOException {
        while (true) {
            System.out.println("Введите id аккаунта, 0 - выход в меню аккаунтов");
            String consoleInput = consoleReader.readLine();

            if (consoleInput.equals("0")){
                System.out.println(menu);
                return;
            }

            if (validateId(consoleInput)) {
                Account account= accountRepository.getById(Long.parseLong(consoleInput));
                if (account != null) {
                    System.out.println("Аккаунт с id = " + account.getId() + " : " + account.getAccountData() + " " +
                            account.getStatus());
                }
            } else {
                System.out.println("Введен неверный формат id");
            }
        }
    }

    public void update(BufferedReader consoleReader, String menu) throws IOException {
        while (true) {
            System.out.println("Введите через запятую: id,новые_данные,статус_аккаунта(1 - ACTIVE, 2 - BANNED, 3 - DELETED)" +
                    "\n0 - выход в меню аккаунта");
            String consoleInput = consoleReader.readLine();
            String consoleData[] = consoleInput.split(",");

            if (consoleInput.equals("0")){
                System.out.println(menu);
                return;
            }

            if (validUpdateInput(consoleData))
            {
                Account account = new Account();
                Long id = Long.parseLong(consoleData[0]);
                String accountData = consoleData[1];
                String accountStatus = getStatusFromConsole(consoleData[2]);
                account.setId(id);
                account.setAccountData(accountData);
                account.setStatus(accountStatus);
                accountRepository.update(account);
                System.out.println("Аккаунт c id = " + id + " изменен");

            } else {
                System.out.println("Ввод не коректен, необходимо: id,data,status");
            }
        }
    }

    public void delete(BufferedReader consoleReader, String menu) throws IOException {
        while (true) {
            System.out.println("Введите id аккаунта, 0 - выход в меню скилов");
            String id = consoleReader.readLine();

            if (id.equals("0")){
                System.out.println(menu);

                return;
            }

            if (validateId(id)) {
                Account account = accountRepository.getById(Long.parseLong(id));
                if (account != null) {
                    System.out.println("Скилл с id = " + account.getId() + " удален");
                    accountRepository.delete(account.getId());
                }
            } else {
                System.out.println("Введен неверный формат id");
            }
        }
    }

    public void getAll() {
        System.out.println("account_id  accont_data account_status");
        accountRepository.getAll().stream().forEach(account -> System.out.println(account.getId() + " " +
                account.getAccountData() + " " + account.getStatus().name()));

    }

}
