package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;


/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        double[] target = new double[10];
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer(10);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i * 1.1);
            target[i] = i * 1.1;
        }
        assertEquals(10, arb.capacity());
        assertEquals(10, arb.fillCount());
        double[] x = new double[10];
        int[] result = new int[10];
        for (int i = 0; i < 10; i++) {
            x[i] = arb.dequeue();
            if (Math.abs(x[i] - target[i]) < 0.001) {
                result[i] = 0;
            } else {
                result[i] = 1;
            }
        }
        assertEquals(10, arb.capacity());
        assertEquals(0, arb.fillCount());
        assertArrayEquals(new int[]{0,0,0,0,0,0,0,0,0,0}, result); // there is no assertEquals method that compares two double arrays. You'll have to manually compare their difference against your tolerance
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
