package DBConnection;

import ModelClasses.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnect {
    private Connection connection;
    private Statement statement;
    private ResultSet res;
    private String querry, selectAll;
    public ArrayList<Client> clientTable;
    public ArrayList<Product> productTable;
    public ArrayList<Order> orderTable;

    public DBConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp_assignment3_db","root","mysqkolse123");
            statement = connection.createStatement();
        }catch(Exception e) {
            System.out.println("Error: "+e);
        }
    }

    /**
     * Executa un query normal, indiferent de ce query este, fara sa se tina cont de ce se returneaza
     * @param querry query-ul SQL ce trebuie executat
     */
    public void executeQuerry(String querry){
            try {
                this.querry = querry;
                statement.execute(this.querry);
            }catch(Exception e) {
                System.out.println("Eroare la querry: executeQuerry "+e);
            }
    }

    /**
     * metoda care verifica daca avem produsul (trimis ca si parametru) in stoc, si il adauga / actualizeaza stocul
     * @param query query-ul ce trebuie executat
     * @param product produsul ce se doreste a fi inserat
     */
    public void executeProductQuery(String query, Product product){
        try {
            String check = "select quantity from products where product_name like '" + product.getName() + "'";
            res = statement.executeQuery(check);
            if(!res.next()) //nu avem in tabela acel produs, il inseram
                executeQuerry(query);
            else {
                int finalQuantity = (product.getQuantity() + res.getInt("quantity"));
                statement.execute("update products " +
                        "set quantity =" + finalQuantity
                        + " where product_name like '" + product.getName() + "'");
            }
        }catch(Exception e) {
            System.out.println("Eroare la querry prod: "+e);
        }
    }

    /**
     * metoda executa un query pe tabelul cerut, ce returneaza toate campurile din acesta
     * @param table tabelul din care se doresc informatii
     * @throws Exception
     */
    public void getAll(String table) throws Exception{
        try {
            selectAll =  "select * from ";
            selectAll += table; //selectarea se face din tabelul dorit
            res = statement.executeQuery(selectAll);
            clientTable = new ArrayList<Client>();
            productTable = new ArrayList<Product>();
            orderTable = new ArrayList<Order>();
            while (res.next()) {
                if (table.equals("clients"))
                    clientTable.add(new Client(res.getString("name"), res.getString("address")));
                else if (table.equals("products"))
                    productTable.add(new Product(res.getString("product_name"),res.getInt("quantity"), res.getDouble("price")));
                else if (table.equals("orders"))
                    orderTable.add(new Order(res.getString("client_name"), res.getString("product_name"), res.getInt("order_quantity")));
            }
        }catch(Exception e){
            System.out.println("Eroare la querry-ul de getAll: " + e);
        }
    }

    /**
     * metoda verifica daca avem produsul cerut in depozit, respectiv daca este destul in stoc pentru comanda ceruta, dar si daca persoana care cere
     * comanda se afla in tabela persoane
     * @param customerName numele clientului care a depus comanda
     * @param productName produsul cerut de client
     * @param orderQuantity cantitatea dorita de client pentru produsul cerut
     * @return
     */
    public int validateOrder(String customerName, String productName, int orderQuantity){
        try {
            this.querry = "select name from clients where name like '" + customerName + "'";
            if(statement.executeQuery(querry).next() && statement.executeQuery("select product_name from products where product_name like '" + productName + "'").next()){ //exista persoana, exista produsul
                //verificam stocul
                res = statement.executeQuery("select quantity from products where product_name like '" + productName + "'");
                if(res.next()) {
                    int quantity = res.getInt("quantity");
                    return quantity - orderQuantity;
                }
            }
        }catch(Exception e) {
            System.out.println("Eroare la querry: validateOrder " + e);
        }
        return -1;
    }

    /**
     * dupa ce comanda a fost validata, ea este procesata si stocul actualizat
     * @param quantity cantitatea noua de produs
     * @param productName produsul pentru care s-a modificat cantitatea in urma comenzii
     * @throws Exception
     */
    public void updateProducts(int quantity, String productName) throws Exception{
        try {
            statement.execute("update products " +
                    "set quantity =" + quantity
                    + " where product_name like '" + productName + "'");
        }catch(Exception e){
            System.out.println("Eroare la querry-ul de updateProducts: " + e + " " + productName);
        }
    }
}
