package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void add(User usr) throws SQLException;
    List<User> getUsers() throws SQLException;
}
