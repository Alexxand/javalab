
public class PersonBean implements java.io.Serializable {
    private String name;
    private String surname;
    private int age;

    public PersonBean(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return (this.name);
    }

    public String getSurname() {
        return (this.surname);
    }

    public int getAge() {
        return (this.age);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PersonBean))
            return false;
        final PersonBean other = (PersonBean) obj;
        return name.equals(other.name) && surname.equals(other.surname);
    }
}
