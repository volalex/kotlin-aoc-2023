fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day06_test")
    val testInput2 = readInput("Day06_2_test")
    check(part1(testInput) == 0)
    check(part2(testInput2) == 0)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
