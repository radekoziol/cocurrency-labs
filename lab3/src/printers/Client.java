package printers;

import java.util.Random;

/**
 * Created by student10 on 2018-10-22.
 */
public class Client {

    PrinterMonitor printerMonitor;

    public Client(PrinterMonitor printerMonitor) {
        this.printerMonitor = printerMonitor;
    }

    public synchronized void run(int id) throws Exception {

        System.out.println("Client " + id + " trying to get printer..");

        Printer printer = printerMonitor.take();

        try {
            System.out.println("Client " + id + " printing");
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Client " + id + " done printing");

        printerMonitor.post(printer);

        System.out.println("Client " + id + " returned printer");


    }
}
