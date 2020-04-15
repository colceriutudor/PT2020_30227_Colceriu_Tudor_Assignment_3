package ManagementLogic;
import DBConnection.*;
import ModelClasses.*;

public class Inserter extends DBConnect{
    private String statement = "insert into ";
    public Inserter(Object toBeInserted){
        if(toBeInserted instanceof Client)
            insertClient((Client)toBeInserted);
        else if(toBeInserted instanceof Product) insertProduct((Product)toBeInserted);
        else if(toBeInserted instanceof Order) insertOrder((Order)toBeInserted);
    }

    /**
     * genereaza comanda de insertie pentru client
     * @param client clientul pentru care se doreste inserarea
     */
    private void insertClient(Client client){
        this.statement += "clients" + " values(0,'" + client.getName() + "','" + client.getAdress() + "')";
        executeQuerry(statement);
    }
    /**
     * genereaza comanda de insertie pentru produs
     * @param product produsul pentru care se doreste inserarea
     */
    private void insertProduct(Product product){
        this.statement += "products" + " values(0,'" + product.getName() + "'," + product.getQuantity() + "," + product.getPrice() + ")";
        executeProductQuery(statement, product);
    }
    /**
     * genereaza comanda de insertie pentru comanda
     * @param order comanda pentru care se doreste inserarea
     */
    void insertOrder(Order order){
        this.statement += "orders" + " values(0,'" + order.getCustomerName() + "','" + order.getProductName() + "'," + order.getOrderQuantity() + ")";
        executeQuerry(this.statement);
    }
}
