package ManagementLogic;

import FileProcessor.ProcessFile;
import ModelClasses.*;
import DBConnection.*;

public class OrderManager {
    private Order order;
    DBConnect connection = new DBConnect();
    Inserter inserter;
    private ProcessFile outputFile = new ProcessFile("");

    public OrderManager(Order order) throws Exception {
        this.order = order;
        insertOrderInDb(this.order);
    }

    /**
     * validarea comenzii: returneaza true daca se paote procesa
     * @param order comanda pentru care se face validarea
     * @return
     * @throws Exception
     */
    private boolean isValid(Order order) throws Exception {
        int finalQuantity = connection.validateOrder(order.getCustomerName(),order.getProductName(),order.getOrderQuantity());
        if(finalQuantity >= 0){
            //comanda poate fi procesata
            connection.updateProducts(finalQuantity, order.getProductName());
            return true;
        }
        return false;
    }

    /**
     * crearea comenzii de inserare pentru client si a pdf-ului cu chitanta, sau a pdf-ului de esuare
     * @param order comanda de procesat
     * @throws Exception
     */
    private void insertOrderInDb(Order order) throws Exception {
        if(isValid(order)) {
            inserter = new Inserter(order);
            outputFile.createBill(order);
        }
        else
            outputFile.reportBill(order);
    }
}
