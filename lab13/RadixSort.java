/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        String[] list = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            list[i] = asciis[i];
        }
        int max_len = 0;
        for (String s : list) {
            if (s.length() > max_len) {
                max_len = s.length();
            }
        }
        for (int i = max_len - 1; i >= 0; i--) {
            sortHelperLSD(list, i);
        }
        return list;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        if (index < 0) return;
        int[] num_Chars = new int[257]; // index[0] is for empty string which is returned when there's no char at the position
        int[] pos_Chars = new int[257];
        for (String s : asciis) {
            if (s.length() <= index) {
                num_Chars[0]++;
            } else {
                //System.out.println(index + s + (int) s.charAt(index));
                num_Chars[(int) s.charAt(index) + 1]++;
            }
        }
        for (int i = 1; i < 257; i++)
        {
            pos_Chars[i] = num_Chars[i-1] + pos_Chars[i-1];
        }
        String[] aux = new String[asciis.length];
        for (String s : asciis) {
            if (s.length() <= index) {
                aux[pos_Chars[0]] = s;
                pos_Chars[0]++;
            } else {
                aux[pos_Chars[(int) s.charAt(index) + 1]] = s;
                pos_Chars[(int) s.charAt(index) + 1]++;
            }
        }
        for (int i = 0; i < aux.length; i++) {
            asciis[i] = aux[i];
        }
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] asciis = new String[]{"Stupid", "Clever", "Xiaoyu", "Ford", "Cleverland", "Chuan", "Stanford"};
        String[] result = sort(asciis);
        System.out.println(asciis);
        System.out.println(result);
    }
}
