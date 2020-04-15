package ProcessCommands;
import ManagementLogic.*;
import ModelClasses.*;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {
    public String clientName, clientAddress, productName;
    public int productQuantity;
    public float productPrice;
    public Reporter reporter = new Reporter();
    Object doCommands;

    public CommandProcessor() throws FileNotFoundException {};
        private static Pattern p = Pattern.compile("\\S+");;
        private static Matcher m;
        private static String find;


    /**
     * Metoda care, utilizand regex, parseaza comanda primita ca parametru si apeleaza metodele necesare, in functie de ce comanda s-a trimis
     * @param command comanda citita din fisier ce urmeaza sa fie interpretata
     * @throws Exception
     */
    public void processCommand(String command) throws Exception{
        initRegex(command);
        find = new String();
        while(m.find()) {
            find += m.group();
            if(find.equals("Insert")){
                m.find();
                if(m.group().equals("client:")){
                    insertClient();
                    break;
                }
                else{ //daca nu se insereaza client, se insereaza produs
                    insertProduct();
                    break;
                }
            }
            else if(find.equals("Report")){
                report();
                break;
            }
            else if(find.equals("Order:")){
                order();
                break;
            }
            else if(find.equals("Delete")) {
                delete();
                break;
            }
        }
    }
    private void initRegex(String command){
        m = p.matcher(command);
    }
    private void insertClient(){
        m.find();
        clientName = m.group();
        m.find();
        clientName += " " + m.group().replace(",","");
        m.find();
        clientAddress = m.group();
        doCommands = new Inserter(new Client(clientName,clientAddress));
    }
    private void insertProduct(){
        m.find();
        productName = m.group().replace(",","");
        m.find();
        productQuantity = Integer.parseInt(m.group().replace(",",""));
        m.find();
        productPrice = Float.parseFloat(m.group());
        doCommands = new Inserter(new Product(productName,productQuantity,productPrice));
    }
    private void order() throws Exception {
        m.find();
        clientName = m.group();
        m.find();
        clientName += " " + m.group().replace(",","");
        m.find();
        productName = m.group().replace(",","");
        m.find();
        productQuantity = Integer.parseInt(m.group().replace(",",""));
        doCommands = new OrderManager(new Order(clientName,productName,productQuantity));
    }
    private void delete(){
        m.find();
        if (m.group().equals("client:")) {
            m.find(); clientName = m.group();
            m.find(); clientName += " " + m.group().replace(",","");
            m.find(); clientAddress = m.group();
            doCommands =  new Deleter(new Client(clientName, clientAddress));
        } else {
            while(m.find()) {
                productName = m.group();
                doCommands = new Deleter(new Product(productName, 0, 0)); //the price and quantity are not important in deletion
            }
        }
    }
    private void report() throws Exception {
        m.find();
        if(m.group().equals("client")) reporter.reportClients();
        else if(m.group().equals("product")) reporter.reportProducts();
        else if(m.group().equals("order")) reporter.reportOrders();
    }
}
