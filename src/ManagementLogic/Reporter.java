package ManagementLogic;
import DBConnection.*;
import FileProcessor.*;
import java.io.FileNotFoundException;

public class Reporter extends DBConnect{
    private ProcessFile outputFile = new ProcessFile("");

    public Reporter() throws FileNotFoundException {
    }

    /**
     * ia informatiile despre clienti si genereaza pdf-ul aferent
     * @throws Exception
     */
    public void reportClients() throws Exception {
        getAll("clients");
        outputFile.reportClients(clientTable);
    }
    /**
     * ia informatiile despre produse si genereaza pdf-ul aferent
     * @throws Exception
     */
    public void reportProducts() throws Exception {
        getAll("products");
        outputFile.reportProducts(productTable);
    }
    /**
     * ia informatiile despre comenzi si genereaza pdf-ul aferent
     * @throws Exception
     */
    public void reportOrders() throws Exception {
        getAll("orders");
        outputFile.reportOrders(orderTable);
    }
}
