import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {

    private final TextManager textManager;
    private final HashMap<String, Token.TokenTypes> keyWords;
    int lineNumber = 0;
    int characterPosition = 0;
    List<Token> listOfTokens;

    public Lexer(String word) {
        this.textManager = new TextManager(word);
        keyWords = new HashMap<>();

        keyWords.put("identifier", Token.TokenTypes.IDENTIFIER);
        keyWords.put("number", Token.TokenTypes.NUMBER);
        keyWords.put("\n", Token.TokenTypes.NEWLINE);
        keyWords.put("indent", Token.TokenTypes.INDENT);
        keyWords.put("dedent", Token.TokenTypes.DEDENT);
        keyWords.put("string", Token.TokenTypes.STRING);
        keyWords.put("from", Token.TokenTypes.FROM);
        keyWords.put("create", Token.TokenTypes.CREATE);
        keyWords.put("table", Token.TokenTypes.TABLE);
        keyWords.put("enum", Token.TokenTypes.ENUM);
        keyWords.put("decimalNumber", Token.TokenTypes.DECIMALNUMBER);
        keyWords.put("where", Token.TokenTypes.WHERE);
        keyWords.put("wholeNumber", Token.TokenTypes.WHOLENUMBER);
        keyWords.put("list", Token.TokenTypes.LIST);
        keyWords.put("delete", Token.TokenTypes.DELETE);
        keyWords.put("greaterThan", Token.TokenTypes.GREATERTHAN);
        keyWords.put("lessThan", Token.TokenTypes.LESSTHAN);
        keyWords.put("greaterThanEqual", Token.TokenTypes.GREATERTHANEQUAL);
        keyWords.put("lessThanEqual", Token.TokenTypes.LESSTHANEQUAL);
        keyWords.put("insert", Token.TokenTypes.INSERT);
        keyWords.put("return", Token.TokenTypes.RETURN);
        keyWords.put("findOne", Token.TokenTypes.FINDONE);
        keyWords.put("closeParen", Token.TokenTypes.CLOSEPAREN);
        keyWords.put("openParen", Token.TokenTypes.OPENPAREN);
        keyWords.put("equal", Token.TokenTypes.EQUAL);
        keyWords.put("notEqual", Token.TokenTypes.NOTEQUAL);
        keyWords.put("leftBrace", Token.TokenTypes.LEFTBRACE);
        keyWords.put("rightBrace", Token.TokenTypes.RIGHTBRACE);
        keyWords.put("comma", Token.TokenTypes.COMMA);
        keyWords.put("dot", Token.TokenTypes.DOT);

    }



    public List<Token> lex() {
        listOfTokens = new ArrayList<Token>();
        while (!textManager.isEnd()) {
            char c = textManager.peekCharacter();

            if (Character.isLetter(c)) {
                String word = textManager.readWord();
                if (keyWords.containsKey(word)){
                    listOfTokens.add( new Token(keyWords.get(word), lineNumber, characterPosition, word));
                }
                else{
                    listOfTokens.add(new Token (Token.TokenTypes.IDENTIFIER, lineNumber, characterPosition, word));
                }
            }
            else if (Character.isDigit(c)) {
                String number = textManager.readNumber();
                listOfTokens.add(new Token(Token.TokenTypes.NUMBER, lineNumber, characterPosition, number));
            }
            else if (c == '\n'){
                textManager.getCharacter();
                listOfTokens.add(new Token(Token.TokenTypes.NEWLINE, lineNumber, characterPosition, "\n"));
            }
            else {
                textManager.getCharacter();
            }
        }

        return listOfTokens;
    }



}