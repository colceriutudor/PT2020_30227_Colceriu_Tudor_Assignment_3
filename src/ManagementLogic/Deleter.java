package ManagementLogic;

import DBConnection.DBConnect;
import ModelClasses.Client;
import ModelClasses.Product;

public class Deleter extends DBConnect{
    private String statement = "";
    public Deleter(Object toBeDeleted){
        if(toBeDeleted instanceof Client)
            deleteClient((Client)toBeDeleted);
        else deleteProduct((Product)toBeDeleted);
    }

    /**
     * genereaza comanda de stergere pentru un client
     * @param client clientul pentru care se doreste stergerea
     */
    private void deleteClient(Client client){
        this.statement += "delete from clients where name like '"+ client.getName() + "'";
        executeQuerry(statement);
    }

    /**
     * genereaza comanda de stergere pentru un produs
     * @param product produsul pentru care se doreste stergerea
     */
    private void deleteProduct(Product product){
        this.statement += "delete from products where product_name like '"+ product.getName() + "'";
        executeQuerry(statement);
    }
}
