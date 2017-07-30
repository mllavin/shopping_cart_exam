package shoppingcart.sample;

import shoppingcart.interfaces.PricingRules;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SamplePricingRulesConstructorTest {

    private PricingRules rules;

    @Test
    public void shouldHaveDefaultPromoCodeIfNoPromoCodesWereGiven() {
        rules = new SamplePricingRules();

        assertTrue(rules.isPromoCodeValid("I<3AMAYSIM"));
    }

    @Test
    public void shouldHavePromoCodesIfPromoCodesWereGiven() {
        String code1 = "None";
        String code2 = "Half";
        String code3 = "Free";
        Map<String, Float> promoCodes = new HashMap<>();
        promoCodes.put(code1, 0f);
        promoCodes.put(code2, 0.5f);
        promoCodes.put(code3, 1f);

        rules = new SamplePricingRules(promoCodes);

        assertTrue(rules.isPromoCodeValid(code1));
        assertTrue(rules.isPromoCodeValid(code2));
        assertTrue(rules.isPromoCodeValid(code3));
        assertFalse(rules.isPromoCodeValid("I<3AMAYSIM"));
    }

}
