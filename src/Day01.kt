fun main() {
    val replacementTable = mapOf(
        "zero" to 0,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part1(input: List<String>): Int {
        return input.sumOf { s ->
            val numbers = s.filter { c -> c.isDigit() }
            (numbers.first().toString() + numbers.last()).toInt()
        }
    }

    fun toIntList(input: String): List<Int> {
        return input.windowedSequence(5, partialWindows = true)
            .mapNotNull {
                when {
                    it.first().isDigit() -> it.first().digitToInt()
                    else -> {
                        it.indices.reversed().map { i ->
                            it.slice(0..i)
                        }.firstNotNullOfOrNull(replacementTable::get)
                    }
                }
            }.toList()
    }

    fun part2(input: List<String>):Int {
        return input.map { toIntList(it) }.sumOf { it.first() * 10 + it.last() }
    }

    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_2_test")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
