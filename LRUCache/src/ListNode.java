public class ListNode<V> {
    private V value;
    private ListNode<V> prev;
    private ListNode<V> next;

    public ListNode(V value) {
        this.value = value;
        prev = null;
        next = null;
    }

    public V get() {
        return value;
    }

    public void set(V v) {
        value = v;
    }

    public ListNode<V> getNext() {
        return next;
    }

    public ListNode<V> getPrev() {
        return prev;
    }

    public void setNext(ListNode<V> n) {
        next = n;
    }

    public void setPrev(ListNode<V> p) {
        prev = p;
    }
}
