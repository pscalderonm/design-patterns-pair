import config.DatabaseConnection;
import dao.impl.UserDaoImpl;
import model.User;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        //create users
        User userB = new User("bvega","bvega.123");
        User userP = new User("pcalderon","pcalderon.123");

        //create databse connection with sigleton pattern and test with  two instance with different values
        System.out.println("Create database Connection");
        DatabaseConnection db = DatabaseConnection.getInstance("test","test","test.123");
        DatabaseConnection db1 = DatabaseConnection.getInstance("test","bad","bad.123");

        //run script init.sql
        System.out.println("Run script init.sql");
        ScriptRunner sr = new ScriptRunner(db.getConnection());
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/init.sql"));
        sr.runScript(reader);

        // implement user dao with the first instance
        UserDaoImpl userDao = new UserDaoImpl(db.getConnection());

        //persist users
        userDao.add(userB);
        userDao.add(userP);

        // implement user dao with the second instance
        userDao = new UserDaoImpl(db1.getConnection());

        //get users
        System.out.println("get users");
        System.out.println(userDao.getUsers());
    }
}
