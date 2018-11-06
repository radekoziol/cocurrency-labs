package self_service_shop;

import semaphore.Semaphore;

import java.util.List;
import java.util.Random;

public class Shop {

    private final List<Cart> cartList;
    private Semaphore cartSemaphore;

    public Shop(List<Cart> cartList) {
        this.cartList = cartList;
        this.cartSemaphore = new Semaphore(1, 1);
    }

    public Cart getCart() throws Exception {

        Cart cart = null;

        while (cart == null) {

            cartSemaphore.take();

                cart = cartList
                        .stream()
                        .filter(c -> c.getAvailability().equals(Availability.FREE))
                        .findFirst()
                        .orElse(null);

                if (cart == null) {
                    System.out.println("None is available. Will come later!");
                    try {
                        Thread.sleep(new Random().nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    cart.setAvailability(Availability.TAKEN);
                }

            cartSemaphore.post();

        }


        return cart;
    }


    public void returnCart(Cart cart) throws Exception {

        cartSemaphore.take();

            cartList.stream()
                    .filter(c -> c.equals(cart))
                    .findFirst()
                    .orElseThrow(Exception::new)
                    .setAvailability(Availability.FREE);

        cartSemaphore.post();
    }
}
