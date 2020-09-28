package ttl.introkot.course._4FunctionalFun;

import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author whynot
 */
public class FilterMapJava {

    public void go() {
        StudentService service = new StudentService();
        init(service);
        List<Student> students = service.getAllStudents();

        Predicate<Student> ps = s -> s.getStatus() == Student.Status.HIBERNATING;

        List<Student> withM = filter(students, (s) -> s.getName().startsWith("M"));
        withM.forEach(System.out::println);
    }
    public static void init(StudentService ss) {
        ((InMemoryStudentDAO)ss.getStudentDAO()).createStore();
        ss.createStudent("Manoj", "282 939 9944", Student.Status.FULL_TIME);
        ss.createStudent("Charlene", "282 898 2145", Student.Status.FULL_TIME);
        ss.createStudent("Firoze", "228 678 8765", Student.Status.HIBERNATING);
        ss.createStudent("Joe", "3838 678 3838", Student.Status.PART_TIME);
    }

    public List<Student> filter(List<Student> input, Predicate<Student> pred) {
//        List<Student> result = new ArrayList<>();
//        for(Student s : input) {
//            if(pred.test(s)) {
//                result.add(s);
//            }
//        }
//        return result;
        return input.stream().filter(pred).collect(Collectors.toList());
    }


}
