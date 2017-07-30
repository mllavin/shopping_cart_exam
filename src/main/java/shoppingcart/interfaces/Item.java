package shoppingcart.interfaces;

public interface Item {

    String getCode();

    String getName();

    float getPrice();

    boolean equals(Item item);

}
