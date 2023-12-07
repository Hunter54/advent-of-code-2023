package Day07

import readInput
import kotlin.system.measureTimeMillis

const val dayNumber = "07"

enum class WinningCombinations(val value: Int) {
    FIVE_KIND(7),
    FOUR_KIND(6),
    FULL_HOUSE(5),
    THREE_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

typealias HandBid = Pair<String, Long>
typealias TypeHandBid = Triple<WinningCombinations, String, Long>

class Day07 {

    fun parseInput(input: List<String>, isPart1: Boolean): List<HandBid> {
        return input.map {
            val bid = it.split(" ")
            val formattedHand = bid[0].map { char ->
                when (char) {
                    'A' -> 'E'
                    'K' -> 'D'
                    'Q' -> 'C'
                    'J' -> {
                        if (isPart1) 'B' else '1'
                    }

                    'T' -> 'A'
                    else -> char
                }
            }.joinToString("")
            formattedHand to bid[1].toLong()
        }
    }

    private fun getHandType(
        cardMap: Map<Char, Int>,
        hand: String,
        bid: Long
    ): TypeHandBid {
        return if (cardMap.values.any { it == 5 }) {
            Triple(WinningCombinations.FIVE_KIND, hand, bid)
        } else if (cardMap.values.any { it == 4 }) {
            Triple(WinningCombinations.FOUR_KIND, hand, bid)
        } else if (cardMap.values.any { it == 3 } && cardMap.values.any { it == 2 }) {
            Triple(WinningCombinations.FULL_HOUSE, hand, bid)
        } else if (cardMap.values.any { it == 3 }) {
            Triple(WinningCombinations.THREE_KIND, hand, bid)
        } else if (cardMap.values.count { it == 2 } == 2) {
            Triple(WinningCombinations.TWO_PAIR, hand, bid)
        } else if (cardMap.values.count { it == 2 } == 1) {
            Triple(WinningCombinations.ONE_PAIR, hand, bid)
        } else if (cardMap.values.all { it == 1 }) {
            Triple(WinningCombinations.HIGH_CARD, hand, bid)
        } else {
            error("invalid hand")
        }
    }

    fun findHandTypePart1(handBid: HandBid): TypeHandBid {
        val (hand, bid) = handBid
        val cardMap = hand.associate { card ->
            card to hand.count {
                it == card
            }
        }
        return getHandType(cardMap, hand, bid)

    }

    fun findHandTypePart2(handBid: HandBid): TypeHandBid {
        val (hand, bid) = handBid
        val cardMap = hand.associate { card ->
            card to hand.count {
                it == card
            }
        }
        //We need to remove jokers from the list, since they are never the desire value, except when all cards are joker
        val maxCard = cardMap.filterNot { it.key == '1' }
            .maxWithOrNull(compareBy({ it.value }, { it.key }))?.key ?: '1'
        val jokerRemoved = if (maxCard != '1') cardMap.filterNot { it.key == '1' } else cardMap

        val numberOfJokersInHand = cardMap['1'] ?: 0

        val updatedCardMap = jokerRemoved.mapValues { (hand, bid) ->
            if (hand == maxCard && hand != '1')
                bid + numberOfJokersInHand
            else bid
        }

        return getHandType(updatedCardMap, hand, bid)
    }

    fun computeHandRank(handsTypeBidList: List<TypeHandBid>): List<TypeHandBid> {
        return handsTypeBidList.sortedWith(compareBy({ it.first.value }, { it.second }))
    }

    private fun computeWinnings(input: List<String>, isPart1: Boolean): Long {
        val handsBidList = parseInput(input, isPart1)
        val handTypeBidList = handsBidList.map {
            if (isPart1) findHandTypePart1(it) else findHandTypePart2(it)
        }
        val rankedList = computeHandRank(handTypeBidList)
        val winnings = rankedList.foldIndexed(0L) { index, sum, item ->
            sum + (item.third * (index + 1))
        }
        return winnings
    }

    fun part1(input: List<String>): Int {
        val winnings = computeWinnings(input, true)
        return winnings.toInt()
    }

    fun part2(input: List<String>): Int {
        return computeWinnings(input, false).toInt()
    }
}

fun main() {

    val day = Day07()

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

    check(testOutput1 == 4466)
    println("✅ part one with $testOutput1 ✅ in $testOutput1Millis ms")

    val testOutput2Millis = measureTimeMillis {
        testOutput2 = part2(testInput)
    }
    check(testOutput2 == 4657)
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