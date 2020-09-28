package ttl.introkot.course._1Intro

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.UnsupportedOperationException
import java.time.LocalTime


class TestIfAndWhen {
    enum class Grade {
        A, B, C
    }

    fun processScoresIf(score: Double): Pair<String, Grade> {
        val description: String
        val grade: Grade
        if (score >= 90.0) {
            description = "Excellent"
            grade = Grade.A
        } else if (score in 70.0..89.9) {
            description = "Good"
            grade = Grade.B
        } else {
            description = "Acceptable"
            grade = Grade.C
        }
        //Use description and grade
        //Notice that the use of the else allows you
        //to use the variables here.  If you remove it,
        //you will get a compile error.
        return Pair(description, grade)
    }

    @Test
    fun testProcessScoresIf() {
        var p = processScoresIf(91.0);
        assertEquals(Grade.A, p.second);

        p = processScoresIf(89.9)
        assertEquals(Grade.B, p.second);

        p = processScoresIf(85.0)
        assertEquals(Grade.B, p.second);

        p = processScoresIf(60.0)
        assertEquals(Grade.C, p.second);
    }

    fun processScores(score: Double): Pair<String, Grade> {
        val description: String
        val grade: Grade
        when {
            score >= 90.0 -> {
                description = "Excellent"
                grade = Grade.A
            }
            score in 70.0..89.9 -> {
                description = "Good"
                grade = Grade.B
            }
//        score < 70.0 -> {
            else -> {
                description = "Acceptable"
                grade = Grade.C
            }
        }
        //Use description and grade
        //If you don't have an 'else' part to the
        //'when' above, you will get an uninitialized
        //error on the variables if you try to use them.
        //To make this compile, change the
        //last "case" into an else.
        return Pair(description, grade)
    }

    @Test
    fun testProcessScores() {
        var p = processScores(91.0);
        assertEquals(Grade.A, p.second);

        p = processScores(89.9)
        assertEquals(Grade.B, p.second);

        p = processScores(85.0)
        assertEquals(Grade.B, p.second);

        p = processScores(60.0)
        assertEquals(Grade.C, p.second);
    }

    //Destructuring.  The Pair that is returned is automatically
//destructed into 'description' and 'grade'
    fun updateScore(score: Double): Pair<String, Grade> {
        val (description, grade) =
                when {
                    score >= 90.0 -> Pair("Excellent", Grade.A)
                    score in 70.0..89.9 -> "Good" to Grade.B
                    else -> "Acceptable" to Grade.C
                }
        return description to grade;
    }

    @Test
    fun testUpdateScores() {
        var p = updateScore(91.0);
        assertEquals(Grade.A, p.second);

        p = updateScore(89.9)
        assertEquals(Grade.B, p.second);

        p = updateScore(85.0)
        assertEquals(Grade.B, p.second);

        p = updateScore(60.0)
        assertEquals(Grade.C, p.second);
    }

//When with a variable

    //Using an Enum in a if/vs when.
//With an if, you have to provide an 'else'
//clause, or the code below won't compile.
    fun aVarWithIf(grade: Grade): String {
        val result = if (grade == Grade.A) {
            "Excellent"
        } else if (grade == Grade.B) {
            "Good"
        } else if (grade == Grade.C) {
            "Acceptable"
        } else {
            throw UnsupportedOperationException("Unknown Grade: $grade")
        }
        return result
    }

    @Test
    fun aVarWithIf() {
        var p = aVarWithIf(Grade.A)
        assertEquals("Excellent", p);

        p = aVarWithIf(Grade.B)
        assertEquals("Good", p);

        p = aVarWithIf(Grade.C)
        assertEquals("Acceptable", p);
    }

    //No 'else' required if you use a 'when'
    fun aVarWithWhen(grade: Grade): String {
        val result = when (grade) {
            Grade.A -> "Excellent"
            Grade.B -> "Good"
            Grade.C -> "Acceptable"
        }
        return result
    }

    //More cleanup.  Use the return of the
//when expression directly as the value
//of the function
    fun aVarWithWhenCleaned(grade: Grade) = when (grade) {
        Grade.A, Grade.B -> "Excellent"
        Grade.B -> "Good"
        Grade.C -> "Acceptable"
    }

    @Test
    fun aVarWithIfCleaned() {
        var p = aVarWithIf(Grade.A)
        assertEquals("Excellent", p);

        p = aVarWithIf(Grade.B)
        assertEquals("Good", p);

        p = aVarWithIf(Grade.C)
        assertEquals("Acceptable", p);
    }
}


