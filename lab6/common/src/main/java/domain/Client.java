package domain;

import java.io.Serializable;

public class Client extends BaseEntity<Long> implements Serializable {
    private String FirstName;
    private String LastName;
    private int age;


    public Client(String _FirstName, String _LastName, int _age){
        FirstName=_FirstName;
        LastName=_LastName;
        age=_age;
    }

    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (age != client.age) return false;
        if (!LastName.equals(client.LastName)) return false;
        return FirstName.equals(client.FirstName);

    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + FirstName + '\'' +
                ", lastName='" + LastName + '\'' +
                ", age=" + age +
                "} " + super.toString();
    }
    public Client(){}
}
