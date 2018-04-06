package BE.models.user;

public class AvailabilityModel {

    private boolean available;

    public AvailabilityModel(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
