fun main() {
    fun readCard(card: String): Pair<Set<Int>, Set<Int>> {
        val parts = card.split(":").last().trim().split("|")
        return Pair(parts.first().trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet(),
            parts.last().trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        )
    }

    fun part1(input: List<String>): Int {
        return input.map { readCard(it) }.map {
            it.second.fold(0) { acc, number ->
                when {
                    it.first.contains(number) && acc == 0 -> 1
                    it.first.contains(number) -> acc * 2
                    else -> acc
                }
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val winnings = input
            .map { readCard(it) }
            .map { it.second.count { number -> it.first.contains(number) } }
        val copies = winnings.map { 1 }.toIntArray()
        for (index in winnings.indices) {
            val winCount = winnings[index]
            val copyCount = copies[index]
            for (sub in index + 1..index + winCount) {
                copies[sub] += copyCount
            }
        }
        return copies.sum()
    }

    val testInput = readInput("Day04_test")
    val testInput2 = readInput("Day04_2_test")
    check(part1(testInput) == 13)
    check(part2(testInput2) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
