public class OffByN implements CharacterComparator {

    public int number;

    public OffByN(int N){
        number = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == number;
    }
}
