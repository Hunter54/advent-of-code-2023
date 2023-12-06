package Day05

import kotlinx.coroutines.*
import readInput
import kotlin.system.measureTimeMillis

const val dayNumber = "05"

class Day05 {

    fun getSeedsPart2(seedsString: String): List<LongRange> {
        return seedsString.substringAfter(" ")
            .split(" ")
            .filterNot { it.isBlank() }
            .chunked(2)
            .map { it[0].toLong() to it[1].toLong() }
            .map { (start, length) ->
                start..<start + length
            }
    }

    fun getSeedsPart1(seedsString: String): List<Long> {
        return seedsString.substringAfter(" ")
            .split(" ")
            .filterNot { it.isBlank() }
            .map {
                it.toLong()
            }
    }

    fun getMaps(input: List<String>): List<Map<LongRange, LongRange>> {
        //after join, each section will end with two \n\n, since there is an empty line between maps
        val sections = input.drop(2)
            .joinToString("\n")
            .split("\n\n")
        val mappings = sections.map { section ->
            section.lines().drop(1).associate { sectionMapping ->
                val mapping = sectionMapping.split(" ").mapNotNull { it.toLongOrNull() }
                val range = mapping[2]
                mapping[1]..mapping[1] + range to mapping[0]..mapping[0] + range
            }
        }
        return mappings
    }

    suspend fun getLocation(input: List<String>, seeds: List<LongRange>): Int = withContext(Dispatchers.Default) {
        val listOfMappings = getMaps(input)
        return@withContext seeds.map { seedsRange ->
            async {
                seedsRange.minOf { seed ->
                    listOfMappings.fold(seed) { value, map ->
                        val destination = map.entries.firstOrNull {
                            value in it.key
                        }?.let { (source, destination) ->
                            val offsetFromStart = value - source.first
                            destination.first + offsetFromStart
                        } ?: value
                        destination
                    }
                }
            }
        }.awaitAll().min().toInt()
    }

    suspend fun part1(input: List<String>): Int {
        val seeds = getSeedsPart1(input.first()).map { it.rangeTo(it) }
        return getLocation(input, seeds)
    }

    suspend fun part2(input: List<String>): Int {
        val seeds = getSeedsPart2(input.first())
        return getLocation(input, seeds)
    }
}

fun main() = runBlocking(Dispatchers.Default) {

    val day = Day05()

    suspend fun part1(input: List<String>): Int {
        return day.part1(input)
    }

    suspend fun part2(input: List<String>): Int {
        return day.part2(input)
    }

    val testInput = readInput("Day$dayNumber/Day${dayNumber}_test")

    println("***** Day $dayNumber *****")
    var testOutput1: Int
    var testOutput2: Int
    val testOutput1Millis = measureTimeMillis {
        testOutput1 = part1(testInput)
    }

    check(testOutput1 == 35)
    println("✅ part one with $testOutput1 ✅ in $testOutput1Millis ms")

    val testOutput2Millis = measureTimeMillis {
        testOutput2 = part2(testInput)
    }
    check(testOutput2 == 46)
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