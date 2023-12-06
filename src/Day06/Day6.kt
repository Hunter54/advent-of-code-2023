package Day06

import readInput
import kotlin.math.*
import kotlin.system.measureTimeMillis

const val dayNumber = "06"

typealias Race = Pair<Long, Long>

class Day06 {

    fun parseInputDataPart1(input: List<String>): List<Race> {
        val timeList = input.first().split(" ").drop(1).filterNot { it.isBlank() }.map { it.toLong() }
        val distanceList = input.last().split(" ").drop(1).filterNot { it.isBlank() }.map { it.toLong() }
        return timeList.zip(distanceList)
    }

    fun parseInputDataPart2(input: List<String>): Race {
        val time = input.first().split(" ").drop(1).filterNot { it.isBlank() }.joinToString("").toLong()
        val distance = input.last().split(" ").drop(1).filterNot { it.isBlank() }.joinToString("").toLong()
        return time to distance
    }

    fun quadraticFormula(time: Double, record: Double): Pair<Int,Int>{
        val time1 = ((-time + sqrt(time.pow(2)-(4*-1)*-record))/(2*-1))
        val time2 = (-time - sqrt(time.pow(2)-(4*-1)*-record))/(2*-1)
        val epsilon = 1e-10
        val (rounded1, rounded2) = if(time1 > time2){
            floor(time1 - epsilon) to ceil(time2 + epsilon)
        } else {
            floor(time2 - epsilon) to ceil(time1 + epsilon)
        }
        return rounded1.toInt() to rounded2.toInt()
    }

    fun solvePart1(race: List<Race>): Int{
        return race.fold(1){ sum, (time,record) ->
            val (time1, time2) = quadraticFormula(time.toDouble(),record.toDouble())
            sum*(abs(time1-time2)+1)
        }
    }

    fun part1(input: List<String>): Int {
        val races = parseInputDataPart1(input)
        return solvePart1(races)
    }

    fun part2(input: List<String>): Int {
        val race = parseInputDataPart2(input)
        return solvePart1(listOf(race))
    }
}

fun main() {

    val day = Day06()

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

    check(testOutput1 == 288)
    println("✅ part one with $testOutput1 ✅ in $testOutput1Millis ms")

    val testOutput2Millis = measureTimeMillis {
        testOutput2 = part2(testInput)
    }
    check(testOutput2 == 71503)
    println("✅ part two with $testOutput2 ✅ in $testOutput2Millis ms")

    println("******************")

    val input = readInput("Day$dayNumber/Day${dayNumber}")
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