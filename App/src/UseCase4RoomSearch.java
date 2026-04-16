import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Room Domain Object
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getAmenities() { return amenities; }

    @Override
    public String toString() {
        return String.format("%-12s | Price: $%6.2f | Amenities: %s", type, price, amenities);
    }
}

// Inventory as State Holder
class Inventory {
    private Map<String, Integer> roomCounts = new HashMap<>();
    private Map<String, Room> roomDetails = new HashMap<>();

    public void addRoomType(Room room, int count) {
        roomCounts.put(room.getType(), count);
        roomDetails.put(room.getType(), room);
    }

    // Read-only access to availability
    public int getAvailability(String roomType) {
        return roomCounts.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return new HashMap<>(roomCounts); // Return a copy to prevent external mutation
    }

    public Room getRoomDetails(String roomType) {
        return roomDetails.get(roomType);
    }
}

// Search Service - Handles read-only logic
class SearchService {
    private Inventory inventory;

    public SearchService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {
        System.out.println("\n--- Available Room Search Results ---");
        boolean found = false;

        for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {
            String roomType = entry.getKey();
            int availableCount = entry.getValue();

            // Validation Logic: Only show room types with availability > 0
            if (availableCount > 0) {
                Room details = inventory.getRoomDetails(roomType);
                System.out.println(details + " | Available: " + availableCount);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms currently available.");
        }
        System.out.println("-------------------------------------\n");
    }
}

// Main Class
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        // Initialize Inventory
        Inventory hotelInventory = new Inventory();

        // Setup initial room data
        hotelInventory.addRoomType(new Room("Single", 100.0, "Wifi, TV"), 5);
        hotelInventory.addRoomType(new Room("Double", 150.0, "Wifi, TV, Mini-bar"), 2);
        hotelInventory.addRoomType(new Room("Suite", 300.0, "Wifi, TV, Kitchen, Jacuzzi"), 0); // Unavailable

        // Initialize Search Service
        SearchService searchService = new SearchService(hotelInventory);

        // Perform search
        System.out.println("Guest initiates a room search request...");
        searchService.searchAvailableRooms();

        System.out.println("Search complete. System state remains unchanged.");
    }
}