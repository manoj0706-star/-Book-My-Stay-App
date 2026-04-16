import java.util.LinkedList;
import java.util.Queue;

class BookingRequest {
    String guestName;
    int roomId;

    public BookingRequest(String guestName, int roomId) {
        this.guestName = guestName;
        this.roomId = roomId;
    }
}

class BookingProcessor extends Thread {
    private static final Queue<BookingRequest> requestQueue = new LinkedList<>();
    private static final boolean[] rooms = new boolean[5]; // false = available, true = booked

    public static void addRequest(BookingRequest request) {
        synchronized (requestQueue) {
            requestQueue.add(request);
        }
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request = null;

            // Critical Section: Accessing the shared queue
            synchronized (requestQueue) {
                if (!requestQueue.isEmpty()) {
                    request = requestQueue.poll();
                } else {
                    break; // No more requests to process
                }
            }

            if (request != null) {
                processBooking(request);
            }
        }
    }

    private void processBooking(BookingRequest request) {
        // Critical Section: Accessing and modifying the shared room inventory
        synchronized (rooms) {
            if (!rooms[request.roomId]) {
                // Simulate processing time
                try { Thread.sleep(100); } catch (InterruptedException e) {}

                rooms[request.roomId] = true;
                System.out.println("✅ Success: Room " + request.roomId + " booked for " + request.guestName);
            } else {
                System.out.println("❌ Failure: Room " + request.roomId + " is already occupied! (Requested by " + request.guestName + ")");
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Starting Concurrent Booking Simulation ---");

        // Simulating multiple guests submitting requests simultaneously
        BookingProcessor.addRequest(new BookingRequest("Alice", 1));
        BookingProcessor.addRequest(new BookingRequest("Bob", 1)); // Double booking attempt
        BookingProcessor.addRequest(new BookingRequest("Charlie", 2));
        BookingProcessor.addRequest(new BookingRequest("David", 2)); // Double booking attempt
        BookingProcessor.addRequest(new BookingRequest("Eve", 3));

        // Create multiple processor threads to simulate concurrent handling
        Thread thread1 = new BookingProcessor();
        Thread thread2 = new BookingProcessor();

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("--- Simulation Complete ---");
    }
}
