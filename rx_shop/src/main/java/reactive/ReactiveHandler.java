package reactive;

import com.mongodb.rx.client.MongoCollection;
import entity.Item;
import entity.User;
import org.bson.Document;
import rx.Observable;
import rx.functions.Func1;
import server.MongoServer;

public class ReactiveHandler {

    private final MongoCollection<Document> users = MongoServer.getUsersCollection();
    private final MongoCollection<Document> items = MongoServer.getItemCollection();

    public Observable<Document> filteredObservable(MongoCollection<Document> documents, Func1<Document, Boolean> predicate) {
        return documents.find().toObservable()
                .filter(predicate)
                .switchIfEmpty(Observable.error(new IllegalArgumentException("No such element")));
    }

    public Observable<String> rxAddUser(User user) {
        return filteredObservable(users, document -> document.getString("id").equals(user.getId()))
                .flatMap(t -> users.insertOne(user.toDocument())
                        .map(Object::toString));
    }

    public Observable<String> rxAddItem(Item item) {
        return filteredObservable(items, document -> document.getString("id").equals(item.getId()))
                .flatMap(t -> items.insertOne(item.toDocument())
                        .map(Object::toString));
    }

    public Observable<String> rxGetItems(String userId) {
        return filteredObservable(items, document -> document.getString("id").equals(userId))
                .map(User::new)
                .flatMap(user -> items.find().toObservable()
                        .map(document -> new Item(document).changeCurrency(user.getCurrency()).toString()));
    }
}
