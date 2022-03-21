package controller;

import entity.Price;
import entity.Currency;
import entity.Item;
import entity.User;
import reactive.ReactiveHandler;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class Controller {

    private final ReactiveHandler reactiveHandler;

    public Controller(ReactiveHandler reactiveHandler) {
        this.reactiveHandler = reactiveHandler;
    }

    public Observable<String> handleRequest(String path, Map<String, List<String>> query) {
        switch (path) {
            case "add-user" -> {
                return addUser(query);
            }
            case "add-item" -> {
                return addItem(query);
            }
            case "get-items" -> {
                return getItems(query);
            }
            default -> throw new IllegalArgumentException("Unknown request");
        }

    }

    private Observable<String> getItems(Map<String, List<String>> query) {
        String userId = query.get("id").get(0);

        return reactiveHandler.rxGetItems(userId);
    }

    private Observable<String> addUser(Map<String, List<String>> query) {
        String id = query.get("id").get(0);
        String name = query.get("name").get(0);
        String currency = query.get("currency").get(0);

        return reactiveHandler.rxAddUser(new User(id, name, currency));
    }

    private Observable<String> addItem(Map<String, List<String>> query) {
        String id = query.get("id").get(0);
        String name = query.get("name").get(0);
        String currency = query.get("currency").get(0);
        String value = query.get("value").get(0);

        return reactiveHandler.rxAddItem(new Item(id, name, new Price(Currency.valueOf(currency), Double.parseDouble(value))));
    }
}
