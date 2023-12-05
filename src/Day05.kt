import kotlin.math.max
import kotlin.math.min

fun main() {
    fun LongRange.isIntersectsWith(other: LongRange): Boolean =
        this.first >= other.first && this.first <= other.last || this.first <= other.first && this.last >= other.first

    fun LongRange.intersect(other: LongRange): LongRange? =
        if (this.isIntersectsWith(other)) {
            LongRange(max(this.first, other.first), min(this.last, other.last))
        } else {
            null
        }

    fun LongRange.partialIntersect(other: LongRange): Pair<LongRange?, List<LongRange>> =
        when (val possibleIntersection = this.intersect(other)) {
            null -> Pair(null, listOf(this))
            else -> {
                val nonIntersectedParts = buildList {
                    if (this@partialIntersect.first < possibleIntersection.first) {
                        add(this@partialIntersect.first..possibleIntersection.first)
                    }
                    if (this@partialIntersect.last > possibleIntersection.last) {
                        add(possibleIntersection.last..this@partialIntersect.last)
                    }
                }
                Pair(possibleIntersection, nonIntersectedParts)
            }
        }

    fun LongRange.shiftRange(value: Long): LongRange {
        return this.first + value..this.last + value
    }

    data class AlmanacMapper(val sourceRange: LongRange, val gap: Long)
    data class Almanac(val seeds: List<LongRange>, val maps: List<List<AlmanacMapper>>)

    fun mapperFromString(string: String): AlmanacMapper {
        val parts = string.trim().split(" ")
        val from = parts[1].toLong()
        val to = parts[0].toLong()
        val size = parts[2].toLong()
        return AlmanacMapper(from..from + size, to - from)
    }

    fun parseMappers(input: List<String>): List<List<AlmanacMapper>> {
        return input.drop(1).filter { it.isNotBlank() }.fold(mutableListOf<MutableList<AlmanacMapper>>()) { acc, next ->
            if (next.contains(":")) {
                acc.add(mutableListOf())
                acc
            } else {
                val rangesList = acc.last()
                rangesList.add(mapperFromString(next))
                acc
            }
        }
    }

    fun parseInputForPart1(input: List<String>): Almanac {
        val seeds = input.first().split(":").last().trim().split(" ")
            .map { it.toLong() }.map { it..it }
        val maps = parseMappers(input)
        return Almanac(seeds, maps)
    }

    fun parseInputForPart2(input: List<String>): Almanac {
        val seeds = input.first().split(":")
            .last().trim().split(" ")
            .map { it.toLong() }.chunked(2)
            .map { it.first()..it.first() + it.last() }
        val maps = parseMappers(input)
        return Almanac(seeds, maps)
    }

    fun applyMapper(seeds: List<LongRange>, mapper: AlmanacMapper): Pair<List<LongRange>, List<LongRange>> {
        val changedSeeds = mutableListOf<LongRange>()
        val unchangedSeeds = mutableListOf<LongRange>()
        seeds.map { it.partialIntersect(mapper.sourceRange) }.forEach { (possibleIntersection, rest) ->
            if (possibleIntersection != null) {
                changedSeeds.add(possibleIntersection.shiftRange(mapper.gap))
            }
            unchangedSeeds.addAll(rest)
        }
        return Pair(unchangedSeeds, changedSeeds)
    }

    fun findMinLocation(almanac: Almanac): Long {
        return almanac.maps.fold(almanac.seeds) { seeds, mappers ->
            val stepResult = mappers.fold(Pair(seeds, listOf<LongRange>())) { acc, mapper ->
                val (unchangedSeeds, changedSeeds) = applyMapper(acc.first, mapper)
                Pair(unchangedSeeds, acc.second + changedSeeds)
            }
            stepResult.first + stepResult.second
        }.minBy { it.first }.first
    }

    fun part1(input: List<String>): Long {
        val almanac = parseInputForPart1(input)
        return findMinLocation(almanac)
    }

    fun part2(input: List<String>): Long {
        val almanac = parseInputForPart2(input)
        return findMinLocation(almanac)
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
