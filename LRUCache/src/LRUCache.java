import org.junit.Assert;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {
    private final HashMap<K, ListNode<Map.Entry<K, V>>> cache;
    private final MyLinkedList<Map.Entry<K, V>> order = new MyLinkedList<>();
    private final int capacity;

    public LRUCache(int capacity) {
        Assert.assertTrue(capacity > 0);
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
    }

    @Override
    public void put(K key, V value) {
        Assert.assertEquals(cache.size(), order.size());

        var newEntry = new AbstractMap.SimpleEntry<>(key, value);
        if (cache.containsKey(key)) {
            order.removeNode(cache.get(key));
        } else {
            if (cache.size() == capacity) {
                var removed = order.pollFirst();
                cache.remove(removed.getKey());
            }
        }
        cache.put(key, order.add(newEntry));

        Assert.assertEquals(cache.size(), order.size());
    }

    @Override
    public int size() {
        return cache.size();
    }

    public int capacity() {
        return capacity;
    }

    @Override
    public V get(K key) {
        var ans = cache.get(key);
        if (ans != null) {
            put(key, ans.get().getValue());
        }
        return ans != null ? ans.get().getValue() : null;
    }
}
