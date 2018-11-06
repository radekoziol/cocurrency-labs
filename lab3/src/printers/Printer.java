package printers;

import java.util.Random;

/**
 * Created by student10 on 2018-10-22.
 */
public class Printer {

    private Availability availability;

    public Printer(Availability availability) {
        this.availability = availability;
    }

    public void print() throws InterruptedException {
        Thread.sleep(new Random().nextInt(1000));
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
}
