/*Created on 04/05/18. */
package logic.question

import java.util.*

open class MultipleChoiceQuestion(val question: String, override val answer: String, val incorrect: Triple<String, String, String>, val answerChoice: MultipleChoiceChoice) : Question {
    override val fullCorrections: Boolean = false

    override fun verifyAnswer(attempt: String): Boolean {
        TODO("not implemented for multiple choice questions")
    }

    fun markAnswer(attempt: String): MultipleChoiceMark {
        val choice = attempt.first().toLowerCase().toChoice()
        return MultipleChoiceMark(choice == answerChoice, choice, answerChoice)
    }
}

private fun Char.toChoice(): MultipleChoiceChoice {
    return when (this) {
        'a' -> MultipleChoiceChoice.A
        'b' -> MultipleChoiceChoice.B
        'c' -> MultipleChoiceChoice.C
        'd' -> MultipleChoiceChoice.D
        else -> {
            TODO("Cannot get choice which isn't in {a, b, c, d}")
        }
    }
}

data class MultipleChoiceMark(override val correct: Boolean, val choice: MultipleChoiceChoice, val answer: MultipleChoiceChoice) : Mark

enum class MultipleChoiceChoice {
    A, B, C, D;
}

fun randomChoice(): MultipleChoiceChoice {
    return MultipleChoiceChoice.values()[Random().nextInt(4)]
}