public class TextManager {
    final String str;
    private int position = 0;

    public TextManager(String str) {
        this.str = str;
    }

    public boolean isEnd() {
        return position >= str.length();
    }

    public char getCharacter() {
        char newChar = str.charAt(position);
        position++;
        return newChar;
    }

    public char peekCharacter() {

        return str.charAt(position);
    }

    public String readWord() {
        String word = "";
        while (!isEnd() && !Character.isWhitespace(peekCharacter()) && peekCharacter() != '('
                && peekCharacter() != ')' && peekCharacter() != ',' && peekCharacter() != '{'
                && peekCharacter() != '}' && peekCharacter() != '<' && peekCharacter() != '>'
                && peekCharacter() != '=' && peekCharacter() != ',' && peekCharacter() != '.') {
            word = word + getCharacter();
        }
        return word;
    }

    public String readNumber() {
        String integer = "";
        while ( !isEnd() && !Character.isWhitespace(peekCharacter()) && peekCharacter() != ',' && peekCharacter() != ')') {
            integer = integer + getCharacter();
        }
        return integer;
    }
}