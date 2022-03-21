package entity;

import org.bson.Document;

public class Item {
    String id;
    String name;
    Price price;

    public Item(String id, String name, Price currency) {
        this.id = id;
        this.name = name;
        this.price = currency;
    }

    public Item(Document document) {
        this.id = document.getString("id");
        this.name = document.getString("name");
        this.price = new Price(document.getString("currency"), document.getString("value"));
    }

    public Document toDocument() {
        final Document document = new Document();
        document.append("id", id);
        document.append("name", name);
        document.append("currency", price.currency.toString());
        document.append("value", price.value);
        return document;
    }

    public double toRoubles(Currency currency) {
        switch (currency) {
            case RUB -> {
                return 1;
            }
            case EUR -> {
                return 120;
            }
            case USD -> {
                return 100;
            }
            default -> {
                return 0;
            }
        }
    }

    public Item changeCurrency(Currency newCurrency) {
        price = new Price(newCurrency, price.value * toRoubles(price.currency) / toRoubles(newCurrency));
        return this;
    }

    public String getId() {
        return id;
    }
}
