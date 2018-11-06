package printers;

/**
 * Created by student10 on 2018-10-22.
 */
public class Client {

    PrinterMonitor printerMonitor;

    public Client(PrinterMonitor printerMonitor) {
        this.printerMonitor = printerMonitor;
    }

    public void run(int id) throws Exception {

        System.out.println("Client " + id + " trying to get printer..");

        Printer printer = printerMonitor.take();

        System.out.println("Client " + id + " printing");
        printer.print();

        System.out.println("Client " + id + " done printing");

        printerMonitor.post(printer);

        System.out.println("Client " + id + " returned printer");


    }
}
