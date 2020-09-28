package ttl.introkot.solutions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author whynot
 */
public class _2ClassesJava {

    //TODO - Complete testTask6 to call the function
    // you wrote in Task 5.
    // Set up the Kotlin code to make the call from Java
    // as convenient as possible.
    // The assertion in testTask6 should pass.
    @Test
    public void testTask6() {
        String greeting = ClassesTasks.greeting();
        assertEquals("Hola", greeting);
    }
}
