public abstract class Product {
    private String name;
    private double price;
    private int quantity;
    private String type;

    public Product(String name, double price, int quantity, String type) {
        try {
            if (price < 0) throw new ValidationException("Price cannot be negative.");
            if (quantity < 0) throw new ValidationException("Quantity cannot be negative.");
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.type = type;
        } catch (ValidationException e) {
            System.out.println("Error while creating product: " + e.getMessage());
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable(int requestedQuantity){
        return requestedQuantity <= quantity;
    }

    public void reduceQuantity(int quantity) {
        try {
            if (quantity > getQuantity()) {
                throw new ProductOutOfStockException("Insufficient quantity available for purchase.");
            }
            setQuantity(getQuantity() - quantity);
        } catch (ProductOutOfStockException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error during quantity reduction: " + e.getMessage());
        }
    }
}
