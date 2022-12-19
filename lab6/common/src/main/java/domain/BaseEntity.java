package domain;

import java.io.Serializable;

public class BaseEntity<ID> implements Serializable {
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
