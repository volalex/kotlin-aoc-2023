fun main() {
    val possibleInGame = mapOf(
        "blue" to 14,
        "green" to 13,
        "red" to 12
    )

    fun maxInGame(game: String): Map<String, Int> {
        return game.split(";")
            .flatMap { it.split(",") }
            .map { it.trim() }
            .map {
                val colorAndQuantity = it.split(" ")
                Pair(colorAndQuantity.last().trim(), colorAndQuantity.first().trim().toInt())
            }.groupingBy { it.first }.fold(-1, operation = { accumulator, element ->
                if (accumulator < element.second) {
                    element.second
                } else {
                    accumulator
                }
            })
    }

    fun gameIsPossible(maxValues: Map<String, Int>) =
        maxValues.filter { possibleInGame.getValue(it.key) < it.value }.isEmpty()

    fun parseGames(input: List<String>) = input.map {
        val idAndGame = it.split(":")
        Pair(idAndGame.first().split(" ").last().toInt(), maxInGame(idAndGame.last()))
    }

    fun part1(input: List<String>): Int {
        return parseGames(input).filter {
            gameIsPossible(it.second)
        }.sumOf { it.first }
    }


    fun part2(input: List<String>): Int {
        return parseGames(input).sumOf {
            it.second.values.reduce { acc, i -> acc * i }
        }
    }

    val testInput = readInput("Day02_test")
    val testInput2 = readInput("Day02_2_test")
    check(part1(testInput) == 8)
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
