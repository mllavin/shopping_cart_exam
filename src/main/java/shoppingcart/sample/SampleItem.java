package shoppingcart.sample;

import shoppingcart.interfaces.Item;

public enum SampleItem implements Item {

    ULT_SMALL ("ult_small", "Unlimited 1GB", 24.90f),
    ULT_MEDIUM("ult_medium", "Unlimited 2GB", 29.90f),
    ULT_LARGE("ult_large", "Unlimited 5GB", 44.90f),
    ONE_GB("1gb", "1GB Data-pack", 9.90f);

    private String code;
    private String name;
    private float price;

    SampleItem(String code, String name, float price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public boolean equals(Item item) {
        return item.getCode().equals(this.getCode()) && item.getName().equals(this.getName())
                && item.getPrice() == this.getPrice();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

}
