package ttl.introkot;
import org.junit.jupiter.api.Test;

/**
 * @author whynot
 */
public class JavaLoops {

    @Test
    public void testChars() {
        int i = 0xFFFF;
        char ch = 'a';
        System.out.println("first i=" + i + "a=" + (int)ch);
        //char x = ch + i;  This doesn't work in java because return of + is int

        //In Java, the ch is converted to an int and that's
        //what you get printed out
        for(char c : "abc".toCharArray()) {
            System.out.print(c + i);
            System.out.print(", ");
            System.out.println(c + i);
        }
    }
}
