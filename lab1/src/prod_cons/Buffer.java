package prod_cons;

import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Buffer {

    public String message;


    public synchronized void put(String input) {

        while (!isEmpty()) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        message = input;
        notifyAll();
    }

    public synchronized String take() {

        while (this.isEmpty()) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        String output = message;
        setEmpty();

        notifyAll();

        return output;
    }

    public void setEmpty() {
        message = null;
    }

    public Boolean isEmpty() {
        return message == null;
    }

}
