// ───────────────────────────────────────────────────────────
// ENUMS
// ───────────────────────────────────────────────────────────

enum VehicleType {
    BIKE, SEDAN, SUV, AUTO
}

enum RideStatus {
    REQUESTED, CONFIRMED, DRIVER_ON_THE_WAY, IN_PROGRESS, COMPLETED, CANCELLED
}

// ───────────────────────────────────────────────────────────
// Location Model
// ───────────────────────────────────────────────────────────

class Location {
    private final String name;
    private final double latitude;
    private final double longitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double distanceTo(Location other) {
        double dx = this.latitude - other.latitude;
        double dy = this.longitude - other.longitude;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}

// ───────────────────────────────────────────────────────────
// Abstract User Class
// ───────────────────────────────────────────────────────────

abstract class User {
    protected String name;
    protected String phone;

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
}

// ───────────────────────────────────────────────────────────
// Rider Class
// ───────────────────────────────────────────────────────────

class Rider extends User {
    private Location location;

    public Rider(String name, String phone, Location location) {
        super(name, phone);
        this.location = location;
    }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
}

// ───────────────────────────────────────────────────────────
// Driver Class
// ───────────────────────────────────────────────────────────

class Driver extends User {
    private Location location;
    private boolean available;
    private VehicleType vehicleType;
    private double rating;

    public Driver(String name, String phone, Location location, VehicleType vehicleType, double rating) {
        super(name, phone);
        this.location = location;
        this.available = true;
        this.vehicleType = vehicleType;
        this.rating = rating;
    }

    public Location getLocation() { return location; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public VehicleType getVehicleType() { return vehicleType; }
    public double getRating() { return rating; }
    public void setLocation(Location location) { this.location = location; }
    public void setRating(double rating) { this.rating = rating; }
}

// ───────────────────────────────────────────────────────────
// Ride Class
// ───────────────────────────────────────────────────────────

import java.util.UUID;

class Ride {
    private final UUID rideId;
    private final Rider rider;
    private final Location pickupLocation;
    private final Location dropLocation;
    private final VehicleType vehicleType;
    private Driver driver;
    private RideStatus status;
    private double fare;

    public Ride(Rider rider, Location pickup, Location drop, VehicleType vehicleType) {
        this.rideId = UUID.randomUUID();
        this.rider = rider;
        this.pickupLocation = pickup;
        this.dropLocation = drop;
        this.vehicleType = vehicleType;
        this.status = RideStatus.REQUESTED;
    }

    public void assignDriver(Driver driver) {
        this.driver = driver;
        this.status = RideStatus.CONFIRMED;
        driver.setAvailable(false);
    }

    public void updateStatus(RideStatus status) {
        this.status = status;
    }

    public void calculateFare() {
        double distance = pickupLocation.distanceTo(dropLocation);
        this.fare = 50 + 10 * distance;
    }

    public void completeRide() {
        updateStatus(RideStatus.COMPLETED);
        calculateFare();
        driver.setAvailable(true);
    }

    public UUID getRideId() { return rideId; }
    public RideStatus getStatus() { return status; }
    public double getFare() { return fare; }
    public Driver getDriver() { return driver; }
}

// ───────────────────────────────────────────────────────────
// Main Simulation Class
// ───────────────────────────────────────────────────────────

public class Main {
    public static void main(String[] args) {
        Location cityCenter = new Location("City Center", 0, 0);
        Location suburb = new Location("Suburb", 5, 5);
        Location remoteArea = new Location("Remote Area", 100, 100);

        Driver driver1 = new Driver("Alice", "111-222", cityCenter, VehicleType.SEDAN, 4.9);
        Driver driver2 = new Driver("Bob", "333-444", suburb, VehicleType.SUV, 4.7);

        Rider rider1 = new Rider("Charlie", "555-666", cityCenter);
        Rider rider2 = new Rider("Dave", "777-888", remoteArea);

        System.out.println("\n=== Simulated Ride Requests ===");
        Ride ride1 = new Ride(rider1, cityCenter, suburb, VehicleType.SEDAN);
        ride1.assignDriver(driver1);
        ride1.updateStatus(RideStatus.DRIVER_ON_THE_WAY);
        ride1.updateStatus(RideStatus.IN_PROGRESS);
        ride1.completeRide();
        System.out.println("[Ride1] Status: " + ride1.getStatus());
        System.out.println("[Ride1] Fare: Rs." + ride1.getFare());

        System.out.println("\n[Simulation] Rider 2 requesting a ride in Remote Area: Expected to fail");
        if (!driver1.isAvailable() && !driver2.isAvailable()) {
            System.out.println("No available drivers. Ride cannot be assigned.");
        } else {
            System.out.println("Matching available driver...");
        }
    }
}
