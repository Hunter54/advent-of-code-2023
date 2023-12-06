import kotlin.system.measureTimeMillis

const val dayNumber = "00"
class Day00 {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }
}

fun main() {

    val day = Day00()

    fun part1(input: List<String>): Int {
        return day.part1(input)
    }

    fun part2(input: List<String>): Int {
        return day.part2(input)
    }

    val testInput = readInput("Day$dayNumber/Day${dayNumber}_test")

    println("***** Day $dayNumber *****")
    var testOutput1: Int
    var testOutput2: Int
    val testOutput1Millis = measureTimeMillis {
        testOutput1 = part1(testInput)
    }

    check(testOutput1 == 0)
    println("✅ part one with $testOutput1 ✅ in $testOutput1Millis ms")

    val testOutput2Millis = measureTimeMillis {
        testOutput2 = part2(testInput)
    }
    check(testOutput2 == 0)
    println("✅ part two with $testOutput2 ✅ in $testOutput2Millis ms")

    println("******************")

    val input = readInput("Day${dayNumber}/Day${dayNumber}")
    var output1: Int
    var output2: Int
    val output1Millis = measureTimeMillis {
        output1 = part1(input)
    }
    println("💡 part one with $output1 💡in $output1Millis ms")
    val output2Millis = measureTimeMillis {
        output2 = part2(input)
    }
    println("💡 part two with $output2 💡in $output2Millis ms")
}