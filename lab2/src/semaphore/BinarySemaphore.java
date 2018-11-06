package semaphore;

public class BinarySemaphore {

    private boolean isTaken;


    public synchronized void post() {
        isTaken = false;
        notifyAll();
    }

    public synchronized void take() {

        while (isTaken) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        isTaken = true;

    }

}
