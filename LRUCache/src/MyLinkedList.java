import org.junit.Assert;

public class MyLinkedList<V> {
    ListNode<V> head;
    ListNode<V> tail;
    private int size = 0;

    private void assertListInvariant() {
        if (size == 0) {
            Assert.assertNull(head);
            Assert.assertNull(tail);
        } else {
            Assert.assertNull(head.getPrev());
            Assert.assertNull(tail.getNext());
        }
        if (size == 1) {
            Assert.assertEquals(head, tail);
        }
    }

    public ListNode<V> add(V value) {
        ListNode<V> newTail = new ListNode<>(value);
        if (head == null) {
            head = newTail;
            tail = newTail;
        } else {
            tail.setNext(newTail);
            newTail.setPrev(tail);
            tail = newTail;
        }
        size++;
        assertListInvariant();
        return newTail;
    }

    public void removeNode(ListNode<V> node) {
        ListNode<V> prev = node.getPrev();
        ListNode<V> next = node.getNext();
        if (prev != null) {
            prev.setNext(next);
        } else {
            head = next;
        }
        if (next != null) {
            next.setPrev(prev);
        } else {
            tail = prev;
        }
        size--;
        assertListInvariant();
    }

    public V pollFirst() {
        V ans = head.get();
        head = head.getNext();
        if (head != null) {
            head.setPrev(null);
        }
        size--;
        assertListInvariant();
        return ans;
    }

    public V pollLast() {
        V ans = tail.get();
        tail = tail.getPrev();
        tail.setNext(null);
        assertListInvariant();
        return ans;
    }

    public int size() {
        return size;
    }
}
