package shoppingcart.sample;

import shoppingcart.interfaces.Item;
import shoppingcart.interfaces.PricingRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplePricingRules implements PricingRules {

    private Map<String, Float> promoCodes;

    public SamplePricingRules() {
        promoCodes = new HashMap<>();
        promoCodes.put("I<3AMAYSIM", 0.10f);
    }

    public SamplePricingRules(Map<String, Float> promoCodes) {
        this.promoCodes = promoCodes;
    }

    @Override
    public double calculatePriceWithoutDiscount(List<Item> items) {
        double price = 0d;
        for (Item item : items) {
            price+= item.getPrice();
        }
        return price;
    }

    @Override
    public double calculatePerItemDiscount(List<Item> items) {
        double discount = 0d;
        int ult_small = 0;
        int ult_large = 0;
        for (Item item : items) {
            if (SampleItem.ULT_SMALL.equals(item)) {
                if (++ult_small % 3 == 0) {
                    discount+= item.getPrice();
                }
            }
            else if (SampleItem.ULT_LARGE.equals(item)) {
                ult_large++;
            }
        }
        if (ult_large > 3) {
            discount+= 5d * ult_large;
        }
        return discount;
    }

    @Override
    public double calculatePromoCodeDiscount(List<Item> items, String promoCode) {
        double discount = 0d;
        if (promoCodes.containsKey(promoCode)) {
            discount = promoCodes.get(promoCode);
        }
        return discount * (calculatePriceWithoutDiscount(items) - calculatePerItemDiscount(items));
    }

    @Override
    public List<Item> calculateFreeBundles(List<Item> items) {
        List<Item> bundles = new ArrayList<>();
        for (Item item : items) {
            if (SampleItem.ULT_MEDIUM.equals(item)) {
                bundles.add(SampleItem.ONE_GB);
            }
        }
        return bundles;
    }

    public boolean isPromoCodeValid(String promoCode) {
        return promoCodes.containsKey(promoCode);
    }

}
