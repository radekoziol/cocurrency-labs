package prod_cons;

/**
 * Created by student15 on 2018-10-08.
 */
public class Producer {

    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public synchronized void run(int id) throws InterruptedException {

        buffer.put(String.valueOf(id));

        System.out.println("Producer " + id + " - just put " + "message: " + id);

    }
}
