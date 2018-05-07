import MultipleChoiceChoice.*

open class MultipleChoiceOverlay : Overlay<MultipleChoiceQuestion, MultipleChoiceMark> {
    var questionLine = Text("")
    var choice1 = Text("")
    var choice2 = Text("")
    var choice3 = Text("")
    var choice4 = Text("")

    override fun runQuestion(s: Screen, q: MultipleChoiceQuestion): MultipleChoiceMark {
        s.overlay = this
        showQuestion(q)
        s.print()
        val a = s.awaitAnswer().toString()
        showAnswer(a)
        val mark = q.markAnswer(a)
        showMarkedAnswer(mark)
        return mark
    }

    override fun showQuestion(q: MultipleChoiceQuestion) {
        questionLine = Text("Which of these ${q.question}?")
        when (q.answerChoice) {
            A -> {
                choice1 = Text(q.answer)
                choice2 = Text(q.incorrect.first)
                choice3 = Text(q.incorrect.second)
                choice4 = Text(q.incorrect.third)
            }
            B -> {
                choice1 = Text(q.incorrect.first)
                choice2 = Text(q.answer)
                choice3 = Text(q.incorrect.second)
                choice4 = Text(q.incorrect.third)
            }
            C -> {
                choice1 = Text(q.incorrect.first)
                choice2 = Text(q.incorrect.second)
                choice3 = Text(q.answer)
                choice4 = Text(q.incorrect.third)
            }
            D -> {
                choice1 = Text(q.incorrect.first)
                choice2 = Text(q.incorrect.second)
                choice3 = Text(q.incorrect.third)
                choice4 = Text(q.answer)
            }
        }
    }

    override fun printWith(printer: ColourPrinter) {
        questionLine.printlnWith(printer)
        printer.printWhite("  ")
        printer.print(choice1.baseColour, choice1.toString())
        printer.printWhite("    ")
        printer.println(choice2.baseColour, choice2.toString())
        printer.printWhite("  ")
        printer.print(choice3.baseColour, choice3.toString())
        printer.printWhite("    ")
        printer.println(choice4.baseColour, choice4.toString())

    }

    override fun showAnswer(a: String) {}

    override fun showMarkedAnswer(m: MultipleChoiceMark) {
        if (m.correct) {
            choice1.setAllGreen()
        } else {
            when (m.choice) {
                B -> choice2.setAllRed()
                C -> choice3.setAllRed()
                D -> choice4.setAllRed()
                A -> TODO("This shouldn't happen")
            }
            choice1.setAllBlue()
        }
    }

    override fun toString(): String {
        return "$questionLine\n  $choice1    $choice2\n  $choice3    $choice4"
    }

    override fun clear() {
        questionLine = Text("")
        choice1 = Text("")
        choice2 = Text("")
        choice3 = Text("")
        choice4 = Text("")
    }

    override fun maxLineLength(): Int {
        return listOf(questionLine.toString().length,
                6 + choice1.toString().length + choice2.toString().length,
                6 + choice3.toString().length + choice4.toString().length).max() ?: 0
    }
}
