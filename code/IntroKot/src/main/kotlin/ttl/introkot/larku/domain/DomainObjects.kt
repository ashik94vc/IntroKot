package ttl.introkot.larku.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.time.LocalDate

/**
 * @author whynot
 */

val emailRegEx = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$".toRegex()

data class Email(val email: String) : Serializable{
    init {
       if(!emailRegEx.matches(email)) {
           throw RuntimeException("Bad email: " + email)
       }
    }
}


data class Student (val id: Long,
                    var name: String,
                    var phoneNumber: String,
//                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
                    val dob: LocalDate,
                    var email: Email? = null) : Serializable

data class Course (val id: Long,
                   var name: String,
                   var courseCode: String,
                   var dateAdded: LocalDate? = null)
