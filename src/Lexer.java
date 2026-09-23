import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {

    private final TextManager textManager;
    private final HashMap<String, Token.TokenTypes> keyWords;
    int lineNumber = 1;
    int characterPosition = 1;
    List<Token> listOfTokens;
    int indentLevel = 0;

    public Lexer(String word)  {
        this.textManager = new TextManager(word);
        keyWords = new HashMap<>();

        keyWords.put("identifier", Token.TokenTypes.IDENTIFIER);
        keyWords.put("number", Token.TokenTypes.NUMBER);
        keyWords.put("newLine", Token.TokenTypes.NEWLINE);
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

    private void increaseLine(){
        lineNumber++;
    }

    private void increaseCharacterPosition(int n){
        characterPosition += n;
    }
    private void resetPosition(){
        characterPosition = 0;
    }
    private void resetLine(){lineNumber = 0;}
    private void indent(){
        indentLevel += 4;
        listOfTokens.add(new Token (Token.TokenTypes.INDENT, lineNumber, characterPosition));
    }
    private void dedent(){
        indentLevel -= 4;
        listOfTokens.add(new Token (Token.TokenTypes.DEDENT, lineNumber, characterPosition));
    }


    public List<Token> Lex() throws SyntaxErrorException {
        listOfTokens = new ArrayList<Token>();
        while (!textManager.isEnd()) {
            char c = textManager.peekCharacter();

            if (Character.isLetter(c)) {
                String word = textManager.readWord();
                if (keyWords.containsKey(word)){
                    listOfTokens.add( new Token(keyWords.get(word), lineNumber, characterPosition, word));
                    increaseCharacterPosition(word.length());
                }
                else{
                    listOfTokens.add(new Token (Token.TokenTypes.IDENTIFIER, lineNumber, characterPosition, word));
                    increaseCharacterPosition(word.length());
                }
            }
            else if (Character.isDigit(c)) {
                String number = textManager.readNumber();
                listOfTokens.add(new Token(Token.TokenTypes.NUMBER, lineNumber, characterPosition, number));
                increaseCharacterPosition(number.length());
            }
            else if (c == '\t'){
                listOfTokens.add(new Token(Token.TokenTypes.INDENT, lineNumber, characterPosition));
                increaseCharacterPosition(4);
                textManager.getCharacter();

            }

            else if (c == '\n'){
                listOfTokens.add(new Token(Token.TokenTypes.NEWLINE, lineNumber, characterPosition));
                textManager.getCharacter();
                resetPosition();
                lineNumber++;
            }

            else if (c == ' '){
                int spaceCount = 0;
                while (!textManager.isEnd() && textManager.peekCharacter() == ' '){
                    textManager.getCharacter();
                    spaceCount+=1;
                    characterPosition++;
                }
                if (spaceCount % 4 != 0 && spaceCount > 1){
                    throw new SyntaxErrorException("Invalid Format", lineNumber, characterPosition);
                }

                else if (spaceCount > 1 && spaceCount > indentLevel){
                    indent();
                }
                else if (spaceCount == 0 && spaceCount < indentLevel){
                    dedent();
                }
            }

            else if (c == '/'){
                textManager.getCharacter();
                characterPosition++;
                if (!textManager.isEnd() && textManager.peekCharacter() == '*'){
                    textManager.getCharacter();
                    characterPosition++;
                    while(!textManager.isEnd()){
                        c = textManager.getCharacter();
                        characterPosition++;

                        if (c == '*' && !textManager.isEnd() && textManager.peekCharacter() == '/'){
                            textManager.getCharacter();
                            characterPosition++;
                            break;
                        }
                    }
                }
            }

            else if (c == '"'){
                String str = "";
                while(!textManager.isEnd() && textManager.peekCharacter() != '"'){
                    str = str + textManager.getCharacter();
                    characterPosition++;
                }
                if (textManager.isEnd()){
                    throw new SyntaxErrorException("Unterminated String", lineNumber, characterPosition);
                }
                textManager.getCharacter();
                characterPosition++;
                listOfTokens.add(new Token(Token.TokenTypes.STRING, lineNumber, characterPosition, str));
            }
            else if (c == '('){
                listOfTokens.add(new Token (Token.TokenTypes.OPENPAREN, lineNumber, characterPosition));
                textManager.getCharacter();
                characterPosition++;
            }
            else if (c == ')'){
                listOfTokens.add(new Token (Token.TokenTypes.CLOSEPAREN, lineNumber, characterPosition));
                textManager.getCharacter();
                characterPosition++;
            }
            else if (c == ','){
                listOfTokens.add(new Token (Token.TokenTypes.COMMA, lineNumber, characterPosition));
                textManager.getCharacter();
                characterPosition++;
            }
            else {
                textManager.getCharacter();
                characterPosition++;
            }
        }

        /*for (int i = 0; i < listOfTokens.size(); i++){
            System.out.println(i + ": " + listOfTokens.get(i).Type + " " + listOfTokens.get(i).Value);
        }*/
        return listOfTokens;
    }



}