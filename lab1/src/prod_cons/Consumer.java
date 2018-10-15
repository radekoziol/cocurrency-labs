package prod_cons;

/**
 * Created by student15 on 2018-10-08.
 */
public class Consumer {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public synchronized void run(int id) {

        String message = null;
        while (message == null) {
            message = buffer.take();
        }

        System.out.println("Consumer " + id + " - just read message: " + message);
    }

}
