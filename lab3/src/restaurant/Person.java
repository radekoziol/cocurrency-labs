package restaurant;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by student10 on 2018-11-05.
 */
public class Person {

    private final String name;
    private final Waiter waiter;
    private Person partner;
    private boolean isWaiting;
    final Lock lock = new ReentrantLock();
    private Condition isReady = lock.newCondition();

    public Person(String name, Waiter waiter, Person partner) {
        this.name = name;
        this.waiter = waiter;
        this.isWaiting = true;
        this.partner = partner;
    }

    public void run(int id) throws InterruptedException {

        System.out.println(getName() + " trying to get table with " +
                partner.getName());

        waiter.take(this);

        try {
            System.out.println(getName() + " eating with " +
                    partner.getName());
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(getName() + " done eating with " +
                partner.getName());

        waiter.post(this);

    }

    public Person getPartner() {
        return partner;
    }

    public void setPartner(Person partner) {
        this.partner = partner;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
    }

    public String getName() {
        return name;
    }

    public Condition getIsReady() {
        return isReady;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            return ((Person) o).getName().equals(name);
        }
        return false;
    }

    public void notifyPartner() {

    }
}
