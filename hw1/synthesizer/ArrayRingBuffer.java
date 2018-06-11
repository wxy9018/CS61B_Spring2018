// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        try {
            if (fillCount >= capacity) {
                throw new RuntimeException("RingBufferOverflow");
            }
            rb[last] = x;
            fillCount += 1;
            last += 1;
            if (last == this.capacity) {
                last = 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // that's how you catch a specific exception according to its message.
            // No matter how many types of exceptions you will throw in the try statement, you'll catch it here and distinguish the cause.
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        try {
            if (fillCount <= 0) {
                throw new RuntimeException("RingBufferEmpty");
            }
            T result = rb[first];
            first += 1;
            if (first == this.capacity) {
                first = 0;
            }
            fillCount -= 1;
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.

    @Override
    public Iterator<T> iterator() {
        return new keyIterator();
    }

    private class keyIterator implements Iterator<T> {
        private int ptr;
        public keyIterator() {
            ptr = 0;
        }
        public boolean hasNext() {
            return (ptr != rb.length);
        }
        public T next() {
            T returnItem = rb[ptr];
            ptr = ptr + 1;
            return returnItem;
        }
    }
}
