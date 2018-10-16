package self_service_shop;

public class Cart {

    private Availability availability;
    private String name;

    public Cart(Availability availability, String name) {
        this.availability = availability;
        this.name = name;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Cart)
            return name.equals(obj.toString());

        return false;
    }
}
