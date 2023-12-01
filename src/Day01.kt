fun main() {
    fun part1(input: List<String>): Int {
        val finalValue = input.fold(0) { sum, line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            val lineValue = listOf(firstDigit,lastDigit).joinToString()
            sum + lineValue.toInt()
        }
        return finalValue
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
