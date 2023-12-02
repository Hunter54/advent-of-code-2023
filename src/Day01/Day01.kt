package Day01

import println
import readInput

enum class Numbers(val int: Int) {
    one(1),
    two(2),
    three(3),
    four(4),
    five(5),
    six(6),
    seven(7),
    eight(8),
    nine(9)
}

val digits = List(10) { "$it" }
val words = Numbers.entries.map { it.name }

fun main() {

    fun part1(input: List<String>): Int {
        val finalValue = input.fold(0) { sum, line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            val lineValue = listOf(firstDigit, lastDigit).joinToString("")
            sum + lineValue.toInt()
        }
        return finalValue
    }

    fun returnDigitFromMatch(match: String): Int {
        return if (match.length > 1) {
            Numbers.entries.first { it.name == match }.int
        } else {
            match.toInt()
        }
    }

    fun withRegex(input: List<String>): Int {
        val regex = Regex("""(?=(one|two|three|four|five|six|seven|eight|nine|\d))""")
        val finalValue = input.fold(0) { sum, line ->
            val matches = regex.findAll(line)
            val firstValueString = matches.first().groupValues[1].takeIf { it.isNotEmpty() } ?: run{
                matches.first().groupValues[2]
            }
            val firstValue = returnDigitFromMatch(firstValueString)
            val lastValueString = matches.last().groupValues[1].takeIf { it.isNotEmpty() } ?: run{
            matches.last().groupValues[2]
        }
            val lastValue = returnDigitFromMatch(lastValueString)
            sum + (firstValue * 10 + lastValue)
        }
        return finalValue
    }

    fun findDigitFromString(word: String): Int {
        val indexInList = words.indexOf(word)
        if (indexInList == -1) {
            error("Word digit not found in list")
        } else return indexInList + 1
    }

    fun findFirstDigit(line: String): Int{
        val (digitIndex, digit) = line.findAnyOf(digits) ?: (Int.MAX_VALUE to "not found")
        val (wordIndex, word) = line.findAnyOf(words) ?: (Int.MAX_VALUE to "not found")

        return if (digitIndex < wordIndex)
            digit.toInt()
        else findDigitFromString(word)
    }

    fun findLastDigit(line: String): Int{
        val (digitIndex, digit) = line.findLastAnyOf(digits) ?: (-1 to "not found")
        val (wordIndex, word) = line.findLastAnyOf(words) ?: (-1 to "not found")

        return if (digitIndex > wordIndex)
            digit.toInt()
        else findDigitFromString(word)
    }

    fun withoutRegex(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = findFirstDigit(line)
            val lastDigit = findLastDigit(line)
            firstDigit*10+ lastDigit
        }

    }

    fun part2(input: List<String>): Int {
        return withRegex(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01/Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01/Day01")
    part1(input).println()
    part2(input).println()
}
