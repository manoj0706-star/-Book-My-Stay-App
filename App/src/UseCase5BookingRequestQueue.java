import java.util.LinkedList;
import java.util.Queue;

// Model class representing a Guest's intent to book
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation{Guest: '" + guestName + "', Room: '" + roomType + "'}";
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        // Step 1: Initialize the Booking Request Queue
        // We use LinkedList because it implements the Queue interface
        Queue<Reservation> bookingQueue = new LinkedList<>();

        System.out.println("--- Hotel Booking System: Request Intake ---");

        // Step 2: Guest submits booking requests (Arrival Order)
        System.out.println("Receiving incoming requests...");

        bookingQueue.add(new Reservation("Alice", "Deluxe"));
        bookingQueue.add(new Reservation("Bob", "Standard"));
        bookingQueue.add(new Reservation("Charlie", "Suite"));
        bookingQueue.add(new Reservation("Diana", "Deluxe"));

        // Step 3: Display the Queue status
        // At this stage, no room allocation or inventory mutation occurs.
        System.out.println("\nCurrent Queue Size: " + bookingQueue.size());
        System.out.println("Requests waiting in order of arrival:");

        for (Reservation res : bookingQueue) {
            System.out.println(" - " + res);
        }

        // Step 4: Prepare for processing (FIFO Principle)
        System.out.println("\n--- Readiness Check ---");
        if (!bookingQueue.isEmpty()) {
            System.out.println("Next request to be processed: " + bookingQueue.peek());
            System.out.println("System is ready for fair allocation based on arrival order.");
        }
    }
}