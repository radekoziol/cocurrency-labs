package buffer_pipelining;

/**
 * Created by student15 on 2018-10-08.
 */
public class Buffer {

    private final int bufferSize = 1000;
    private Object[] buffer;

    public Buffer() {
        buffer = new Object[bufferSize];
    }

    public synchronized void put(int index, Object output) {
        buffer[index] = output;
    }

    public synchronized Object read(int index) {
        return buffer[index];
    }

}
