package ttl.introkot.course.playground

import ttl.larku.domain.Student
import ttl.larku.service.StudentService
import java.util.function.Predicate
import java.util.function.Function

/**
 * @author whynot
 */

fun go() {
    val service = StudentService()
    FilterMapJava.init(service)
    val students = service.allStudents

    //val withM = filter(students, Predicate{s -> s.getName().startsWith("M")})
    val withM = filterPred(students, Predicate { s -> s.getName().startsWith("M") })

    val withMToo = filter(students) { s ->
        s.getName().startsWith("M")
    }

    val sNames = mapFunc(students, Function { s -> s.getName() })

    val sNamesToo = map(students) { s ->
        s.getName()
    }

    val combined = mapFunc(
            filterPred(students, Predicate { s -> s.getName().startsWith("M") }),
            Function { s -> s.name }
    )

    val combinedToo = map(
            filter(students) { s -> s.name.startsWith("M") }
    ) { s ->
        s.name
    }

    val combinedThree = students.filter{
        it.name.startsWith("M")
    }.map (Student::getName)

}

fun filterPred(input: List<Student>, pred: Predicate<Student>): List<Student> {
    val result = mutableListOf<Student>()
    for (s in input) {
        if (pred.test(s)) {
            result.add(s)
        }
    }
    return result
}

fun mapFunc(input: List<Student>, func: Function<Student, String>): List<String> {
    val result = mutableListOf<String>()
    for (s in input) {
        result.add(func.apply(s))
    }
    return result
}

fun filter(input: List<Student>, pred: (Student) -> Boolean): List<Student> {
    val result = mutableListOf<Student>()
    for (s in input) {
        if (pred(s)) {
            result.add(s)
        }
    }
    return result
}

fun map(input: List<Student>, func: (Student) -> String): List<String> {
    val result = mutableListOf<String>()
    for (s in input) {
        result.add(func(s))
    }
    return result
}
