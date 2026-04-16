import java.util.*;

public class UseCase6RoomAllocationService {

    // Inventory Service: Tracks remaining count of each room type
    private static Map<String, Integer> inventory = new HashMap<>();

    // Booking Service: Queue for FIFO request handling
    private static Queue<BookingRequest> bookingQueue = new LinkedList<>();

    // Allocation Tracking: Maps Room Type -> Set of unique assigned Room IDs
    private static Map<String, Set<String>> allocatedRooms = new HashMap<>();

    static class BookingRequest {
        String guestName;
        String roomType;

        BookingRequest(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    public static void main(String[] args) {
        // Initialize Inventory
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        // Initialize Allocation Sets
        allocatedRooms.put("Deluxe", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());

        // Simulate incoming requests
        bookingQueue.add(new BookingRequest("Alice", "Deluxe"));
        bookingQueue.add(new BookingRequest("Bob", "Deluxe"));
        bookingQueue.add(new BookingRequest("Charlie", "Suite"));
        bookingQueue.add(new BookingRequest("David", "Deluxe")); // Should fail (no inventory)

        processBookings();
    }

    public static void processBookings() {
        System.out.println("--- Starting Room Allocation ---");

        while (!bookingQueue.isEmpty()) {
            BookingRequest request = bookingQueue.poll();
            String type = request.roomType;

            // 1. Check Availability
            if (inventory.getOrDefault(type, 0) > 0) {

                // 2. Generate Unique Room ID (e.g., Deluxe-101)
                String roomId = type + "-" + (100 + (allocatedRooms.get(type).size() + 1));

                // 3. Prevent Double Booking using Set uniqueness
                if (!allocatedRooms.get(type).contains(roomId)) {
                    allocatedRooms.get(type).add(roomId);

                    // 4. Atomic-like update: Decrement Inventory
                    inventory.put(type, inventory.get(type) - 1);

                    System.out.println("CONFIRMED: " + request.guestName +
                            " assigned to " + roomId);
                }
            } else {
                System.out.println("FAILED: No " + type + " rooms available for " + request.guestName);
            }
        }

        displayFinalState();
    }

    private static void displayFinalState() {
        System.out.println("\n--- Final System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Allocations: " + allocatedRooms);
    }
}
