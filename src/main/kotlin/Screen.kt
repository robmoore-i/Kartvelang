/*Created on 29/04/18. */
import LineLabel.*
import java.io.BufferedReader

class Screen(private val printer: ColourPrinter, private val keyWaiter: KeyWaiter, var input: BufferedReader) {
    var lines: MutableList<Pair<LineLabel, Text>> = mutableListOf()

    override fun toString(): String {
        return if (lines.isEmpty()) {
            ""
        } else {
            lines.map({ (_, txt) -> txt.toString() }).reduce({ acc, nxt -> acc + "\n" + nxt })
        }
    }

    private fun maxLengthLine(): Int {
        return lines.map({ (_, txt) -> txt.toString().length }).max() ?: 0
    }

    fun print() {
        val maxLengthLine = maxLengthLine()
        printer.printlnWhite("-".repeat(maxLengthLine))
        lines.forEach({(_, txt) -> txt.printlnWith(printer) })
        printer.printlnWhite("-".repeat(maxLengthLine))
    }

    fun awaitAnswer(): Text {
        val readLine = input.readLine()
        return if (readLine == null || readLine.isEmpty()) {
            awaitAnswer()
        } else {
            Text(readLine)
        }
    }

    fun awaitCorrection(q: Question) {
        printer.printlnWhite("Type out the correct answer:")
        while (input.readLine()!! != q.answerText) {}
    }

    fun awaitKeyPress(key: Key) {
        printer.printlnWhite("Press " + key.name.toLowerCase() + " to continue")
        keyWaiter.await(key.keyCode)
    }

    private fun answerText(): Text? {
        return lines.find({ (label, _) -> label == A })?.second
    }

    fun showQuestion(q: Question) {
        lines.add(Pair(Q, Text(q.questionText)))
    }

    fun showAnswer(a: String) {
        lines.add(Pair(A, Text(a)))
    }

    fun showAnswerCorrect() {
        answerText()?.setAllGreen()
    }

    fun showAnswerEntirelyIncorrect() {
        answerText()?.setAllRed()
    }

    fun showAnswerIncorrectIndices(indices: Set<Int>) {
        val answerText = answerText()
        if (answerText != null) {
            answerText.setAllGreen()
            answerText.setRed(indices)
        }
    }

    fun showCorrection(q: Question, errorIndices: Set<Int>) {
        val indicesCorrection = Text(q.answerText.mapIndexed({ i, c ->
            if (i in errorIndices) {
                c
            } else {
                ' '
            }
        }).joinToString(separator = ""))
        indicesCorrection.baseColour = Colour.B

        val fullCorrection = Text("correct answer: " + q.answerText)
        fullCorrection.baseColour = Colour.W
        fullCorrection.overlayColour = Colour.B
        fullCorrection.overlayIndices = (0..15).toMutableSet()

        lines.add(Pair(C, indicesCorrection))
        lines.add(Pair(C, fullCorrection))
    }

    fun clear() {
        lines.clear()
    }

    fun close() {
        input.close()
    }
}

enum class LineLabel {
    Q, // Question
    A, // Answer
    C  // Correction
}