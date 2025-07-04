import java.time.LocalDate;

public class ExpirableShippableProduct extends Product implements Expirable, Shippable {
    private LocalDate expiryDate;
    private double weight;

    public ExpirableShippableProduct(String name, double price, int quantity, String type, LocalDate expiryDate, double weight) throws ValidationException {
        super(name, price, quantity, type);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isAvailable(int requestedQuantity) {
        return requestedQuantity <= getQuantity() && !isExpired();
    }

    @Override
    public void reduceQuantity(int quantity) {
        try {
            if (isExpired()) {
                throw new ProductExpiredException("Product '" + getName() + "' is expired and cannot be purchased.");
            }

            if (quantity > getQuantity()) {
                throw new ProductOutOfStockException("Insufficient quantity for '" + getName() + "'. Requested: " + quantity + ", Available: " + getQuantity());
            }

            setQuantity(getQuantity() - quantity);
        } catch (ProductExpiredException | ProductOutOfStockException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
