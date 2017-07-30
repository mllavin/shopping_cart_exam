package shoppingcart.helper;

import shoppingcart.interfaces.Item;
import shoppingcart.sample.SampleItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestHelper {

    private static final DecimalFormat FORMATTER = new DecimalFormat("#0.00");

    public static List<Item> generateSampleItems(int ult_small, int ult_medium, int ult_large, int one_gb) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < ult_small; i++) {
            items.add(SampleItem.ULT_SMALL);
        }
        for (int i = 0; i < ult_medium; i++) {
            items.add(SampleItem.ULT_MEDIUM);
        }
        for (int i = 0; i < ult_large; i++) {
            items.add(SampleItem.ULT_LARGE);
        }
        for (int i = 0; i < one_gb; i++) {
            items.add(SampleItem.ONE_GB);
        }
        Collections.shuffle(items);
        return items;
    }

    public static String formatDecimalValueToString(double value) {
        return FORMATTER.format(value);
    }
}
