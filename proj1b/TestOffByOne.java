import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void testEqualChars(){
        char a = 'a';
        char b = 'b';
        assertTrue(new OffByOne().equalChars(a,b));
        assertTrue(new OffByOne().equalChars(b,a));
        assertFalse(new OffByOne().equalChars(a,'e'));
        assertFalse(new OffByOne().equalChars(a,a));
    }

}
