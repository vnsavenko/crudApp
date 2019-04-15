package controller;

import service.DeveloperServiceImpl;
import model.Developer;

import java.util.List;

public class DeveloperControllerImpl {
//обработка запроса пользователя

    private DeveloperServiceImpl developerService;

    public DeveloperControllerImpl() {
        developerService = new DeveloperServiceImpl();
    }

    public List<Developer> getAllDevelopers(){

        return developerService.getAllDevelopers();
    }

}
