package semaphore;

public class Semaphore {

    private Integer counter;
    private Integer limit;

    public Semaphore(int counter, Integer limit) {
        this.counter = counter;
        this.limit = limit;
    }

    public synchronized void post() {
        counter++;
        notifyAll();
    }

    public synchronized void take() {

        while (counter == 0 && counter < limit) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        counter--;
    }


}
