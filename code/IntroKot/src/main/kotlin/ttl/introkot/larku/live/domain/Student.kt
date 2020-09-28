package ttl.introkot.larku.live.domain

import java.time.LocalDate

/**
 * @author whynot
 */
data class Student(val id: Int, var name: String, val dob: LocalDate,
              val phoneNumbers: MutableList<String> = mutableListOf())

