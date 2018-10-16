package semaphore;

public class Semaphore {

    private Integer counter;

    public Semaphore(int counter) {
        this.counter = counter;
    }

    public void post() {

        synchronized (counter) {
            counter++;
        }

    }

    public void take() {

        synchronized (counter) {

            while (counter < 0) {

                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            counter--;

        }


    }


}
