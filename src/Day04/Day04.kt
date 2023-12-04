package Day04

import readInput
import kotlin.math.pow

data class Card(
    val id: Int,
    val winningNumbers: List<Int>,
    val numbers: List<Int>,
)

fun main() {


    fun parseInputData(input: List<String>): List<Card> {

        val cardsList = input.map { line ->
            val splitLinesOnSemicolon = line.split(":")
            val id = splitLinesOnSemicolon.first().split(" ").last().toInt()
            val allNumbers = splitLinesOnSemicolon.last().split("|")

            val winningNumbers = allNumbers.first().trim().split(" ").mapNotNull { it.toIntOrNull() }
            val numbers = allNumbers.last().trim().split(" ").mapNotNull { it.toIntOrNull() }
            Card(id, winningNumbers, numbers)
        }

        return cardsList
    }

    fun findMatchingNumbers(card: Card): Int {

        val matchingNumbers = card.winningNumbers.count {
            it in card.numbers
        }
        return matchingNumbers
    }

    fun part1(input: List<String>): Int {
        val parsedData = parseInputData(input)
        val numberOfPoints = parsedData.sumOf { card ->
            2.0.pow(findMatchingNumbers(card) - 1).toInt()
        }
        return numberOfPoints
    }

    fun findNumberOfCards(cards: List<Card>): Int {
        val numberOfEachCards = cards.associate { it.id to 1 }.toMutableMap()

        cards.forEach { card ->
            val matchingNumbers = findMatchingNumbers(card)
            (1..matchingNumbers).forEach {
                val numberOfCardsAtId = numberOfEachCards[card.id] ?: error("Invalid ID")

                numberOfEachCards[card.id + it] =
                    numberOfEachCards[card.id + it]?.plus(numberOfCardsAtId) ?: error("Invalid ID")
            }
        }

        return numberOfEachCards.values.sumOf {
            it
        }
    }

    fun part2(input: List<String>): Int {

        val cards = parseInputData(input)
        return findNumberOfCards(cards)
    }

    val testInput = readInput("Day04/Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04/Day04")
    println(part1(input))
    println(part2(input))
}