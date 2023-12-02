fun main() {
    fun part1(input: List<String>): Int {
        val calibration = input.map {
            it.replace(Regex("[a-zA-Z]"), "")
        }.map {
            val num1 = it.first()
            val num2 = it.last()
            "$num1$num2"
        }.map {
            it.toInt()
        }
        return calibration.sum()
    }

    fun part2(input: List<String>): Int {
        /*
        First attempt returns 54607:
        .map { line ->
            var updatedLine = line
            var i = 0
            while (i < updatedLine.length) {
                val sub = updatedLine.substring(i)

                (1..9).forEach { num ->
                    val wordForNum = num.toWord()
                    if (wordForNum.isNotEmpty() && sub.startsWith(wordForNum)) {
                        updatedLine = updatedLine.replaceFirst(wordForNum, "$num", ignoreCase = true)
                    }
                }
                i += 1
            }
            updatedLine
        }
         */
        // Correct implementation: returned 54581
        val calibration = input
                .map { line ->
                    val newStringBuilder = StringBuilder()
                    line.forEachIndexed { index, char ->
                        if (char.isDigit()) {
                            newStringBuilder.append(char)
                        } else {
                            val sub = line.substring(index)
                            (1..9).forEach { num ->
                                val wordForNum = num.toWord()
                                if (wordForNum.isNotEmpty() && sub.startsWith(wordForNum)) {
                                    newStringBuilder.append(num)
                                }
                            }
                        }
                    }
                    return@map newStringBuilder.toString()
                }.map {
                    val num1 = it.first()
                    val num2 = it.last()
                    "$num1$num2"
                }.map {
                    it.toInt()
                }
        return calibration.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    part1(testInput).println()
    check(part1(testInput) == 142)

    val test2Input = readInput("Day02_test")
    part2(test2Input).println()
    check(part2(test2Input) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
