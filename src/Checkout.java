public class Checkout {
    private boolean checkedout;
    private String errorMessage;
    private double subtotal;
    private double shippingFees;
    private double totalAmount;
    private double customerBalanceAfter;
  
  
   public Checkout(boolean checkedout, String errorMessage) {
        this.checkedout = checkedout;
        this.errorMessage = errorMessage;
        this.subtotal = 0;
        this.shippingFees = 0;
        this.totalAmount = 0;
        this.customerBalanceAfter = 0;
    }

     public Checkout(double subtotal, double shippingFees, double totalAmount, double customerBalanceAfter) {
        this.checkedout = true;
        this.errorMessage = null;
        this.subtotal = subtotal;
        this.shippingFees = shippingFees;
        this.totalAmount = totalAmount;
        this.customerBalanceAfter = customerBalanceAfter;
    }

    public boolean checkedout() { 
        return checkedout; 
    }
    public void setCheckedout(boolean checkedout) { 
        this.checkedout = checkedout; 
    }

    public String getErrorMessage() { 
        return errorMessage; 
    }
    public void setErrorMessage(String errorMessage) { 
        this.errorMessage = errorMessage; 
    }

    public double getSubtotal() { 
        return subtotal;
     }
    public void setSubtotal(double subtotal) { 
        this.subtotal = subtotal;
    }

    public double getShippingFees() { 
        return shippingFees; 
    }
    public void setShippingFees(double shippingFees) { 
        this.shippingFees = shippingFees; 
    }

    public double getTotalAmount() {
         return totalAmount;
         }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getCustomerBalanceAfter() { 
        return customerBalanceAfter; 
    }
    public void setCustomerBalanceAfter(double customerBalanceAfter) {
        this.customerBalanceAfter = customerBalanceAfter;
    }
    public void printReceipt() {
        if (!checkedout) {
            System.out.println("Checkout failed: " + errorMessage);
            return;
        }
        
        System.out.println("** Checkout receipt **");
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Shipping: " + shippingFees);
        System.out.println("----------------------");
        System.out.println("Amount: " + totalAmount);
        System.out.println("Customer balance after: " + customerBalanceAfter);
        System.out.println("END.");
    }
}

