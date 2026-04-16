import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Demonstrates state reversal and LIFO rollback using a Stack.
 */
public class UseCase10BookingCancellation {

    // Simulating a simple inventory and booking database
    private static int availableRooms = 5;
    private static Map<String, String> bookings = new HashMap<>(); // BookingID -> RoomID
    private static Stack<String> rollbackStack = new Stack<>(); // Tracks released Room IDs

    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: Cancellation & Rollback ---");

        // 1. Setup initial state: Create some bookings
        setupInitialBookings();
        displayStatus();

        // 2. Perform a valid cancellation
        cancelBooking("BK-102");

        // 3. Attempt to cancel a non-existent booking
        cancelBooking("BK-999");

        // 4. Final System Status
        displayStatus();
    }

    /**
     * Reverses a booking, increments inventory, and pushes the room back to the rollback stack.
     */
    public static void cancelBooking(String bookingId) {
        System.out.println("\n[Action] Initiating cancellation for: " + bookingId);

        // Validation: Check if reservation exists
        if (!bookings.containsKey(bookingId)) {
            System.err.println("[Error] Cancellation failed: Booking ID " + bookingId + " not found.");
            return;
        }

        // State Reversal logic
        String roomId = bookings.remove(bookingId); // Remove from booking history
        rollbackStack.push(roomId);                // Record room ID in rollback structure (LIFO)
        availableRooms++;                          // Increment inventory count

        System.out.println("[Success] Booking " + bookingId + " cancelled.");
        System.out.println("[Rollback] Room " + roomId + " has been returned to the pool.");
    }

    private static void setupInitialBookings() {
        // Mocking some confirmed bookings
        bookings.put("BK-101", "Room_A");
        bookings.put("BK-102", "Room_B");
        availableRooms = 3; // Reduced from 5 after 2 bookings
        System.out.println("Initial bookings created for BK-101 and BK-102.");
    }

    private static void displayStatus() {
        System.out.println("\n--- Current System State ---");
        System.out.println("Available Inventory: " + availableRooms);
        System.out.println("Active Bookings: " + bookings);
        System.out.println("Rooms in Rollback Stack: " + rollbackStack);
        System.out.println("----------------------------");
    }
}
