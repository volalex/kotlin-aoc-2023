fun main() {
    data class BoundedSymbol(val row: Int, val position: Int, val symbol: Char)
    data class PartNum(val num: Int, val adjSymbols: List<BoundedSymbol>)

    fun findAdjSymbols(scheme: List<CharArray>, numRow: Int, numLeft: Int, numRight: Int): List<BoundedSymbol> {
        val rowRange = numRow - 1..numRow + 1
        val colRange = numLeft - 1..numRight + 1
        return buildList {
            for (row in rowRange) {
                for (col in colRange) {
                    val symbol = scheme.getOrNull(row)?.getOrNull(col)
                    if (symbol != null && symbol != '.' && !symbol.isDigit()) {
                        add(BoundedSymbol(row, col, symbol))
                    }
                }
            }
        }
    }

    fun readScheme(scheme: List<CharArray>): List<PartNum> {
        return buildList {
            for (row in scheme.indices) {
                var numString = ""
                var left = 0
                var right = 0
                for (symbolIndex in scheme[row].indices) {
                    val symbol = scheme[row][symbolIndex]
                    right = symbolIndex
                    if (symbol.isDigit()) {
                        if (numString == "") {
                            left = symbolIndex
                        }
                        numString += symbol
                    } else if (numString != "") {
                        add(PartNum(numString.toInt(), findAdjSymbols(scheme, row, left, right - 1)))
                        numString = ""
                    }
                }
                if (numString != "") {
                    add(PartNum(numString.toInt(), findAdjSymbols(scheme, row, left, right)))
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        return readScheme(input.map { it.toCharArray() })
            .filter { it.adjSymbols.isNotEmpty() }
            .sumOf { it.num }
    }

    fun part2(input: List<String>): Int {
        return readScheme(input.map { it.toCharArray() })
            .filter { it.adjSymbols.any { symbol -> symbol.symbol == '*' } }
            .flatMap { partNum -> partNum.adjSymbols.map { Pair(it, partNum) } }
            .groupBy({ it.first }, { it.second })
            .filter { it.value.size == 2 }
            .mapValues { it.value.first().num * it.value.last().num }
            .values.sum()
    }

    val part1Test = readInput("Day03_test")
    val part2Test = readInput("Day03_2_test")
    val input = readInput("Day03")
    check(part1(part1Test) == 4361)
    check(part2(part2Test) == 467835)
    part1(input).println()
    part2(input).println()
}
