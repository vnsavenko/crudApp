package repository.io;

import controller.IOProvider;
import model.Account;
import model.Skill;
import repository.AccountRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaIOAccountRepositoryImpl implements AccountRepository {

    public static final String FILE_NAME = "accounts.txt";
    private static final int NUMBER_OF_FIELDS_IN_ACCOUNT = 3;

    private BufferedWriter getWriter(boolean append) throws IOException {
        return IOProvider.getWriter(FILE_NAME, append);
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return IOProvider.getReader(FILE_NAME);
    }

    private List<String> readToArrayList() {
        String line;
        List<String> list = new ArrayList<>();
        try(BufferedReader reader = getReader()) {
            while(reader.ready())
            {
                line = reader.readLine(); //читаем строку
                if (validateLine(line)) {
                    list.add(line);  // добавляем непустую запись в arrayList
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

    private boolean validateToken(String token) {
        return !token.equals("");
    }

    private boolean validateLine(String line) {

        String[] words = line.split(",");
        try {
            boolean isValid = (words.length==NUMBER_OF_FIELDS_IN_ACCOUNT)&validateId(words[0]);
            for (int i = 1; i < words.length; i++) {
                isValid &= validateToken(words[i]);
            }
            return isValid;
        } catch (Exception e) {
            return false;
        }
    }




    public void save(Account account) {
        try (BufferedWriter writer = getWriter(true))
        {
            String skillLine = "" + account.getId() + "," + account.getAccountData() + "," + account.getStatus().name();
            writer.newLine();
            writer.write(skillLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Account account) {

        Long accountId;
        boolean match = false;
        List<String> list = readToArrayList();//получили ArrayList из файла

        try(BufferedWriter writer = getWriter(false)){
            for (String str : list
            ) {
                accountId = Long.parseLong(str.substring(0, str.indexOf(',')));// вытаскиваем из строки id
                writer.newLine();
                if (accountId == account.getId()){ // если id совпадает - перезаписываем
                    writer.write(""+ account.getId() + "," + account.getAccountData() + "," + account.getStatus());
                    match = true;
                } else writer.write(str); // если id не совпадает - записываем старую строку
            }

            if(!match) {
                System.out.println("Нет объекта с таким Id: " + account.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void delete(Long id) {

        Long accountId;

        List<String> list = readToArrayList(); // получаем ArrayList из файла

        //здесь будем проходить по элементам arrayList, парсить id в строках, в случае несовпадения c удаляемым id
        //будем отправлять строку на запись в файл

        try(BufferedWriter writer = getWriter(false)) {
            for(String str : list) {
                accountId = Long.parseLong(str.substring(0, str.indexOf(',')));// вытаскиваем из строки id
                if (!id.equals(accountId)) {
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

    public Account getById(Long id) {
        Account account = null;
        String line;
        Long accountId;
        String accountData;
        String accountStatus;

        try(BufferedReader reader = getReader()){

            while (reader.ready()) {
                line = reader.readLine();
                if (validateLine(line)) {
                    String[] accountFields = line.split(",");

                    accountId = Long.parseLong(accountFields[0]);
                    accountData = accountFields[1];
                    accountStatus = accountFields[2];

                    if (id.equals(accountId)) {
                        account = new Account();
                        account.setId(accountId);
                        account.setAccountData(accountData);
                        account.setStatus(accountStatus);
                    }
                }
            }

            if (account==null) {
                System.out.println("Нет объекта с id = " + id);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;

    }

    public List<Account> getAll() {


        List<Account> accounts = new ArrayList<>();
        String line;
        Long accountId;
        String accountData;
        String accountStatus;
        Account account;

        try(BufferedReader reader = getReader()){

            while (reader.ready()) {
                line = reader.readLine();
                if (validateLine(line)) {

                    String[] accountFileds = line.split(",");

                    accountId = Long.parseLong(accountFileds[0]);
                    accountData = accountFileds[1];
                    accountStatus = accountFileds[2];

                    account = new Account();
                    account.setId(accountId);
                    account.setAccountData(accountData);
                    account.setStatus(accountStatus);
                    accounts.add(account);

                }
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accounts;


    }
}
