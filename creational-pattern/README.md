# 

<a>
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/Logo_Universidad_Polit%C3%A9cnica_Salesiana_del_Ecuador.png/800px-Logo_Universidad_Polit%C3%A9cnica_Salesiana_del_Ecuador.png" alt="Ups logo" title="Aimeos" align="right" height="60" />
</a>

# Tarea de Patrones Creacionales

**Integrantes:**

* Bryam David Vega Moreno

* Pablo Sebastian Calderon Maldonado

**Repositorio:** [design-patterns-pair/creational-pattern at main · pscalderonm/design-patterns-pair · GitHub](https://github.com/pscalderonm/design-patterns-pair/tree/main/creational-pattern)

-------------------

## Patron Singleton

> "Usando el patron Singleton. Realizar una aplicacion para que cargue informacion desde una base de datos, a la vez, cuando una propiedad sea actualizada, esta debera reflejarse en la base de datos para que sea cargada cuando reiniciemos la aplicacion."

#### Solucion del problema

**Clase de base de datos**

Esta clase de base de datos contiene el patron singleton, en donde se tiene un metodo que se instancia la clase denominada `DatabaseConnection`.  

El metodo `getInstance` tiene contemplado que en caso de que la instancia sea `null` o que la conexion a la base esta cerrada para que se proceda a instanciar una nueva clase, caso contrario simplemente se usara la clase previamente instanciada.  

A continuacion se presenta la clase de base de datos desarrollada.

```java
public class DatabaseConnection {

    private static DatabaseConnection instance;

    private Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/";

    public String database;
    public String username;
    public String password;

    private DatabaseConnection(String database, String username, String password){
        try{
            this.database = database;
            this.username = username;
            this.password = password;

            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url+this.database,this.username,this.password);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver de conexion no encontrado "+ e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error en la conexion debase de datos: "+ e.getMessage());
        }
    }

    public static DatabaseConnection getInstance(String database, String username, String password) throws SQLException {
        if(instance == null){
            instance = new DatabaseConnection(database, username, password);
        }else if (instance.getConnection().isClosed()){
            instance = new DatabaseConnection(database, username, password);
        }
        return instance;
    }

    @Override
    public String toString() {
        return "DatabaseConnection{" +
                "database='" + database + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Connection getConnection() {
        return connection;
    }

}
```

<div style="page-break-after: always"></div>

**Clase User**

Esta clase contiene el modelo que se va a guardar en la base de datos, no es mas que una sencilla clase que contiene un nombre de usuario y una clave. A continuacion se presenta el codigo de la misma:

```java
public class User {

    private Integer id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
```

<div style="page-break-after: always"></div>

**Clase Dao**

Esta clase contiene las operaciones para guardar y obtener informacion de la base de datos. A continuacion su codigo:

* **UserDao interface**
  
  ```java
  public interface UserDao {
      void add(User usr) throws SQLException;
      List<User> getUsers() throws SQLException;
  }
  ```

* **UserDao implementation**
  
  ```java
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
  ```

<div style="page-break-after: always"></div>

**Clase main**

Dicha clase contiene la prueba que se realiza para demostrar el funcionamiento del patron singleton con la conexion de base de datos, para ello se instanciaron dos conexiones con diferentes datos, una conexion con datos correctos y otra con datos erroneos, sin embargo, notaremos que gracias al patron singleton, la conexion a base utilizara la instancia con la base de datos correcta. 

A continuacion el codigo:

```java
public class Main {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        //create users
        User userB = new User("bvega","bvega.123");
        User userP = new User("pcalderon","pcalderon.123");

        //create databse connection with sigleton pattern and test with  two instance with different values
        System.out.println("=== CREATE DATABASE CONNECTION ===");
        DatabaseConnection db = DatabaseConnection.getInstance("test","test","test.123");
        DatabaseConnection db1 = DatabaseConnection.getInstance("test","bad","bad.123");
        System.out.println(db.toString());
        System.out.println(db1.toString());

        //run script init.sql
        System.out.println("=== RUN SCRIPT init.sql ===");
        ScriptConfig script = new ScriptConfig("src/main/resources/sql/init.sql",db);
        script.runScript();

        // implement user dao with the first instance
        UserDaoImpl userDao = new UserDaoImpl(db.getConnection());

        //persist users
        System.out.println("==== PERSIST USERS ===");
        userDao.add(userB);
        userDao.add(userP);

        // implement user dao with the second instance
        userDao = new UserDaoImpl(db1.getConnection());

        //get users
        System.out.println("==== GET USERS ====");
        System.out.println(userDao.getUsers());
    }
}
```

<div style="page-break-after: always"></div>

**Resultado**

```bash
=== CREATE DATABASE CONNECTION ===
DatabaseConnection{database='test', username='test', password='test.123'}
DatabaseConnection{database='test', username='test', password='test.123'}
=== RUN SCRIPT init.sql ===
DROP TABLE IF EXISTS users

CREATE TABLE users(
                      usr_id serial,
                      usr_username varchar(255),
                      usr_password varchar(255),
                      PRIMARY KEY (usr_id)
)

==== PERSIST USERS ===
insert user: User{id=null, username='bvega', password='bvega.123'}
insert user: User{id=null, username='pcalderon', password='pcalderon.123'}
==== GET USERS ====
[User{id=1, username='bvega', password='bvega.123'}, User{id=2, username='pcalderon', password='pcalderon.123'}]

Process finished with exit code 0

```

## Patron Builder

> "Modifiquemos la clase Employee para agregarle una nueva relación con la clase Department (departamento) la cual será una nueva clase que tendremos que crear, la clase departamento deberá tener el nombre del departamento al que pertenece y finalmente agreguemos la construcción de este objeto al EmployeeBuilder."

#### Solucion del problema

**Clase Department**

```java
public class Department {
    private String name;
    public Department(String name){
        this.name = name;
    }
    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}
    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

<div style="page-break-after: always"></div>

**Clase employee**

Se ajusta la clase Employee agregando la nueva propiedad

```java

private Department department;
... 
public void setDepartment(Department department){ this.department = department; }
public Department getDepartment(){ return this.department; }

@Override
    public String toString() {
        return "Employee{" + "name=" + name + ", age=" + age + ", gender=" 
                + gender + ", \naddress=" + adress + ", \nphones=" + phones
                + ", \ncontacts=" + contacs + "\ndepartment=" + department +  '}';
    }
```

Se ajusta la clase **employeeBuilder** que esta dentro de la clase Employee

```java
private Department department;

public EmployeeBuilder addDepartment(String departmentName){
            department = new Department(departmentName);
            return this;
}
```

<div style="page-break-after: always"></div>

**Clase Main**

Se crea un nuevo Employee con el nuevo atributo del departamento

```java
Employee emp = new Employee.EmployeeBuilder()
                .setName("Oscar Javier Blancarte Iturralde")
                .setGender("Male")
                .setAge(29)
                .setAdress("Benito "
                        + "Juarez", "Mexico D.F.", "Mexico", "03400")
                .addContacs("Rene Blancarte", "1122334455", "123", "Casa",
                        "Chapultepect No. 123 Col. Militar", "Mexico"
                        , "Mexico", "10023")
                .addContacs("Jaime Blancarte", "3344556677", null, "Celular")
                .addPhones("4567890234", null, "Celular")
                .addPhones("7788990099", null, "Casa")
                .addDepartment("Accesorios Deportivos")
                .build();
```

**Resultado**

```bash
Connected to the target VM, address: '127.0.0.1:52828', transport: 'socket'
Employee{name=Oscar Javier Blancarte Iturralde, age=29, gender=Male, 
address=Address{address=Benito Juarez, city=Mexico D.F., country=Mexico, cp=03400}, 
phones=[Phone{phoneNumber=4567890234, ext=null, phoneType=Celular}, Phone{phoneNumber=7788990099, ext=null, phoneType=Casa}], 
contacts=[Contact{name=Rene Blancarte, phone=Phone{phoneNumber=1122334455, ext=123, phoneType=Casa}}, Contact{name=Jaime Blancarte, phone=Phone{phoneNumber=3344556677, ext=null, phoneType=Celular}}]
department=Department{name='Accesorios Deportivos'}}
Disconnected from the target VM, address: '127.0.0.1:52828', transport: 'socket'
```


