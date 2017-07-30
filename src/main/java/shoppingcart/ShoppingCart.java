package shoppingcart;

import shoppingcart.interfaces.Item;
import shoppingcart.interfaces.PricingRules;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private PricingRules rules;
    private List<Item> items;
    private String appliedPromoCode;

    public ShoppingCart(PricingRules rules) {
        this.rules = rules;
        this.items = new ArrayList<>();
        this.appliedPromoCode = null;
    }

    public List<Item> items() {
        List<Item> allItems = rules.calculateFreeBundles(items);
        allItems.addAll(items);
        return allItems;
    }

    public void add(Item item) {
        items.add(item);
    }

    public void add(Item item, String promoCode) {
        if (rules.isPromoCodeValid(promoCode)) {
            this.appliedPromoCode = promoCode;
            add(item);
        }
    }

    public double total() {
        double total = rules.calculatePriceWithoutDiscount(items) - rules.calculatePerItemDiscount(items)
                - rules.calculatePromoCodeDiscount(items, appliedPromoCode);
        return Math.max(0d, total);
    }

    public String getAppliedPromoCode() {
        return appliedPromoCode;
    }

}
