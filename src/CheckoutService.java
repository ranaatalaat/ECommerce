import java.util.List;
public class CheckoutService {
    private ShippingService shippingService;
    
    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    
    public Checkout checkout(Customer customer, Cart cart) {
        try {
            if (cart.isEmpty()) {
                throw new EmptyCartException("Cart is empty.");
            }
            double subtotal = cart.getSubtotal();
            List<Shippable> shippableItems = cart.getShippableItems();
            double shippingFees = calculateShippingFees(shippableItems);
            double totalAmount = subtotal + shippingFees;
            if (!customer.hasEnoughBalance(totalAmount)) {
                throw new InsufficientBalanceException("Customer's balance is insufficient.");
            }
            customer.removeFromBalance(totalAmount);
            updateProductQuantities(cart);
            if (!shippableItems.isEmpty()) {
                shippingService.ship(shippableItems);
            }
            cart.clear();
            return new Checkout(subtotal, shippingFees, totalAmount, customer.getBalance());
        } catch (EmptyCartException | ProductExpiredException | ProductOutOfStockException | InsufficientBalanceException e) {
            return new Checkout(false, "Checkout failed: " + e.getMessage());
        } catch (Exception e) {
            return new Checkout(false, "Unexpected error: " + e.getMessage());
        }
    }
    
    private double calculateShippingFees(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return 0;
        }
        return shippingService.calculateShippingFee(shippableItems);
    }
    
   private void updateProductQuantities(Cart cart) throws ProductOutOfStockException, ProductExpiredException {
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
    }
}
