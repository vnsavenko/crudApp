package repoTests;

import model.Account;
import model.AccountStatus;
import repository.io.JavaIOAccountRepositoryImpl;

import java.util.List;

public class AccountRepoTest {
    public static void main(String[] args) {
        JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl();

//        Account account1 = new Account(1L, "123456", AccountStatus.ACTIVE);
//        Account account2 = new Account(2L, "789-455", AccountStatus.BANNED);
//        Account account3 = new Account(3L, "111-455", AccountStatus.DELETED);
//
            // Записываем элементы
//        accountRepository.save(account1);
//        accountRepository.save(account2);
//        accountRepository.save(account3);


        printAllAccounts(accountRepository);

        // удаляем элемент с Id = 2L
        accountRepository.delete(2L);

        printAllAccounts(accountRepository);

        // getById
        Account account = accountRepository.getById(3L);

        System.out.println(account.toString());
        System.out.println();

        printAllAccounts(accountRepository);

        //update (Account account)
        Account updatedAccount = new Account(3L, "AAAAAA", AccountStatus.ACTIVE);

        accountRepository.update(updatedAccount);

        printAllAccounts(accountRepository);

        Account updatedAccount1 = new Account(10L, "AA", AccountStatus.ACTIVE);

        accountRepository.update(updatedAccount1);

        printAllAccounts(accountRepository);


    }

    static void printAllAccounts(JavaIOAccountRepositoryImpl accountRepository) {

        //Возвращаем List с Accounts
    List<Account> accounts = accountRepository.getAll();
        accounts.stream().
    forEach(account ->System.out.println(account));
        System.out.println();
}
}
