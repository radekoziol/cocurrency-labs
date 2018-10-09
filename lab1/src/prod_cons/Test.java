package prod_cons;

import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Test {

    public static void main(String[] args) {

        Buffer buffer = new Buffer();

        Runnable producerTask = () -> {

            Consumer consumer = new Consumer(buffer);

            IntStream.range(1, 5).forEach(consumer::run);
        };

        Runnable consumerTask = () -> {

            Producer producer = new Producer(buffer);
            IntStream.range(1, 4).forEach(producer::run);
        };


        Thread producerThread = new Thread(producerTask);
        Thread consumerThread = new Thread(consumerTask);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
