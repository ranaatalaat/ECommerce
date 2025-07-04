public class Customer {
    private String name;
    private double balance = 0.0;
    private String email;
    private String address;
    private Cart cart;

    public Customer(String name, String email, String address, double balance) throws ValidationException {
        if (balance < 0) {
            throw new ValidationException("Initial balance cannot be negative.");
        }
        this.name = name;
        this.email = email;
        this.address = address;
        this.balance = balance;
        this.cart = new Cart();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void removeFromBalance(double amount) {
        try {
            if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance.");
            }
            balance -= amount;
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }
}
