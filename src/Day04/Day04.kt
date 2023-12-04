package Day04

import readInput
import kotlin.math.pow
import kotlin.system.measureTimeMillis

data class Card(
    val id: Int,
    val winningNumbers: List<Int>,
    val numbers: List<Int>,
)
val dayNumber = "04"
class Day04 {
    private fun parseInputData(input: List<String>): List<Card> {
        val cardsList = input.map { line ->
            val splitLinesOnSemicolon = line.split(":")
            val id = splitLinesOnSemicolon.first()
                .split(" ")
                .last()
                .toInt()
            val allNumbers = splitLinesOnSemicolon.last()
                .split("|")

            val winningNumbers = allNumbers.first()
                .trim()
                .split(" ")
                .filterNot { it.isBlank() }
                .map { it.toInt() }
            val numbers = allNumbers
                .last()
                .trim()
                .split(" ")
                .filterNot { it.isBlank() }
                .map { it.toInt() }
            Card(id, winningNumbers, numbers)
        }

        return cardsList
    }

    private fun findMatchingNumbers(card: Card): Int {
        val matchingNumbers = card.winningNumbers.count {
            it in card.numbers
        }
        return matchingNumbers
    }

    private fun findNumberOfCards(cards: List<Card>): Int {
        val numberOfEachCards = cards.associate { it.id to 1 }.toMutableMap()

        cards.forEach { card ->
            val matchingNumbers = findMatchingNumbers(card)
            (1..matchingNumbers).forEach {
                val numberOfCardsAtId = numberOfEachCards[card.id] ?: error("Invalid ID")

                numberOfEachCards[card.id + it] =
                    numberOfEachCards[card.id + it]?.plus(numberOfCardsAtId) ?: error("Invalid ID")
            }
        }

        return numberOfEachCards.values.sumOf { it }
    }

    fun part1(input: List<String>): Int {
        val parsedData = parseInputData(input)
        val numberOfPoints = parsedData.sumOf { card ->
            2.0.pow(findMatchingNumbers(card) - 1).toInt()
        }
        return numberOfPoints
    }

    fun part2(input: List<String>): Int {
        val cards = parseInputData(input)
        return findNumberOfCards(cards)
    }
}

fun main() {

    val day = Day04()

    fun part1(input: List<String>): Int {
        return day.part1(input)
    }

    fun part2(input: List<String>): Int {
        return day.part2(input)
    }

    val testInput = readInput("Day${dayNumber}/Day${dayNumber}_test")
    println("***** Day $dayNumber *****")
    var testOutput1: Int
    var testOutput2: Int
    val testOutput1Millis = measureTimeMillis {
        testOutput1 = part1(testInput)
    }
    check(testOutput1 == 13)
    println("✅ part one with $testOutput1 ✅ in $testOutput1Millis ms")

    val testOutput2Millis = measureTimeMillis {
        testOutput2 = part2(testInput)
    }
    check(testOutput2 == 30)
    println("✅ part two with $testOutput2 ✅ in $testOutput2Millis ms")

    println("*****************")

    val input = readInput("Day04/Day04")
    var output1: Int
    var output2: Int
    val output1Millis = measureTimeMillis {
        output1 = part1(input)
    }
    val output2Millis = measureTimeMillis {
        output2 = part2(input)
    }
    println("💡 part one with $output1 💡in $output1Millis ms")
    println("💡 part two with $output2 💡in $output2Millis ms")
}