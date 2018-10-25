package printers;

/**
 * Created by student10 on 2018-10-22.
 */
public class Printer {

    private Availability availability;

    public Printer(Availability availability) {
        this.availability = availability;
    }

    public void print() {

    }


    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
}
