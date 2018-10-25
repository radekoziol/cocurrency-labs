package prod_cons;

/**
 * Created by student15 on 2018-10-08.
 */
public class Consumer {

    private BoundedBuffer buffer;

    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public synchronized void run(int id) throws InterruptedException {

        String message = null;
        while (message == null) {
            message = (String) buffer.take();
        }

        System.out.println("Consumer " + id + " - just read message: " + message);
    }

}
