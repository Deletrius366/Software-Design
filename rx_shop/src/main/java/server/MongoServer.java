package server;

import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;

public class MongoServer {

    private static final MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("all");

    public static MongoCollection<Document> getUsersCollection() {
        return database.getCollection("users");
    }

    public static MongoCollection<Document> getItemCollection() {
        return database.getCollection("items");
    }
}
