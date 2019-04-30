package src.main.java.com.savenko.crudapp;

import src.main.java.com.savenko.crudapp.view.AppView;

import java.io.IOException;

public class CrudApp {
    public static void main(String[] args) throws IOException {
        AppView view = new AppView();
        view.process();

    }
}
