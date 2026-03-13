abstract class Room {

    protected String roomType;
    protected int beds;
    protected double size;
    protected double price;

    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : ₹" + price + " per night");
    }
}


class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 180, 2000);
    }
}


class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 300, 3500);
    }
}


class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 6000);
    }
}


public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("Version 2.0 - Room Initialization");
        System.out.println("--------------------------------------");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailability = 10;
        int doubleAvailability = 6;
        int suiteAvailability = 3;

        System.out.println("\nSingle Room Details");
        single.displayRoomDetails();
        System.out.println("Available Rooms : " + singleAvailability);

        System.out.println("\nDouble Room Details");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + doubleAvailability);

        System.out.println("\nSuite Room Details");
        suite.displayRoomDetails();
        System.out.println("Available Rooms : " + suiteAvailability);

        System.out.println("\nSystem initialized successfully.");
    }
}