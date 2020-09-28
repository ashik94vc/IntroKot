package ttl.introkot.course._5CollectionOps;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author whynot
 */
public class JavaStream {

    public static void main(String[] args) {
        List<Person> persons = DBKt.getPersons();

        List<String> names = persons.stream()
                .filter(p -> p.getAddress().getZipCode().equals("19191"))
                .map(Person::getName)
                .collect(toList());
       names.forEach(System.out::println);
    }
}
