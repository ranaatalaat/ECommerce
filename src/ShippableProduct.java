public class ShippableProduct extends Product implements Shippable {
    private double weight;
    public ShippableProduct(String name, double price, int quantity, String type, double weight) {
        super(name, price, quantity, type);
        this.weight = weight;
    }
    public String getName() {
        return super.getName();
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    

}
