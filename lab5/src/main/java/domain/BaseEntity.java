package domain;

public class BaseEntity<ID> {
    private ID id;

    public ID getId(){return id;}

    public void setId(ID newID){
        id=newID;
    }

    @Override
    public String toString(){
        return "BaseEntity("+"id="+id+")";
    }
}
