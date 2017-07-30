package shoppingcart.interfaces;

import java.util.List;

public interface PricingRules {

    double calculatePriceWithoutDiscount(List<Item> items);

    double calculatePerItemDiscount(List<Item> items);

    double calculatePromoCodeDiscount(List<Item> items, String promoCode);

    List<Item> calculateFreeBundles(List<Item> items);

    boolean isPromoCodeValid(String promoCode);

}
