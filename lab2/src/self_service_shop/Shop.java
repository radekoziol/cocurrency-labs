package self_service_shop;

import semaphore.Semaphore;

import java.util.List;
import java.util.Random;

public class Shop {

    private final List<Cart> cartList;
    private Semaphore newClientsSemaphore;
    private Semaphore shoppingClientsSemaphore;
    private Semaphore cartSemaphore;

    public Shop(List<Cart> cartList) {
        this.cartList = cartList;
        this.newClientsSemaphore = new Semaphore(0, 100);
        this.shoppingClientsSemaphore = new Semaphore(0, 20);
//        this.cartSemaphore= new Semaphore(0, 1);
    }

    public Cart getCart() throws Exception {

        Cart cart = null;

        while (cart == null) {


            newClientsSemaphore.take();

            synchronized (cartList) {

                cart = cartList
                        .stream()
                        .filter(c -> c.getAvailability().equals(Availability.FREE))
                        .findFirst()
                        .orElse(null);

                newClientsSemaphore.post();

                if (cart == null) {
                    System.out.println("None is available. Will come later!");
                    try {
                        Thread.sleep(new Random().nextInt(2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    cart.setAvailability(Availability.TAKEN);
                }
            }

        }


        return cart;
    }


    public void returnCart(Cart cart) throws Exception {


        shoppingClientsSemaphore.take();

        synchronized (cartList) {

            cartList.stream()
                    .filter(c -> c.equals(cart))
                    .findFirst()
                    .orElseThrow(Exception::new)
                    .setAvailability(Availability.FREE);
        }

        shoppingClientsSemaphore.post();



    }
}
