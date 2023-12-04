package Day03

import readInput

fun main() {
    val symbolList = "! \" # \$ % & ' ( ) * + , - / : ; < = > ? @ [ \\ ] ^ _ ` { | } ~".split(" ").map { it.first() }

    fun checkIfNeighborSymbol(input: List<String>, range: IntRange, lineIndex: Int): Boolean {
        for (rowIndex in lineIndex - 1..lineIndex + 1) {
            for (charIndex in range.first - 1..range.last + 1) {
                val char = input.getOrNull(rowIndex)?.getOrNull(charIndex) ?: continue
                if (char in symbolList) {
                    return true
                }
            }
        }
        return false
    }

    fun calculateParts(input: List<String>): Int {
        val numbersRegex = Regex("""\d+""")

        return input.foldIndexed(0) { rowIndex, sum, row ->
            val numbers = numbersRegex.findAll(row)
            val lineSum = numbers.sumOf {
                if (checkIfNeighborSymbol(input, it.range, rowIndex))
                    it.value.toInt()
                else 0
            }
            sum + lineSum
        }
    }

    fun part1(input: List<String>): Int {
        return calculateParts(input)
    }

    fun findGearNumbers(input: List<String>, collumnIndex: Int, lineIndex: Int): Int {
        val numbersRegex = Regex("""\d+""")
        val adjacentNumbers = mutableSetOf<MatchResult>()
        for (rowIndex in lineIndex - 1..lineIndex + 1) {
            val line = input.getOrNull(rowIndex) ?: continue
            val numbers = numbersRegex.findAll(line).toList()

            for (charIndex in collumnIndex - 1..collumnIndex + 1) {
                numbers.forEach {
                    if (charIndex in it.range) {
                        adjacentNumbers.add(it)
                    }
                }
            }
        }

        return if(adjacentNumbers.size == 2) {
            adjacentNumbers.fold(1) { pow, number ->
                pow * number.value.toInt()
            }
        }
        else 0
    }

    fun findGearRatios(input: List<String>): Int {
        val gearsRegex = Regex("""\*""")

        return input.foldIndexed(0) { rowIndex, sum, row ->
            val gears = gearsRegex.findAll(row)
            val gearRatios = gears.sumOf {
                findGearNumbers(input, it.range.first, rowIndex)
            }
            sum + gearRatios
        }
    }

    fun part2(input: List<String>): Int {
        return findGearRatios(input)
    }

    val testInput = readInput("Day03/Day03_test")
    check(part2(testInput) == 467835)

    val input = readInput("Day03/Day03")
    println(part1(input))
    println(part2(input))
}