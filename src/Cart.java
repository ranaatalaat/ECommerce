import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(CartItem item) {
            Product product = item.getProduct();
            product.reduceQuantity(item.getQuantity());
            for (CartItem existingItem : items) {
                if (existingItem.getProduct().getName().equals(product.getName())) {
                    existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                    return;
                }
            }
            items.add(item);
    }

    public void addItem(Product product, int quantity) {
            addItem(new CartItem(product, quantity));
    }

    public void removeItem(CartItem item) {
        try {
            if (isEmpty()) {
                throw new EmptyCartException("Cannot remove item. Cart is empty.");
            }

            boolean removed = items.removeIf(existingItem ->
                    existingItem.getProduct().getName().equals(item.getProduct().getName()));

            if (!removed) {
                System.out.println("Error: Item not found in cart.");
            }
        } catch (EmptyCartException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeItem(Product product) {
        try {
            if (isEmpty()) {
                throw new EmptyCartException("Cannot remove item. Cart is empty.");
            }

            boolean removed = items.removeIf(existingItem ->
                    existingItem.getProduct().getName().equals(product.getName()));

            if (!removed) {
                System.out.println("Error: Item not found in cart.");
            }
        } catch (EmptyCartException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateItemQuantity(CartItem item, int newQuantity) {
            if (newQuantity <= 0) {
                removeItem(item);
                return;
            }

            for (CartItem cartItem : items) {
                if (cartItem.getProduct().getName().equals(item.getProduct().getName())) {
                    int diff = newQuantity - cartItem.getQuantity();
                    cartItem.getProduct().reduceQuantity(diff);
                    cartItem.setQuantity(newQuantity);
                    return;
                }
            }

            System.out.println("Error: Item not found in cart.");

    }

    public void updateItemQuantity(Product product, int newQuantity) {
        try {
            updateItemQuantity(new CartItem(product, 1), newQuantity);
        } catch (Exception e) {
            System.out.println("Error updating item quantity by product: " + e.getMessage());
        }
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public List<Shippable> getShippableItems() {
        List<Shippable> shippableItems = new ArrayList<>();
        for (CartItem item : items) {
            if (item.getProduct() instanceof Shippable) {
                Shippable shippableProduct = (Shippable) item.getProduct();
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add(shippableProduct);
                }
            }
        }
        return shippableItems;
    }

    public void clear() {
        items.clear();
    }

    public int getTotalItemCount() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    public void displayCart() {
        try {
            if (isEmpty()) {
                throw new EmptyCartException("Cart is empty.");
            }

            System.out.println("=== Cart Contents ===");
            for (CartItem item : items) {
                System.out.printf("%dx %s - $%.2f each = $%.2f%n",
                        item.getQuantity(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getTotalPrice());
            }
            System.out.printf("Subtotal: $%.2f%n", getSubtotal());
            System.out.println("=====================");
        } catch (EmptyCartException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
