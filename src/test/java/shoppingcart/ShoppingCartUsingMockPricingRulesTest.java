package shoppingcart;

import shoppingcart.interfaces.Item;
import shoppingcart.interfaces.PricingRules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import shoppingcart.sample.SampleItem;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartUsingMockPricingRulesTest {

    private static final int ZERO_DELTA = 0;

    @Mock
    private PricingRules mockRules;

    private ShoppingCart cart;

    @Before
    public void setUp() {
        cart = new ShoppingCart(mockRules);
    }

    @Test
    public void shouldHaveNoItemsAtStart() {
        assertEquals(cart.items().size(), 0);
    }

    @Test
    public void shouldHaveNoPromoCodeAtStart() {
        assertNull(cart.getAppliedPromoCode());
    }

    @Test
    public void shouldBeAbleToAddItems() {
        SampleItem item1 = SampleItem.ULT_SMALL;
        SampleItem item2 = SampleItem.ULT_MEDIUM;
        SampleItem item3 = SampleItem.ULT_LARGE;

        cart.add(item1);
        cart.add(item2);
        cart.add(item3);

        assertTrue(cart.items().contains(item1));
        assertTrue(cart.items().contains(item2));
        assertTrue(cart.items().contains(item3));
        assertEquals(cart.items().size(), 3);
    }

    @Test
    public void shouldBeAbleToAddItemWithValidPromoCode() {
        SampleItem item = SampleItem.ULT_SMALL;
        String promoCode = "code";
        when(mockRules.isPromoCodeValid(promoCode)).thenReturn(true);

        cart.add(item, promoCode);

        assertTrue(cart.items().contains(item));
        assertEquals(cart.items().size(), 1);
        assertTrue(cart.getAppliedPromoCode().equals(promoCode));
    }

    @Test
    public void shouldNotBeAbleToAddItemWithInvalidPromoCode() {
        cart.add(SampleItem.ONE_GB, "code");

        assertEquals(cart.items().size(), 0);
        assertNull(cart.getAppliedPromoCode());
    }

    @Test
    public void shouldOverridePromoCodeWhenAddingAnotherValidPromoCode() {
        String oldPromoCode = "old code";
        String newPromoCode = "new code";
        when(mockRules.isPromoCodeValid(oldPromoCode)).thenReturn(true);
        when(mockRules.isPromoCodeValid(newPromoCode)).thenReturn(true);
        cart.add(SampleItem.ULT_SMALL, oldPromoCode);
        assertEquals(oldPromoCode, cart.getAppliedPromoCode());

        cart.add(SampleItem.ULT_LARGE, newPromoCode);

        assertEquals(newPromoCode, cart.getAppliedPromoCode());
    }

    @Test
    public void shouldBeAbleToGetTotalWithoutPromoCode() {
        SampleItem item1 = SampleItem.ULT_SMALL;
        SampleItem item2 = SampleItem.ULT_MEDIUM;
        setUpTotalReturned(Arrays.asList(item1, item2), 1.23d, 0.45d);

        cart.add(item1);
        cart.add(item2);

        assertEquals(0.78d, cart.total(), ZERO_DELTA);
    }

    @Test
    public void shouldBeAbleToGetTotalWithPromoCode() {
        String promoCode = "code";
        SampleItem item1 = SampleItem.ULT_LARGE;
        SampleItem item2 = SampleItem.ONE_GB;
        setUpTotalReturned(Arrays.asList(item1, item2), promoCode, 1.23d, 0.04d, 0.05d);

        cart.add(item1);
        cart.add(item2, promoCode);

        assertEquals(1.14d, cart.total(), ZERO_DELTA);
    }

    @Test
    public void shouldBeAbleToGetTotalAsZeroIfDiscountIsGreaterThanPrice() {
        String promoCode = "free";
        SampleItem item1 = SampleItem.ULT_SMALL;
        SampleItem item2 = SampleItem.ULT_MEDIUM;
        setUpTotalReturned(Arrays.asList(item1, item2), promoCode, 4.32d, 0.98d, 7.65d);

        cart.add(item1, promoCode);
        cart.add(item2);

        assertEquals(0d, cart.total(), ZERO_DELTA);
    }

    private void setUpTotalReturned(List<Item> items, double priceWithoutDiscount, double perItemDiscount) {
        when(mockRules.calculatePriceWithoutDiscount(items)).thenReturn(priceWithoutDiscount);
        when(mockRules.calculatePerItemDiscount(items)).thenReturn(perItemDiscount);
    }

    private void setUpTotalReturned(List<Item> items, String promoCode,
                                    double priceWithoutDiscount, double perItemDiscount, double promoCodeDiscount) {
        setUpTotalReturned(items, priceWithoutDiscount, perItemDiscount);
        when(mockRules.isPromoCodeValid(promoCode)).thenReturn(true);
        when(mockRules.calculatePromoCodeDiscount(items, promoCode)).thenReturn(promoCodeDiscount);
    }

}