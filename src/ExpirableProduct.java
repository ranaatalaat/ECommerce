import java.time.LocalDate;

public class ExpirableProduct extends Product implements Expirable {
    private LocalDate expiryDate;

    public ExpirableProduct(String name, double price, int quantity, String type, LocalDate expiryDate) throws ValidationException {
        super(name, price, quantity, type);
        this.expiryDate = expiryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public boolean isAvailable(int requestedQuantity) {
        return requestedQuantity <= getQuantity() && !isExpired();
    }

    @Override
    public void reduceQuantity(int quantity) {
        try {
            if (isExpired()) {
                throw new ProductExpiredException("Product is expired and cannot be purchased.");
            }

            if (quantity > getQuantity()) {
                throw new ProductOutOfStockException("Insufficient quantity available for purchase.");
            }

            setQuantity(getQuantity() - quantity);
        } catch (ProductExpiredException | ProductOutOfStockException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
