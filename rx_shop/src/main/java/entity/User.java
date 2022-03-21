package entity;

import org.bson.Document;

public class User {
    String id;
    String name;
    Currency currency;

    public User(String id, String name, String currency) {
        this.id = id;
        this.name = name;
        this.currency = Currency.valueOf(currency);
    }

    public User(Document document) {
        this.id = document.getString("id");
        this.name = document.getString("name");
        this.currency = Currency.valueOf(document.getString("currency"));
    }

    public Document toDocument() {
        final Document document = new Document();
        document.append("id", id);
        document.append("name", name);
        document.append("currency", currency.toString());
        return document;
    }

    public String getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }
}
