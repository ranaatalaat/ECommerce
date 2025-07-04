import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ShippingService {
    private static final double SHIPPING_RATE = 10.0;

    public void ship(List<Shippable> items) {
        try {
            if (items == null || items.isEmpty()) {
                System.out.println("No items to ship.");
                return;
            }
            printShipmentNotice(items);
        } catch (Exception e) {
            System.out.println("Error during shipping process: " + e.getMessage());
        }
    }

    public double calculateShippingFee(List<Shippable> items) {
        try {
            if (items == null || items.isEmpty()) {
                return 0;
            }
            double totalWeight = calculateTotalWeight(items);
            return totalWeight * SHIPPING_RATE;
        } catch (Exception e) {
            System.out.println("Error calculating shipping fee: " + e.getMessage());
            return 0;
        }
    }

    private double calculateTotalWeight(List<Shippable> items) {
        double totalWeight = 0;
        try {
            for (Shippable item : items) {
                totalWeight += item.getWeight();
            }
        } catch (Exception e) {
            System.out.println("Error calculating total weight: " + e.getMessage());
        }
        return totalWeight;
    }

    private void printShipmentNotice(List<Shippable> items) {
        try {
            System.out.println("** Shipment notice **");
            Map<String, Integer> itemCounts = new HashMap<>();
            Map<String, Double> itemWeights = new HashMap<>();

            for (Shippable item : items) {
                String name = item.getName();
                itemCounts.put(name, itemCounts.getOrDefault(name, 0) + 1);
                itemWeights.put(name, item.getWeight());
            }

            for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
                String name = entry.getKey();
                int count = entry.getValue();
                double weightPerItem = itemWeights.get(name);
                double totalWeightForItem = weightPerItem * count;
                System.out.printf("%dx %s %.0fg%n", count, name, totalWeightForItem);
            }

            double totalWeight = calculateTotalWeight(items);
            System.out.printf("Total package weight %.1fg%n", totalWeight);
        } catch (Exception e) {
            System.out.println("Error printing shipment notice: " + e.getMessage());
        }
    }
}
