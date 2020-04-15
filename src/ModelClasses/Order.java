package ModelClasses;

public class Order {
    private static final int orderId = 0; //the database is implemented so the id auto-increments
    private String customerName, productName;
    private int orderQuantity;

    public Order(String customerName, String productName, int orderQuantity) {
        this.customerName = customerName;
        this.productName = productName;
        this.orderQuantity = orderQuantity;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Order:" + customerName + ", " + productName + ", " + orderQuantity;
    }
}
