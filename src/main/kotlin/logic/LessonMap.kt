/*Created on 22/09/18. */
package logic

import logic.io.Screen
import logic.lesson.Lesson
import logic.overlay.LessonMapOverlay
import logic.question.homogenise

class LessonMap(private val user: User, private val screen: Screen) {
    fun navigate(): Lesson {
        val lessons = user.availableLessons()
        val lessonMapOverlay = LessonMapOverlay(lessons)
        screen.overlay = lessonMapOverlay
        screen.print()
        return awaitValidLessonChoice(lessons)
    }

    private fun awaitValidLessonChoice(shownLessons: List<Lesson>): Lesson {
        var input = screen.awaitLine()
        while (!isValidLessonChoice(shownLessons, input)) {
            input = screen.awaitPromptedLine("Choose a lesson from the list")
        }
        return findIntendedLessonMatch(input, shownLessons)!!
    }

    private fun isValidLessonChoice(shownLessons: List<Lesson>, input: String): Boolean {
        val match = findIntendedLessonMatch(input, shownLessons)
        return (match != null)
    }

    private fun findIntendedLessonMatch(needle: String, haystack: List<Lesson>): Lesson? {
        return haystack.firstOrNull({ lesson -> homogenise(lesson.name) == homogenise(needle) })
    }
}