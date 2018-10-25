package printers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Created by student10 on 2018-10-22.
 */
public class Test {

    final Lock lock = new ReentrantLock();


    public static void main(String[] args) {

        List<Printer> printerList = new ArrayList<>();

        IntStream.range(1, 4).forEach(i -> {
            printerList.add(new Printer(Availability.FREE));
        });

        PrinterMonitor printerMonitor = new PrinterMonitor(printerList);
        List<Runnable> clientTasks = new ArrayList<>();

        IntStream.range(1, 16).forEach(i -> {
            clientTasks.add(() -> {
                Client client = new Client(printerMonitor);
                try {
                    client.run(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        List<Thread> clientThreads = new ArrayList<>();

        clientTasks.forEach(t -> clientThreads.add(new Thread(t)));

        clientThreads.forEach(Thread::start);


        clientThreads.forEach(t -> {
            try {
                t.join(10000);
                t.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


}
