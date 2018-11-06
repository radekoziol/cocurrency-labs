package prod_cons;

import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Producer {

    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run(int id) throws InterruptedException {

        IntStream.range(4, 10).forEach(i -> {
            try {
                buffer.put(String.valueOf(id * i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Producer " + id + " - just put " + "message: " + id * i);
        });

    }
}
