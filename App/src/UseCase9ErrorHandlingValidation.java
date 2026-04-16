import java.util.*;

// Custom Exception for Invalid Booking Scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

public class UseCase9ErrorHandlingValidation {

    // Mock Database for Room Inventory
    private static Map<String, Integer> inventory = new HashMap<>();
    static {
        inventory.put("DELUXE", 2);
        inventory.put("STANDARD", 5);
    }

    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: Use Case 9 ---");

        // Scenario 1: Valid Booking
        processBooking("Guest_01", "DELUXE");

        // Scenario 2: Invalid Room Type (Validation Failure)
        processBooking("Guest_02", "PENTHOUSE");

        // Scenario 3: Inventory Depletion (System State Guarding)
        processBooking("Guest_03", "DELUXE");
        processBooking("Guest_04", "DELUXE"); // This should fail

        System.out.println("\nFinal System State: " + inventory);
    }

    /**
     * Processes a booking with Fail-Fast validation.
     */
    public static void processBooking(String guestName, String roomType) {
        System.out.println("\nProcessing booking for: " + guestName + " (" + roomType + ")");

        try {
            validateRequest(roomType);
            updateInventory(roomType);
            System.out.println("SUCCESS: Booking confirmed for " + guestName);

        } catch (InvalidBookingException e) {
            // Graceful Failure Handling
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Requirement: Validate room types and system constraints.
     */
    private static void validateRequest(String roomType) throws InvalidBookingException {
        // Validation: Check if room type exists
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }

        // Validation: Check if rooms are available (Guarding System State)
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
    }

    /**
     * Requirement: Prevent inventory from reaching invalid/negative values.
     */
    private static void updateInventory(String roomType) {
        int currentCount = inventory.get(roomType);
        inventory.put(roomType, currentCount - 1);
    }
}
