import java.io.*;
import java.util.*;

class Room {
    int number;
    String type;
    boolean booked;

    public Room(int number, String type) {
        this.number = number;
        this.type = type;
        this.booked = false;
    }

    public String toString() {
        return "Room " + number + " (" + type + ") - " + (booked ? "Booked" : "Available");
    }
}

public class HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static String file = "bookings.txt";

    public static void main(String[] args) {
        loadRooms();
        int choice;
        do {
            System.out.println("\n1. View Rooms\n2. Book Room\n3. Cancel Booking\n4. Exit");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> saveBookings();
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }

    static void loadRooms() {
        for (int i = 1; i <= 3; i++) rooms.add(new Room(i, "Standard"));
        for (int i = 4; i <= 5; i++) rooms.add(new Room(i, "Deluxe"));
        for (int i = 6; i <= 6; i++) rooms.add(new Room(i, "Suite"));

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                int roomNum = Integer.parseInt(line.trim());
                rooms.get(roomNum - 1).booked = true;
            }
        } catch (IOException e) {
            System.out.println("Booking file not found. Starting fresh.");
        }
    }

    static void viewRooms() {
        for (Room r : rooms) System.out.println(r);
    }

    static void bookRoom() {
        System.out.print("Enter room number to book: ");
        int roomNum = sc.nextInt();
        Room room = rooms.get(roomNum - 1);

        if (room.booked) {
            System.out.println("Room already booked.");
        } else {
            System.out.println("Payment required: ₹" + getPrice(room.type));
            System.out.print("Enter card number (simulate payment): ");
            sc.next(); // fake card
            room.booked = true;
            System.out.println("Room booked successfully!");
        }
    }

    static void cancelBooking() {
        System.out.print("Enter room number to cancel: ");
        int roomNum = sc.nextInt();
        Room room = rooms.get(roomNum - 1);

        if (!room.booked) {
            System.out.println("Room is not booked.");
        } else {
            room.booked = false;
            System.out.println("Booking cancelled.");
        }
    }

    static int getPrice(String type) {
        return switch (type) {
            case "Standard" -> 1500;
            case "Deluxe" -> 2500;
            case "Suite" -> 4000;
            default -> 1000;
        };
    }

    static void saveBookings() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Room r : rooms) {
                if (r.booked) bw.write(r.number + "\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to save bookings.");
        }
    }
}
