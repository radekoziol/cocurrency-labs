package printers;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by student10 on 2018-10-22.
 */
public class PrinterMonitor {

    private final List<Printer> printerList;

    final Lock lock = new ReentrantLock();
    final Condition allAreTaken = lock.newCondition();


    public PrinterMonitor(List<Printer> printerList) {
        this.printerList = printerList;
    }


    public synchronized Printer take() throws InterruptedException {

        lock.lock();

        Printer freePrinter = null;

        try {

            while (freePrinter == null) {

                freePrinter = printerList
                        .stream()
                        .filter(c -> c.getAvailability().equals(Availability.FREE))
                        .findFirst()
                        .orElse(null);

                if (freePrinter == null) {
                    allAreTaken.await();
                } else {
                    freePrinter.setAvailability(Availability.TAKEN);
                }
            }


        } finally {
            lock.unlock();
        }

        return freePrinter;

    }


    public synchronized void post(Printer printer) throws Exception {
        lock.lock();
        try {

            printerList.stream()
                    .filter(c -> c.equals(printer))
                    .findFirst()
                    .orElseThrow(Exception::new)
                    .setAvailability(Availability.FREE);


            allAreTaken.signal();
        } finally {
            lock.unlock();
        }
    }

}
