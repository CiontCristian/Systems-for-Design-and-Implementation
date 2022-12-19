package domain;

public class Purchased extends BaseEntity<Long> {
    private long bookID;
    private long clientID;

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public Purchased(){}

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
