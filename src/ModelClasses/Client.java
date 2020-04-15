package ModelClasses;

public class Client {
    private static final int idClient = 0; //the database auto-increments the ids
    private String name, adress;

    public Client(String name, String adress){
        this.name = name;
        this.adress = adress;
    }

    public String getAdress() {return adress;}
    public String getName() {return name;}

    public void setAdress(String adress) {this.adress = adress;}
    public void setName(String name) {this.name = name;}

    @Override
    public String toString() {
        return "Client: " + name + ", " + adress;
    }
}

