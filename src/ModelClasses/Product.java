package ModelClasses;

public class Product {
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getQuantity() {return quantity;}

    public void setName(String name) {this.name = name;}
    public void setPrice(int price) {this.price = price;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    @Override
    public String toString() {
        return "Product: " + name + ", " + price + ", " + quantity;
    }
}

