/*Created on 29/04/18. */
import junit.framework.TestCase.*
import org.junit.Test

class QuestionTest {
    @Test
    fun canRecogniseIncorrectAnswer() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        val mark = q.markAnswer("გმადლომ")

        assertFalse(mark.correct)
    }

    @Test
    fun canRecogniseCorrectAnswer() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        val mark = q.markAnswer("გმადლობ")

        assertTrue(mark.correct)
    }

    @Test
    fun canDiffAnIncorrectWord() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        val mark = q.markAnswer("გმადლომ")

        assertEquals(setOf(6), mark.errorIndices)
    }

    @Test
    fun canDiffAnCorrectWord() {
        val q = Question("Translate 'thanks'", "გმადლობ")

        val mark = q.markAnswer("გმადლობ")

        assertEquals(0, mark.errorIndices.count())
    }
}