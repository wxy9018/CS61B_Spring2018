/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a.equals(b); // this is the bug. original version use a==b. Integer warps a primitive type "int" into an object "integer", thus "==" compares address.
    }
}
