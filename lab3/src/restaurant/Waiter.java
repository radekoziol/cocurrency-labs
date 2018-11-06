package restaurant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by student10 on 2018-11-05.
 */
public class Waiter {

    private final Lock lock = new ReentrantLock();
    private int counter;
    private Map<Person, Condition> restaurantQueue = new ConcurrentHashMap<>();
    private DoubleTable table;

    public Waiter(DoubleTable table) {
        this.table = table;
        counter = 2;
    }

    public void take(Person person) throws InterruptedException {

        restaurantQueue.put(person, lock.newCondition());

        try {

            lock.lock();

            while (person.isWaiting()) {

                if (restaurantQueue.containsKey(person.getPartner()) &&
                        (!person.getPartner().isWaiting() || table.getAvailability().equals(restaurant.Availability.FREE))) {
                    restaurantQueue.get(person.getPartner()).signal();
                    person.setWaiting(false);
                    table.setAvailability(Availability.TAKEN);
                } else {
                    restaurantQueue.get(person).await();
                }
            }

        } finally {
            lock.unlock();
        }

    }

    public void post(Person person) {

        lock.lock();

        restaurantQueue.remove(person);

        counter--;

        if (counter == 0) {
            counter = 2;
            table.setAvailability(Availability.FREE);
            wakeRandomPerson();
        }

        lock.unlock();
    }

    private void wakeRandomPerson() {

        restaurantQueue.entrySet().stream()
                .findAny()
                .ifPresent(p -> {
                    p.getValue().signal();
                });
    }
}
