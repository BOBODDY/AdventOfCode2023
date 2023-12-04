import Game.CubeColor
import Game.CubeColor.*

data class Game(val id: Int, val pulls: List<Pull>) {

    data class Pull(val num: Int, val color: CubeColor)

    enum class CubeColor {
        RED, GREEN, BLUE, UNDEFINED
    }

    val maxRed: Int
        get() {
            return pulls.filter { it.color == RED }.maxBy { it.num }.num
        }

    val maxBlue: Int
        get() {
            return pulls.filter { it.color == BLUE }.maxBy { it.num }.num
        }

    val maxGreen: Int
        get() {
            return pulls.filter { it.color == GREEN }.maxBy { it.num }.num
        }
}

fun main() {

    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    fun stringToColor(color: String): CubeColor {
        return when (color) {
            "red" -> RED
            "blue" -> BLUE
            "green" -> GREEN
            else -> CubeColor.UNDEFINED
        }
    }

    fun convertLineToGame(line: String): Game {
        val data = line.split(":")
        val id = data.first().split(" ")[1].toInt()

        val sessions = data[1].split(";")              // 3 blue, 4 red
                .map { it.replace(", ", ",") }   // 3 blue,4 red
                .map {
                    it.split(",").map {
                        val x = it.trim().split(" ")
                        Game.Pull(x[0].toInt(), stringToColor(x[1]))
                    }
                }.flatten()

        return Game(id, sessions)
    }

    fun part1(input: List<String>): Int {
        val validGames = input
                .map {
                    convertLineToGame(it)
                }
                .filter {
                    it.maxRed <= maxRed && it.maxBlue <= maxBlue && it.maxGreen <= maxGreen
                }
        return validGames.map { it.id }.sum()
    }

    fun part2(input: List<String>): Int {
        val validGames = input
                .map {
                    convertLineToGame(it)
                }
                .map {
                    val maxRed = it.pulls.filter { it.color == RED }.maxOf {
                        it.num
                    }
                    val maxBlue = it.pulls.filter { it.color == BLUE }.maxOf {
                        it.num
                    }
                    val maxGreen = it.pulls.filter { it.color == GREEN }.maxOf {
                        it.num
                    }
                    mapOf(
                            RED to maxRed,
                            BLUE to maxBlue,
                            GREEN to maxGreen,
                    )
                }.map {
                    it[RED]!! * it[GREEN]!! * it[BLUE]!!
                }
        return validGames.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    part1(testInput).println()
    check(part1(testInput) == 8)

    val test2Input = readInput("Day02_test")
    part2(test2Input).println()
    check(part2(test2Input) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
