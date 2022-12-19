package domain;
import java.util.ArrayList;
import java.util.List;

public class Client extends BaseEntity<Long> {
    private String FirstName;
    private String LastName;
    private int age;

    private List<Purchased> purchased;

    public Client(String _FirstName, String _LastName, int _age){
        FirstName=_FirstName;
        LastName=_LastName;
        age=_age;
        purchased=new ArrayList<>();
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

    public List<Purchased> getPurchased() {return purchased;}

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
