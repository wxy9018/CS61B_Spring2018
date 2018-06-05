public class LinkedListDeque<T> {
    private class dequeElement{
        private T item;
        private dequeElement prev;
        private dequeElement next;

        public dequeElement(T item, dequeElement prev, dequeElement next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private dequeElement sentinel;
    private int size;

    public LinkedListDeque() { // Empty linked list deque
        sentinel = new dequeElement(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public void addFirst(T elem) {
        dequeElement t = new dequeElement(elem, sentinel, sentinel.next);
        sentinel.next.prev = t;
        sentinel.next = t;
        size++;
    }
    public void addLast(T elem) {
        dequeElement t = new dequeElement(elem, sentinel.prev, sentinel);
        sentinel.prev.next = t;
        sentinel.prev = t;
        size ++;
    }
    public boolean isEmpty() { // return true if the deque is empty
        boolean result = (size == 0);
        return result;
    }
    public int size() {
        return size;
    }
    public void printDeque() { // Prints the items in the deque from first to last, separated by a space.
        dequeElement q = sentinel.next;
        if (!isEmpty()) {
            while (q != sentinel) {
                System.out.print(q.item + " ");
                q = q.next;
            }
            System.out.println("");
        }
    }
    public T removeFirst() {
        T result =  sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return result;
    }
    public T removeLast() {
        T result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return result;
    }
    public T get(int index) { // the available index starts from 0 and ends at size-1
        if (index > size - 1){
            System.out.println("Deque Overflow: index greater than size!");
            return null;
        }
        dequeElement p = sentinel.next;
        while(index > 0) {
            p = p.next;
            index--;
        }
        return p.item;
    }
    public T getRecursive(int index) { // recursive version of get()
        if (index > size - 1){
            System.out.println("Deque Overflow: index greater than size!");
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
    private T getRecursiveHelper(int index, dequeElement start) {
        if (index == 0) {
            return start.item;
        }
        int i = index-1;
        return getRecursiveHelper(i, start.next);
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> a = new LinkedListDeque<>();
        System.out.println(a.size());
        System.out.println(a.isEmpty());
        a.printDeque();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.printDeque();
        a.addLast(4);
        a.addLast(5);
        a.printDeque();
        System.out.println(a.size());
        System.out.println(a.isEmpty());

        int s1 = (int)a.get(0);
        int s2 = (int)a.get(a.size()-1);
        int s3 = (int)a.getRecursive(0);
        int s4 = (int)a.getRecursive(a.size()-1);
        System.out.println(s1 + " " + s2 + " " + s3 + " " + s4);

        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeLast());
        System.out.println(a.removeLast());
        a.printDeque();
        System.out.println(a.size());
        System.out.println(a.isEmpty());
        System.out.println(a.removeLast());
        System.out.println(a.size());
        for(int i = 0; i < 20; i++) {
            a.addLast(i);
        }
        for(int i = 0; i < 20; i++) {
            a.addFirst(-i);
        }
        a.printDeque();
        System.out.println(a.removeLast()); // should error out
    }
}
