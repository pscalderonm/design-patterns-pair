package dao.impl;

import config.DatabaseConnection;
import dao.UserDao;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static Connection con;

    public UserDaoImpl(Connection connection) throws SQLException {
        con = connection;
    }

    @Override
    public void add(User usr) throws SQLException {
        System.out.println("insert user: "+ usr.toString());
        String query = "insert into users(usr_username, usr_password) values (?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, usr.getUsername());
        ps.setString(2, usr.getPassword());
        ps.executeUpdate();
    }

    @Override
    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        User user;
        String query = "select * from users";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet response = ps.executeQuery();

        while (response.next()){
            user = new User(response.getInt(1),response.getString(2),response.getString(3));
            users.add(user);
        }
        return users;
    }
}
