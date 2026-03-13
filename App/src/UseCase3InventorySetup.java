import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private HashMap<String, Integer> inventory;


    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }


    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }


    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }


    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}


public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("Version 3.0 - Centralized Inventory Management");


        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability for Single Room:");
        System.out.println("Available Rooms: " + inventory.getAvailability("Single Room"));


        System.out.println("\nUpdating availability for Single Room...");
        inventory.updateAvailability("Single Room", 8);

        inventory.displayInventory();

        System.out.println("\nSystem inventory updated successfully.");
    }
}