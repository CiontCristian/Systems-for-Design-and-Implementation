package domain;

public class Purchased extends BaseEntity<Long>{
    private long bookID;
    private long clientID;

    public Purchased(long _bookID, long _clientID){
        this.bookID=_bookID;
        this.clientID=_clientID;
    }

    public long getBookID(){return bookID;}
    public long getClientID(){return clientID;}

    @Override
    public String toString() {
        return "Purchased{" +
                "BookID='" + bookID + '\'' +
                ", Cleint='" + clientID + '\'' +
                "} " + super.toString();
    }
}
