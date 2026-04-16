import java.io.*;
import java.util.*;

// Essential for persistence: Classes must implement Serializable
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    String guestName;
    int roomNumber;

    public Booking(String guestName, int roomNumber) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomNumber;
    }
}

class HotelState implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<Integer, Integer> inventory; // RoomType -> Count
    List<Booking> bookingHistory;

    public HotelState(Map<Integer, Integer> inventory, List<Booking> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

public class UseCase12DataPersistenceRecovery {
    private static final String STORAGE_FILE = "hotel_state.ser";

    public static void main(String[] args) {
        // Initial state
        Map<Integer, Integer> currentInventory = new HashMap<>();
        List<Booking> currentBookings = new ArrayList<>();

        // 1. Attempt System Recovery
        System.out.println("--- System Startup: Attempting Recovery ---");
        HotelState restoredState = loadState();

        if (restoredState != null) {
            currentInventory = restoredState.inventory;
            currentBookings = restoredState.bookingHistory;
            System.out.println("Success: State recovered from persistent storage.");
        } else {
            System.out.println("No previous state found. Initializing fresh system.");
            // Default setup for a new system
            currentInventory.put(101, 1); // Room 101 available
            currentInventory.put(102, 1); // Room 102 available
        }

        // 2. Display Current State
        displayState(currentInventory, currentBookings);

        // 3. Simulate System Activity
        System.out.println("\n--- Processing New Booking ---");
        if (currentInventory.containsKey(101) && currentInventory.get(101) > 0) {
            currentBookings.add(new Booking("Alice Smith", 101));
            currentInventory.put(101, 0); // Mark room as occupied
            System.out.println("Booking confirmed for Alice Smith.");
        }

        // 4. System Shutdown and Data Persistence
        System.out.println("\n--- System Shutdown: Persisting Data ---");
        saveState(new HotelState(currentInventory, currentBookings));

        System.out.println("System terminated safely.");
    }

    /**
     * Serializes the current state into a file.
     */
    private static void saveState(HotelState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(state);
            System.out.println("System state saved to " + STORAGE_FILE);
        } catch (IOException e) {
            System.err.println("Error during persistence: " + e.getMessage());
        }
    }

    /**
     * Deserializes data from the file to restore state.
     */
    private static HotelState loadState() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            return (HotelState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Recovery failed or file corrupted: " + e.getMessage());
            return null;
        }
    }

    private static void displayState(Map<Integer, Integer> inv, List<Booking> bookings) {
        System.out.println("\n--- Current System State ---");
        System.out.println("Inventory: " + inv);
        System.out.println("Bookings: " + (bookings.isEmpty() ? "None" : bookings));
    }
}
