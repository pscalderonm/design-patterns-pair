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

## Patron Builder

> "Modifiquemos la clase Employee para agregarle una nueva relación con la clase Department (departamento) la cual será una nueva clase que tendremos que crear, la clase departamento deberá tener el nombre del departamento al que pertenece y finalmente agreguemos la construcción de este objeto al EmployeeBuilder."

#### Solucion del problema

**Clase Department**

Se procede con la creación de la clase `Department` según lo requerido en el plateamiento del problema.

``` java
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
Se ajusta la clase `Employee` agregando la nueva propiedad

``` java

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

Se ajusta la clase `EmployeeBuilder` que está dentro de la clase `Employee`
``` java
private Department department;

public EmployeeBuilder addDepartment(String departmentName){
            department = new Department(departmentName);
            return this;
}
```
Se crea un nuevo objeto `Employee` con el nuevo atributo del departamento, dentro de la clase `BuilderMain`

``` java
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

**Resultados obtenidos**

Al ejecutar el programa, se puede observar la información textual del objeto `Employee`, creado a partir del builder, con el nuevo atributo y valor del departamento al que pertence _"Accesorios Deportivos"_.

```bash
Connected to the target VM, address: '127.0.0.1:52828', transport: 'socket'
Employee{name=Oscar Javier Blancarte Iturralde, age=29, gender=Male, 
address=Address{address=Benito Juarez, city=Mexico D.F., country=Mexico, cp=03400}, 
phones=[Phone{phoneNumber=4567890234, ext=null, phoneType=Celular}, Phone{phoneNumber=7788990099, ext=null, phoneType=Casa}], 
contacts=[Contact{name=Rene Blancarte, phone=Phone{phoneNumber=1122334455, ext=123, phoneType=Casa}}, Contact{name=Jaime Blancarte, phone=Phone{phoneNumber=3344556677, ext=null, phoneType=Celular}}]
department=Department{name='Accesorios Deportivos'}}
Disconnected from the target VM, address: '127.0.0.1:52828', transport: 'socket'
```


