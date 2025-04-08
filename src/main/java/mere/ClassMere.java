package mere;

import java.sql.Connection;
import java.util.List;

public abstract class ClassMere {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract List<? extends ClassMere> getAll() throws Exception;

    public abstract ClassMere findById(int id) throws Exception;

    public abstract void save() throws Exception;

    public abstract void update() throws Exception;

    public abstract void delete() throws Exception;

    public abstract List<? extends ClassMere> getAllWithPagination(int index, int count) throws Exception;

    public abstract List<? extends ClassMere> getAll(Connection connection) throws Exception;

    public abstract ClassMere findById(Connection connection, int id) throws Exception;

    public abstract void save(Connection connection) throws Exception;

    public abstract void update(Connection connection) throws Exception;

    public abstract void delete(Connection connection) throws Exception;

    public abstract List<? extends ClassMere> getAllWithPagination(Connection connection ,int index, int count) throws Exception;
}