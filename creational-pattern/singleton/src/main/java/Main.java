import config.DatabaseConnection;
import config.ScriptConfig;
import dao.impl.UserDaoImpl;
import model.User;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        //create users
        User userB = new User("bvega","bvega.123");
        User userP = new User("pcalderon","pcalderon.123");

        //create databse connection with sigleton pattern and test with  two instance with different values
        System.out.println("====================================================== CREATE DATABASE CONNECTION ======================================================");
        DatabaseConnection db = DatabaseConnection.getInstance("test","test","test.123");
        DatabaseConnection db1 = DatabaseConnection.getInstance("test","bad","bad.123");
        System.out.println(db.toString());
        System.out.println(db1.toString());

        //run script init.sql
        System.out.println("====================================================== RUN SCRIPT init.sql ======================================================");
        ScriptConfig script = new ScriptConfig("src/main/resources/sql/init.sql",db);
        script.runScript();

        // implement user dao with the first instance
        UserDaoImpl userDao = new UserDaoImpl(db.getConnection());

        //persist users
        System.out.println("====================================================== PERSIST USERS ======================================================");
        userDao.add(userB);
        userDao.add(userP);

        // implement user dao with the second instance
        userDao = new UserDaoImpl(db1.getConnection());

        //get users
        System.out.println("====================================================== GET USERS ======================================================");
        System.out.println(userDao.getUsers());
    }
}
