package ttl.introkot.course._1Intro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author whynot
 */
public class _8ExceptionsFromJava {

//    public static void main(String[] args) {
//        //This one does *not* need try/catch/throws
//        KotExceptions.doAFile("Boo");
//
//
//        //This one *does* need try/catch/throws
//        //because of the @Throws annotation in Kotlin.
////        KotExceptions.doAFileWithThrows("Boo");
//
//        //This one, of course, also needs try/catch/throws
////       doAfileFromJava("Boo");
//    }

    public static void doAfileFromJava(String fileName) throws IOException {
        Files.lines(Paths.get(fileName)).limit(1).findAny().orElse("Nothing here");
    }
}
