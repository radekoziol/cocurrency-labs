package prod_cons;

import semaphore.BinarySemaphore;

/**
 * Created by student15 on 2018-10-08.
 */
public class Buffer {

    public String message;
    private BinarySemaphore binarySemaphore;

    public Buffer(BinarySemaphore binarySemaphore) {
        this.binarySemaphore = binarySemaphore;
    }

    public synchronized void put(String input) {

        binarySemaphore.take();

        if (message == null)
            message = input;

        binarySemaphore.post();

        notifyAll();
    }

    public synchronized String read() {

        binarySemaphore.take();

        String output = message;
        setEmpty();

        binarySemaphore.post();

        notifyAll();

        return output;
    }

    public void setEmpty() {
        message = null;
    }

}
