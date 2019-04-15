package cervice;

import dao.DeveloperDAOImpl;

import java.util.List;

public class DeveloperServiceImpl {

    public DeveloperServiceImpl() {
        developerDAO = new DeveloperDAOImpl();
    }

    private DeveloperDAOImpl developerDAO;

    public List getAllDevelopers(){

        // может быть еще какая  -  то логика
        List list = developerDAO.getDevelopers();
        System.out.println("Получить List of Developers: " + list.toString());
        return list;
    }
}
