package FileProcessor;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import ModelClasses.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * clasa care proceseaza fisierele, fie ele de intrare sau de iesire
 */
public class ProcessFile {
    private static int fileCounter = 0;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-z_");

    private Document document;
    private String inputFile;
    private PdfPTable table;
    private String line;
    private File input; //fisierul din care se citeste, facut in directorul curent
    private Scanner sc;

    public ProcessFile(String inputFile) throws FileNotFoundException {
        this.inputFile = inputFile;
    }

    public void readCommandFile() throws FileNotFoundException {
        input = new File(inputFile);
        sc = new Scanner(input);
    }
    public String readLine() throws FileNotFoundException {
        if(sc.hasNextLine()) {
            line = sc.nextLine();
            return line;
        }
        else return null;
    }

    /**
     * genereaza un tabel in pdf cu informatiile despre clienti
     * @param clients lista de clienti
     */
    public void initClientsTable(ArrayList<Client> clients){
        table = new PdfPTable(2);
        table.addCell("Client Name");
        table.addCell("Address");
        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (Client c: clients) {
            table.addCell(c.getName());
            table.addCell(c.getAdress());
        }
    }

    /**
     * genereaza un tabel pdf cu informatiile despre produse
     * @param products lista de produse
     */
    public void initProductsTable(ArrayList<Product> products){
        table = new PdfPTable(3);
        table.addCell("Product name");
        table.addCell("Product quantity");
        table.addCell("Product price");
        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (Product p: products){
            table.addCell(p.getName());
            table.addCell(String.valueOf(p.getQuantity()));
            table.addCell(String.valueOf(p.getPrice()));
        }
    }

    /**
     * genereaza un tabel pdf cu informatiile despre comenzi
     * @param orders
     */
    public void initOrdersTable(ArrayList<Order> orders){
        table = new PdfPTable(3);
        table.addCell("Customer name");
        table.addCell("Ordered product");
        table.addCell("Ordered quantity");
        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (Order o: orders){
            table.addCell(o.getCustomerName());
            table.addCell(o.getProductName());
            table.addCell(String.valueOf(o.getOrderQuantity()));
        }
    }

    /**
     * genereaza fisierul pdf cu tabela pentru clienti
     * @param clients lista de clienti cu ajutorul careia se face tabelul
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportClients(ArrayList<Client> clients) throws FileNotFoundException, DocumentException {
        initClientsTable(clients);
        createPDF("clientsReport - " + formatter.format(new Date(System.currentTimeMillis())) + fileCounter +  ".pdf");
        fileCounter++;
    }
    /**
     * genereaza fisierul pdf cu tabela pentru produse
     * @param products lista de produse cu ajutorul careia se face tabelul
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportProducts(ArrayList<Product> products) throws FileNotFoundException, DocumentException {
        initProductsTable(products);
        createPDF("productsReport - " + formatter.format(new Date(System.currentTimeMillis())) + fileCounter +  ".pdf");
        fileCounter++;
    }
    /**
     * genereaza fisierul pdf cu tabela pentru comenzi
     * @param orders lista de comenzi cu ajutorul careia se face tabelul
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportOrders(ArrayList<Order> orders) throws FileNotFoundException, DocumentException {
        initOrdersTable(orders);
        createPDF("ordersReport - " + formatter.format(new Date(System.currentTimeMillis())) + fileCounter +  ".pdf");
        fileCounter++;
    }
    /**
     * creaza un document pdf ce contine un tabel
     * @param pdfName numele documentului pdf
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void createPDF(String pdfName) throws FileNotFoundException, DocumentException {
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfName));
        document.open();
        document.add(table);
        document.close();
    }

    /**
     * creaza un document pdf pentru chitanta
     * @param order comanda pentru care se face chitanta
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void createBill(Order order) throws FileNotFoundException, DocumentException {
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Bill - " + order.getCustomerName() + " " + formatter.format(new Date(System.currentTimeMillis())) + fileCounter +  ".pdf"));
        document.open();
        document.add(new Paragraph("Client " + order.getCustomerName() + " ordered the product " + order.getProductName() +  " and recieved it"
         + "\n" + "ORDER QUANTITY: " + order.getOrderQuantity()));
        document.close();
        fileCounter++;
    }

    /**
     * creaza un document pdf pentru o comanda nereusita
     * @param order comanda pentru care se genereaza pdf-ul
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportBill(Order order) throws FileNotFoundException, DocumentException {
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Failed order - " + order.getCustomerName() + " " + formatter.format(new Date(System.currentTimeMillis())) + fileCounter +  ".pdf"));
        document.open();
        document.add(new Paragraph("Client " + order.getCustomerName() + " ordered the product " + order.getProductName() +  "\n"
                + " UNABLE TO RECIEVE !! " + " insufficient stock to dispose " + order.getOrderQuantity() + " " + order.getProductName()));
        document.close();
        fileCounter++;
    }
}
