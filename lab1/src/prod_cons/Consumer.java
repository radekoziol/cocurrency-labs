package prod_cons;

import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Consumer {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public synchronized void run(int id) {

        IntStream.range(1, 3).forEach(i -> {

            String message = buffer.take();

            System.out.println("Consumer " + id + " - just read " + message);

        });

    }

}
