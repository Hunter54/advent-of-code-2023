package Day08

import lcm
import readInput
import kotlin.system.measureTimeMillis

const val dayNumber = "08"

class Day08 {

    fun parsePoints(input: List<String>): Map<String, Pair<String, String>> {
        return input.drop(2).associate { line ->
            line.split(" = ").let {
                it.first() to it.last().drop(1).dropLast(1)
                    .split(", ")
                    .let { (left, right) -> left to right }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val steps = input.first()
        val points = parsePoints(input)
        var node = "AAA"
        var nrOfSteps = 0
        while(node != "ZZZ"){
            node = if(steps[nrOfSteps % steps.length] == 'L') points[node]!!.first else points[node]!!.second
            nrOfSteps++
        }
        return nrOfSteps
    }

    fun part2(input: List<String>): Long {
        val steps = input.first()
        val points = parsePoints(input)
        return points.keys.filter { it.endsWith("A") }.map {
            var nrOfSteps = 0L
            var step = it
            while(!step.endsWith("Z")){
                step = if(steps[(nrOfSteps % steps.length).toInt()] == 'L') points[step]!!.first else points[step]!!.second
                nrOfSteps++
            }
            println(nrOfSteps)
            nrOfSteps
        }.reduce{acc, nrOfSteps ->
            acc.lcm(nrOfSteps)
        }

    }
}

fun main() {

    val day = Day08()

    fun part1(input: List<String>): Int {
        return day.part1(input)
    }

    fun part2(input: List<String>): Long {
        return day.part2(input)
    }

    val testInput = readInput("Day$dayNumber/Day${dayNumber}_test")

    println("***** Day $dayNumber *****")
    var testOutput1: Int
    var testOutput2: Long
    val testOutput1Millis = measureTimeMillis {
        testOutput1 = part1(testInput)
    }

    check(testOutput1 == 6)
    println("✅ part one with $testOutput1 ✅ in $testOutput1Millis ms")

    val testInput2 = readInput("Day$dayNumber/Day${dayNumber}_test2")
    val testOutput2Millis = measureTimeMillis {
        testOutput2 = part2(testInput2)
    }
    check(testOutput2 == 6L)
    println("✅ part two with $testOutput2 ✅ in $testOutput2Millis ms")

    println("******************")

    val input = readInput("Day${dayNumber}/Day${dayNumber}")
    var output1: Int
    var output2: Long
    val output1Millis = measureTimeMillis {
//        output1 = part1(input)
    }
//    println("💡 part one with $output1 💡in $output1Millis ms")
    val output2Millis = measureTimeMillis {
        output2 = part2(input)
    }
    println("💡 part two with $output2 💡in $output2Millis ms")
}