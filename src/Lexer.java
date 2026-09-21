import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lexer {

    private final TextManager textManager;
    private final Map<String, Token.TokenTypes> keyWords;
    int lineNumber = 0;
    int characterPosition = 0;
    List<Token> listOfTokens;

    public Lexer(String word) {
        this.textManager = new TextManager(word);
        keyWords = new HashMap<>();

        keyWords.put("IDENTIFIER", Token.TokenTypes.IDENTIFIER);
        keyWords.put("NUMBER", Token.TokenTypes.NUMBER);
        keyWords.put("NEWLINE", Token.TokenTypes.NEWLINE);
        keyWords.put("INDENT", Token.TokenTypes.INDENT);
        keyWords.put("DEDENT", Token.TokenTypes.DEDENT);
        keyWords.put("STRING", Token.TokenTypes.STRING);
        keyWords.put("FROM", Token.TokenTypes.FROM);
        keyWords.put("CREATE", Token.TokenTypes.CREATE);
        keyWords.put("TABLE", Token.TokenTypes.TABLE);
        keyWords.put("ENUM", Token.TokenTypes.ENUM);
        keyWords.put("DECIMALNUMBER", Token.TokenTypes.DECIMALNUMBER);
        keyWords.put("WHERE", Token.TokenTypes.WHERE);
        keyWords.put("WHOLENUMBER", Token.TokenTypes.WHOLENUMBER);
        keyWords.put("LIST", Token.TokenTypes.LIST);
        keyWords.put("DELETE", Token.TokenTypes.DELETE);
        keyWords.put("GREATERTHAN", Token.TokenTypes.GREATERTHAN);
        keyWords.put("LESSTHAN", Token.TokenTypes.LESSTHAN);
        keyWords.put("GREATERTHANEQUAL", Token.TokenTypes.GREATERTHANEQUAL);
        keyWords.put("LESSTHANEQUAL", Token.TokenTypes.LESSTHANEQUAL);

    }

   /* public Lexer lex() {
        listOfTokens = new ArrayList<Token>();

        while (!textManager.isEnd()) {
            char c = textManager.peekCharacter();

            if (Character.isLetter(c)) {
                listOfTokens.add(this.textManager.readWord());
            } else if (Character.isDigit(c)) {
                listOfTokens.add(this.textManager.readNumber());
            }
        }
    }*/

    public List<Token> lex() {
        listOfTokens = new ArrayList<Token>();
        while (!textManager.isEnd()) {
            char c = textManager.peekCharacter();

            if (Character.isLetter(c)) {
                listOfTokens.add(new Token(Token.TokenTypes.IDENTIFIER, 0, 0, this.textManager.readWord()));
            } else if (Character.isDigit(c)) {
                listOfTokens.add(new Token(Token.TokenTypes.IDENTIFIER, 0, 0, this.textManager.readNumber()));
            }
        }
        // lexer logic here

        return listOfTokens;
    }
}