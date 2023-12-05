import java.util.*
import kotlin.time.measureTimedValue

data class Card(val id: Int, val winningNumbers: List<Int>, val cardNumbers: List<Int>) {

    fun numWinningNumbers(): Int {
        return cardNumbers.filter { winningNumbers.contains(it) }.size
    }

    fun isWinningCard(): Boolean {
        return numWinningNumbers() > 0
    }
}

fun main() {
    fun inputToCards(input: List<String>): List<Card> {
        return input.map {
            val data = it.split(':')
            val cardData = data.first()

            val cardId = cardData.split(' ').last().trim().toInt()

            val cardNumberData = data.last()
            val numberMapping = cardNumberData.split('|')
            val winningNumbers = numberMapping.first().trim().replace("  ", " ").split(' ').map { it.toInt() }
            val cardNumbers = numberMapping.last().trim().replace("  ", " ").split(' ').map { it.toInt() }

            Card(cardId, winningNumbers, cardNumbers)
        }
    }

    fun part1(input: List<String>): Int {
        val scores = inputToCards(input).map { card ->
            card.numWinningNumbers()
        }.filter {
            it > 0
        }.map {
            1.shl(it - 1)
        }.sumOf {
            it
        }
        return scores
    }

    fun part2(input: List<String>): Int {
        val cards = inputToCards(input)
        val cardStack = Stack<Card>()
        val wonCards = mutableListOf<Card>()

        cards.reversed().forEach { cardStack.push(it) }

        while (cardStack.isNotEmpty()) {
            val card = cardStack.pop()
            wonCards.add(card)

            if (card.isWinningCard()) {
                val winningNumbers = card.winningNumbers
                val cardNumbers = card.cardNumbers
                val numberOfWinning = cardNumbers.filter { winningNumbers.contains(it) }.size
                val extraCards = cards.slice(card.id..<card.id+numberOfWinning)
                extraCards.forEach { cardStack.push(it) }
            }
        }

        return wonCards.size
    }

    // test if implementation meets criteria from the description
    println("Test Cases")
    val testInput = readInput("Day04_test")
    print("Part 1: ")
    part1(testInput).println()
    check(part1(testInput) == 13)
    print("Part 2: ")
    part2(testInput).println()
    check(part2(testInput) == 30)

    println("Real input")
    print("Part 1: ")
    val input = readInput("Day04")
    val (part1Value, timeTaken1) = measureTimedValue {
        part1(input)
    }
    part1Value.println()
    println("Part 1 took ${timeTaken1.inWholeMilliseconds}ms")

    print("Part 2: ")
    val (part2Value, timeTaken) = measureTimedValue {
        part2(input)
    }
    part2Value.println()
    println("Part 2 took ${timeTaken.inWholeMilliseconds}ms")
}