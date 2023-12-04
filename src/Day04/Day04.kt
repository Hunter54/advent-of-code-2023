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

    fun findNumberOfPointsPerCard(card: Card): Int{

        val matchingNumbers = card.winningNumbers.count {
            it in card.numbers
        }
        return 2.0.pow(matchingNumbers - 1).toInt()
    }

    fun part1(input: List<String>): Int {
        val parsedData = parseInputData(input)
        val numberOfPoints = parsedData.sumOf { card ->
            findNumberOfPointsPerCard(card)
        }
        return numberOfPoints
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day04/Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04/Day04")
    println(part1(input))
    println(part2(input))
}