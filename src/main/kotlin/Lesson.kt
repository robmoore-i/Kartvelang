/*Created on 30/04/18. */
import java.util.*

class Lesson(val s: Screen, val qs: Questions) {
    fun start(): LessonResults {
        var mistakes = 0
        var answered = 0
        val startTime = Calendar.getInstance().time.time
        while (!qs.empty()) {
            val q = qs.pop()
            s.showQuestion(q)
            s.print()
            val a = s.awaitAnswer().toString()
            s.showAnswer(a)
            val mark = q.markAnswer(a)
            s.showMarkedAnswer(mark)
            s.print()
            if (!mark.correct) {
                qs.insertDelayed(q)
                s.awaitCorrection(q.answerText)
                mistakes++
            }
            s.awaitKeyPress(Key.ENTER)
            s.clear()
            answered++
        }
        val accuracy = 100 * (answered - mistakes).toDouble() / answered
        val endTime = Calendar.getInstance().time.time
        val lessonTimeSeconds = (endTime - startTime).toDouble() / 1000
        s.showLessonDuration(lessonTimeSeconds)
        s.print()
        s.clear()
        s.close()
        return LessonResults(accuracy, lessonTimeSeconds)
    }

    data class LessonResults(val accuracyPc: Double, val timeSeconds: Double)
}
