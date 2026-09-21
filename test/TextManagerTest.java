import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextManagerTest {
    @Test
    public void testGetCharacter() {
        TextManager textManager = new TextManager("hello");
        assertEquals('h', textManager.getCharacter());
        assertEquals('e', textManager.getCharacter());
        assertEquals('l', textManager.getCharacter());
    }
}