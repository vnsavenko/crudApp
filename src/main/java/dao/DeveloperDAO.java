package dao;

import model.Developer;

public interface DeveloperDAO {

   public void addDeveloper(Developer developer);

   public void updateDeveloper(int id);

   public void removeDeveloper(int id);

   public Developer getDeveloperById(int id);



}
