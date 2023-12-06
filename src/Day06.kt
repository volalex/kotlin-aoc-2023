import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    data class Race(val allowedTime: Long, val maxDistance: Long)

    fun parseRaces(input: List<String>): List<Race> {
        val times = input.first().split(":").last().trim().split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        val distances = input.last().split(":").last().trim().split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        return times.zip(distances).map { Race(it.first, it.second) }
    }

    fun parseKerning(input: List<String>): Race {
        val time = input.first().split(":").last().replace(" ", "").toLong()
        val distance = input.last().split(":").last().replace(" ", "").toLong()
        return Race(time, distance)
    }


    fun numberOfWinConditions(race: Race): Long {
        val targetDistance = race.maxDistance + 1
        val discriminant = race.allowedTime * race.allowedTime - 4 * targetDistance
        val root1 = ceil((-race.allowedTime + sqrt(discriminant.toDouble())) / -2).toLong()
        val root2 = floor((-race.allowedTime - sqrt(discriminant.toDouble())) / -2).toLong()
        return root2 - root1 + 1
    }


    fun part1(input: List<String>): Long {
        val races = parseRaces(input)
        return races.map { numberOfWinConditions(it) }.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val race = parseKerning(input)
        return numberOfWinConditions(race)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
