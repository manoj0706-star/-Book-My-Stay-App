import java.util.*;

// Class representing an individual optional offering
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return serviceName + " ($" + price + ")";
    }
}

// Manages the association between reservations and selected services
class AddOnServiceManager {
    // Map and List Combination: Efficient lookup and preserves selection order
    private Map<String, List<AddOnService>> reservationAddOns;

    public AddOnServiceManager() {
        this.reservationAddOns = new HashMap<>();
    }

    // Adds a service to a specific reservation ID
    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationAddOns.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service.getServiceName() + " to Reservation: " + reservationId);
    }

    // Cost Aggregation: Calculates total additional cost for a reservation
    public double calculateTotalExtraCost(String reservationId) {
        List<AddOnService> services = reservationAddOns.getOrDefault(reservationId, new ArrayList<>());
        return services.stream().mapToDouble(AddOnService::getPrice).sum();
    }

    public void displayAddOns(String reservationId) {
        List<AddOnService> services = reservationAddOns.get(reservationId);
        if (services == null || services.isEmpty()) {
            System.out.println("No add-ons for Reservation: " + reservationId);
        } else {
            System.out.println("Add-ons for " + reservationId + ": " + services);
        }
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        // Define available services
        AddOnService wifi = new AddOnService("Premium WiFi", 15.0);
        AddOnService breakfast = new AddOnService("Buffet Breakfast", 25.0);
        AddOnService spa = new AddOnService("Spa Treatment", 50.0);

        String resId = "RES-1001";

        System.out.println("--- Selecting Add-On Services ---");
        manager.addServiceToReservation(resId, wifi);
        manager.addServiceToReservation(resId, breakfast);

        System.out.println("\n--- Reservation Summary ---");
        manager.displayAddOns(resId);

        double extraCost = manager.calculateTotalExtraCost(resId);
        System.out.println("Total Additional Cost: $" + extraCost);

        System.out.println("\n--- Business Extensibility Check ---");
        // Core booking logic remains untouched while we add more services
        manager.addServiceToReservation(resId, spa);
        System.out.println("Updated Total Extra Cost: $" + manager.calculateTotalExtraCost(resId));
    }
}
