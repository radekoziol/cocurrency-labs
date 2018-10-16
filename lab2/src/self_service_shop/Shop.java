package self_service_shop;

import semaphore.Semaphore;

import java.util.List;

public class Shop {

    private final List<Cart> cartList;
    private Semaphore cartSemaphore;

    public Shop(List<Cart> cartList) {
        this.cartList = cartList;
        this.cartSemaphore = new Semaphore(cartList.size());
    }

    public Cart getCart() throws Exception {

        Cart cart = null;

        while (cart == null) {

            synchronized (cartList) {

                cartSemaphore.take();

                cart = cartList
                        .stream()
                        .filter(c -> c.getAvailability().equals(Availability.FREE))
                        .findFirst()
                        .orElse(null);

                cartSemaphore.post();

                if (cart == null) {
                    System.out.println("None is available. Will come later!");
                    try {
                        Thread.sleep(500);
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

        synchronized (cartList) {

            cartSemaphore.take();

            cartList.stream()
                    .filter(c -> c.equals(cart))
                    .findFirst()
                    .orElseThrow(Exception::new)
                    .setAvailability(Availability.FREE);

            cartSemaphore.post();

        }

    }
}