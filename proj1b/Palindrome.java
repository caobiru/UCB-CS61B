public class Palindrome {
    /** Return a Deque where the characters appear in the same order as in the String. */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> D = new ArrayDeque<>();

        for (int i = 0; i < word.length(); i++) {
            D.addLast(word.charAt(i));
        }
        return D;
    }

    /** Defines a word whether it is the same whether read forwards or backwards */
    public boolean isPalindrome(String word) {
        Deque<Character> D = wordToDeque(word);
        return isPalindromeHelper(D);
    }

    private boolean isPalindromeHelper(Deque<Character> D) {
        if (D.size() == 0 || D.size() == 1) {
            return true;
        }
        else if (D.removeFirst() != D.removeLast()) {
            return false;
        }

        return isPalindromeHelper(D);
    }

    /** return true if the word is a palindrome according to the character comparison test provided by
     * the CharacterComparator passed in as argument cc*/
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> D = wordToDeque(word);

        for (int i = 0; i < word.length() / 2; i++) {
            if (D.size() == 0 || D.size() == 1) {
                return true;
            }
            else if (!cc.equalChars(D.removeFirst(), D.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
