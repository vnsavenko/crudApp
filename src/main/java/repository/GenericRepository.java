package repository;

import java.util.List;

public interface GenericRepository<T, ID>{

    void save(T t);

    void update(ID id);

    void delete(ID id);

    T getById(ID id);

    List<T> getAll();


}
