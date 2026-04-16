import java.util.ArrayList;
import java.util.List;

// 1. Reservation Model
class Reservation {
    private String bookingId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String bookingId, String guestName, String roomType, double price) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Guest: %s | Room: %s | Price: $%.2f",
                bookingId, guestName, roomType, price);
    }

    public double getPrice() { return price; }
}

// 2. Booking History (Persistence-oriented storage)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addRecord(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllRecords() {
        // Returning a copy to ensure reporting does not modify stored data
        return new ArrayList<>(history);
    }
}

// 3. Booking Report Service (Operational Visibility)
class BookingReportService {
    public void generateSummaryReport(List<Reservation> records) {
        System.out.println("\n--- Operational Booking Report ---");
        double totalRevenue = 0;

        if (records.isEmpty()) {
            System.out.println("No bookings found in history.");
            return;
        }

        for (Reservation res : records) {
            System.out.println(res);
            totalRevenue += res.getPrice();
        }

        System.out.println("----------------------------------");
        System.out.println("Total Bookings: " + records.size());
        System.out.println("Total Revenue:  $" + totalRevenue);
        System.out.println("----------------------------------\n");
    }
}

// 4. Main Application
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.println("System: Processing and confirming bookings...");

        // Simulating the confirmation flow
        Reservation res1 = new Reservation("BK001", "Alice Smith", "Deluxe", 150.00);
        history.addRecord(res1);

        Reservation res2 = new Reservation("BK002", "Bob Jones", "Suite", 300.00);
        history.addRecord(res2);

        Reservation res3 = new Reservation("BK003", "Charlie Brown", "Standard", 100.00);
        history.addRecord(res3);

        // Admin requests a report
        System.out.println("Admin: Requesting Booking History Report...");
        reportService.generateSummaryReport(history.getAllRecords());
    }
}