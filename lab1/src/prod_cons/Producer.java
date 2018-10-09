package prod_cons;

import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Producer {

    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public synchronized void run(int id) {

        IntStream.range(1, 3).forEach(i -> {

            buffer.put("mssg" + id);

            System.out.println("Producer " + id + " - just put " + "message" + id);

        });

    }
}
