package basic_buffer_pipelining;

import java.util.Arrays;

/**
 * Created by student15 on 2018-10-08.
 */
public class Buffer {

    public static final int bufferSize = 100;
    private Object[] buffer;

    public Buffer() {
        buffer = new Object[bufferSize];
    }

    public synchronized void put(int index, Object output) {
        buffer[index] = output;
    }

    public synchronized Object read(int index) {

        Object returnObject = buffer[index];
        buffer[index] = null;

        return returnObject;
    }


    public boolean isEmpty() {
        return Arrays.asList(buffer).isEmpty();
    }
}
