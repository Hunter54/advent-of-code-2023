package Day02

import readInput

typealias Round = List<GrabbedCubes>

data class Game(
    val gameNumber: Int,
    val rounds: List<Round>
)

data class GrabbedCubes(
    val color: String,
    val amount: Int
)

fun main() {

    fun parseLine(line: String): Game {
        val gameRegex = Regex("""(\d+): (.+)""")
        val roundRegex = Regex("""(\d+) (\w+)""")

        val matches = gameRegex.find(line) ?: error("No match")
        val gameId = matches.groupValues[1].toInt()
        val roundsSplit = matches.groupValues[2].trim().split(";")
        val rounds = roundsSplit.map { round ->
            roundRegex.findAll(round).map {
                GrabbedCubes(it.groupValues[2], it.groupValues[1].toInt())
            }.toList()
        }
        return Game(gameId, rounds)
    }

    fun part1(input: List<String>): Int {
        val numberOfCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)
        val filteredGames = input.map {
            parseLine(it)
        }.filter {
            !it.rounds.any { round ->
                round.any { grabbedCubes ->
                    numberOfCubes[grabbedCubes.color]!! < grabbedCubes.amount
                }
            }
        }

        return filteredGames.sumOf { it.gameNumber }
    }

    fun part2(input: List<String>): Int {
        val games = input.map {
            parseLine(it)
        }
        val powerOfCubes = games.sumOf { game ->
            val mapMinimumValues = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            game.rounds.forEach { round ->
                round.forEach { cubes ->
                    if (cubes.amount > mapMinimumValues[cubes.color]!!) {
                        mapMinimumValues[cubes.color] = cubes.amount
                    }
                }
            }
            val power = mapMinimumValues.values.fold(1) { power, value ->
                power * value
            }
            power
        }
        return powerOfCubes
    }

    val testInput = readInput("Day02/Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02/Day02")
    println(part1(input))
    println(part2(input))
}