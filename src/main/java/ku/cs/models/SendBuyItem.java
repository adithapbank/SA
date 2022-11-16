package ku.cs.models;

public class SendBuyItem {
    private Item item;
    private int amount;

    public SendBuyItem(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

}
