public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque<>();
        char a_char;
        for (int i = 0; i < word.length(); i++) {
            a_char = word.charAt(i);
            result.addLast(a_char);
        }
        return result;
    }
    public boolean isPalindrome(String word) {
        boolean result = true;
        Deque<Character> queueWord = wordToDeque(word);
        while (queueWord.size() > 1) {
            if (!queueWord.removeFirst().equals(queueWord.removeLast())) {
                result = false;
            }
        }
        return result;
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        boolean result = true;
        Deque<Character> queueWord = wordToDeque(word);
        while (queueWord.size() > 1) {
            if (!cc.equalChars(queueWord.removeFirst(), queueWord.removeLast())) {
                result = false;
            }
        }
        return result;
    }
}
