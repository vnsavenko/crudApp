package src.main.java.com.savenko.crudapp.repository;

import java.util.List;

public interface GenericRepository<T, ID>{

    void save(T t);

    void update(T t);

    void delete(ID id);

    T getById(ID id);

    List<T> getAll();


}
