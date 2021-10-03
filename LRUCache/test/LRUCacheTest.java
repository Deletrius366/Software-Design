import org.junit.Assert;
import org.junit.Test;

public class LRUCacheTest {

    public final int defaultCap = 10;

    private String genStringValue(int i) {
        return "Value is " + i;
    }

    @Test
    public void putEqualKeysTest() {
        Cache<Integer, String> cache = new LRUCache<>(defaultCap);
        Assert.assertEquals(cache.capacity(), defaultCap);

        for (int i = 0; i < 10; i++) {
            cache.put(0, "" + i);
        }
        Assert.assertEquals(cache.size(), 1);
        Assert.assertEquals(cache.get(0), "9");
    }

    @Test
    public void putGetTest() {
        Cache<Integer, String> cache = new LRUCache<>(defaultCap);
        Assert.assertEquals(cache.capacity(), defaultCap);

        for (int i = 0; i < 10; i++) {
            cache.put(i, genStringValue(i));
        }
        Assert.assertEquals(cache.size(), 10);

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(cache.get(i), genStringValue(i));
        }
    }

    @Test
    public void orderTest() {
        Cache<Integer, String> cache = new LRUCache<>(defaultCap);
        Assert.assertEquals(cache.capacity(), defaultCap);

        for (int i = 0; i < 10; i++) {
            cache.put(i, genStringValue(i));
        }
        Assert.assertEquals(cache.size(), 10);

        for (int i = 0; i < 10; i++) {
            cache.put(i + 10, genStringValue(i + 10));
            Assert.assertNull(cache.get(i));
        }
    }

    @Test
    public void manyPutsTest() {
        Cache<Integer, String> cache = new LRUCache<>(defaultCap);
        Assert.assertEquals(cache.capacity(), defaultCap);

        for (int i = 0; i < 20; i++) {
            cache.put(i, "Value is " + i);
        }
        Assert.assertEquals(cache.size(), defaultCap);

        for (int i = 0; i < 10; i++) {
            Assert.assertNull(cache.get(i));
        }
        for (int i = 11; i < 20; i++) {
            Assert.assertEquals(cache.get(i), genStringValue(i));
        }
    }

    @Test
    public void CacheOverflowTest() {
        Cache<Integer, String> cache = new LRUCache<>(defaultCap);
        Assert.assertEquals(cache.capacity(), defaultCap);

        for (int i = 0; i < 10; i++) {
            cache.put(i, genStringValue(i));
        }
        Assert.assertEquals(cache.size(), defaultCap);

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                cache.put(i, genStringValue(i));
            }
        }

        for (int i = 10; i < 15; i++) {
            cache.put(i, genStringValue(i));
        }
        for (int i = 0; i < 15; i++) {
            if (i % 2 != 0 && i < 10) {
                Assert.assertNull(cache.get(i));
            } else {
                Assert.assertEquals(cache.get(i), genStringValue(i));
            }
        }
    }
}
