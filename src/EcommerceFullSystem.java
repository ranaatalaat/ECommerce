import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class EcommerceFullSystem {
    private List<Product> products;
    private List<Customer> customers;
    private CheckoutService checkoutService;
    private ShippingService shippingService;

    public EcommerceFullSystem() {
        this.products = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.shippingService = new ShippingService();
        this.checkoutService = new CheckoutService(shippingService);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product findProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer findCustomer(String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }

    public Checkout processCheckout(Customer customer, Cart cart) {
        return checkoutService.checkout(customer, cart);
    }


    public void displayProducts() {
        System.out.println("=== Available Products ===");
        for (Product product : products) {
            System.out.printf("%s - $%.2f (Stock: %d)%n", 
                product.getName(), product.getPrice(), product.getQuantity());
        }
        System.out.println("==========================");
    }

    public void displayCustomers() {
        System.out.println("=== Customers ===");
        for (Customer customer : customers) {
            System.out.printf("%s (%s) - Balance: $%.2f%n",
                customer.getName(), customer.getEmail(), customer.getBalance());
        }
        System.out.println("=================");
    }

public static void main(String[] args) {
        try {
            EcommerceFullSystem system = new EcommerceFullSystem();

            // Shared Products
            Product cheese = new ExpirableShippableProduct("Cheese", 100.0, 10, "Dairy", LocalDate.now().plusDays(7), 0.2);
            Product biscuits = new ExpirableShippableProduct("Biscuits", 150.0, 5, "Snack", LocalDate.now().plusDays(14), 0.7);
            Product tv = new ShippableProduct("TV", 500.0, 3, "Electronics", 15.0);
            Product scratchCard = new BasicProduct("Mobile Scratch Card", 10.0, 100, "Digital");

            system.addProduct(cheese);
            system.addProduct(biscuits);
            system.addProduct(tv);
            system.addProduct(scratchCard);

            // === 1. Basic Product ===
            System.out.println("\n--- Test: Basic Product ---");
            Customer basicCustomer = new Customer("Basic User", "basic@email.com", "Cairo", 200.0);
            system.addCustomer(basicCustomer);
            Cart basicCart = basicCustomer.getCart();
            basicCart.addItem(scratchCard, 2);
            system.processCheckout(basicCustomer, basicCart).printReceipt();

            // === 2. Shippable Product ===
            System.out.println("\n--- Test: Shippable Product ---");
            Customer shipCustomer = new Customer("Ship User", "ship@email.com", "Alexandria", 2000.0);
            system.addCustomer(shipCustomer);
            Cart shipCart = shipCustomer.getCart();
            shipCart.addItem(tv, 1);
            system.processCheckout(shipCustomer, shipCart).printReceipt();

            // === 3. Expirable Product ===
            System.out.println("\n--- Test: Expirable Product ---");
            Customer expCustomer = new Customer("Expiry User", "expiry@email.com", "Giza", 500.0);
            system.addCustomer(expCustomer);
            Product milk = new ExpirableProduct("Milk", 50.0, 5, "Dairy", LocalDate.now().plusDays(2));
            system.addProduct(milk);
            Cart expCart = expCustomer.getCart();
            expCart.addItem(milk, 2);
            system.processCheckout(expCustomer, expCart).printReceipt();

            // === 4. Shippable + Expirable Product ===
            System.out.println("\n--- Test: Shippable + Expirable Product ---");
            Customer hybridCustomer = new Customer("Hybrid User", "hybrid@email.com", "Aswan", 800.0);
            system.addCustomer(hybridCustomer);
            Product iceCream = new ExpirableShippableProduct("Ice Cream", 75.0, 3, "Frozen", LocalDate.now().plusDays(3), 0.5);
            system.addProduct(iceCream);
            Cart hybridCart = hybridCustomer.getCart();
            hybridCart.addItem(iceCream, 1);
            system.processCheckout(hybridCustomer, hybridCart).printReceipt();

            // === 5. Poor Customer (Insufficient Balance) ===
            System.out.println("\n--- Test: Poor Customer (Insufficient Balance) ---");
            Customer poorUser = new Customer("Poor John", "poor@email.com", "Tanta", 10.0);
            system.addCustomer(poorUser);
            Cart poorCart = poorUser.getCart();
            try {
                poorCart.addItem(tv, 1);
            } catch (Exception e) {
                System.out.println("Expected insufficient balance error: " + e.getMessage());
            }
            system.processCheckout(poorUser, poorCart).printReceipt();

            // === 6. Rich Customer ===
            System.out.println("\n--- Test: Rich Customer ---");
            Customer richUser = new Customer("Rich Queen", "rich@email.com", "Zamalek", 5000.0);
            system.addCustomer(richUser);
            Cart richCart = richUser.getCart();
            richCart.addItem(biscuits, 2);
            richCart.addItem(scratchCard, 5);
            system.processCheckout(richUser, richCart).printReceipt();

            // === 7. Out of Stock ===
            System.out.println("\n--- Test: Out of Stock ---");
            Customer stockUser = new Customer("OutOfStock Buyer", "stock@email.com", "Luxor", 2000.0);
            system.addCustomer(stockUser);
            Cart stockCart = stockUser.getCart();
            try {
                stockCart.addItem(tv, 10); // should throw out-of-stock
            } catch (Exception e) {
                System.out.println("Expected out-of-stock error: " + e.getMessage());
            }

            // === 8. Expired Product ===
            System.out.println("\n--- Test: Expired Product ---");
            Customer expiredCustomer = new Customer("Late Buyer", "latebuyer@email.com", "Mansoura", 300.0);
            system.addCustomer(expiredCustomer);
            Product oldYogurt = new ExpirableProduct("Yogurt", 20.0, 10, "Dairy", LocalDate.now().minusDays(1));
            system.addProduct(oldYogurt);
            Cart expiredCart = expiredCustomer.getCart();
            try {
                expiredCart.addItem(oldYogurt, 1); // should throw expired exception
            } catch (Exception e) {
                System.out.println("Expected expired error: " + e.getMessage());
            }

        } catch (ValidationException e) {
            System.err.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}