package semaphore;

public class BinarySemaphore {

    private boolean isTaken;


    public void post() {
        isTaken = false;
    }

    public void take() {

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
