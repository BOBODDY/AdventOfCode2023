fun main() {

    fun Char.isSymbol(): Boolean {
        return this > 32.toChar() && this < 46.toChar()
    }

    fun isSurroundedBySymbol(grid: List<List<Char>>, row: Int, col: Int): Boolean {

        val isValid = (row > 0 && col > 0 && grid[row - 1][col - 1].isSymbol()) ||
                (row > 0 && grid[row - 1][col].isSymbol()) ||
                (row > 0 && col < grid[row - 1].size && grid[row - 1][col + 1].isSymbol()) ||
                (col > 0 && grid[row][col - 1].isSymbol()) ||
                (col < grid[row].size && grid[row][col + 1].isSymbol()) ||
                ((row + 1) < grid.size && col > 0 && grid[row + 1][col - 1].isSymbol()) ||
                ((row + 1) < grid.size && grid[row + 1][col].isSymbol()) ||
                ((row + 1) < grid.size && col < grid[row + 1].size && grid[row + 1][col + 1].isSymbol())
        return isValid
    }

    fun part1(input: List<String>): Int {
        val chars = input.map { it.toList() }

        val engineParts = mutableListOf<Int>()

        var r = 0
        var c: Int
        for (row in input) {
            c = 0

            while (c < row.length) {
                val numberBuilder = StringBuilder()
                var validNumber = false
                if (row[c].isDigit()) { // Found a number
                    if (isSurroundedBySymbol(chars, r, c)) validNumber = true // For symbols immediately preceding the number
                    numberBuilder.append(row[c])
                    c += 1
                    while (c < row.length) {
                        if (row[c].isDigit()) {
                            numberBuilder.append(row[c])
                            if (isSurroundedBySymbol(chars, r, c) && !validNumber) validNumber = true

                            c += 1
                        } else {
                            break
                        }
                    }
                } else {
                    c += 1
                }
                val newNumString = numberBuilder.toString()
                val newNum = newNumString.toIntOrNull()

                c += 1

                // Past this point, we have a number
                if (validNumber && newNum != null) {
                    engineParts.add(newNum)
                }
            }

            r += 1
        }

        return engineParts.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    part1(testInput).println()
    check(part1(testInput) == 4361)

//    val test2Input = readInput("Day02_test")
//    part2(test2Input).println()
//    check(part2(test2Input) == 2286)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
