import kotlin.math.max
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun part1(input: List<String>) : Int {
        var count = input.size * 2 + input.first().count() * 2 - 4

        for (row in 1 until input.lastIndex) {
            for (column in 1 until input.first().lastIndex) {
                val current = input[row][column].digitToInt()

                val left = input[row].substring(0, column).map { it.digitToInt() }.max()
                val right = input[row].substring(column + 1).map { it.digitToInt() }.max()

                val column = input.map { it[column] }
                val up = column.slice(0 until row).map { it.digitToInt() }.max()
                val down = column.slice(row + 1 until column.size).map { it.digitToInt() }.max()


                if (listOf(left, right, up, down).min() < current) {
                    count++
                }
            }
        }

        return count
    }

    fun part2(input: List<String>) : Int {
        fun calculateScenicLine(line: List<Int>, current: Int) = line.takeWhile {
            it < current
        }.size.let {
            if (it == line.size) it else it + 1
        }

        var maxScenicScore = 0

        for (row in 1 until input.lastIndex) {
            for (column in 1 until input.first().lastIndex) {
                val current = input[row][column].digitToInt()

                val left = calculateScenicLine(input[row].substring(0, column).map { it.digitToInt() }.reversed(), current)
                val right = calculateScenicLine(input[row].substring(column + 1).map { it.digitToInt() }, current)

                val column = input.map { it[column] }
                val up = calculateScenicLine(column.slice(0 until row).map { it.digitToInt() }.reversed(), current)
                val down = calculateScenicLine(column.slice(row + 1 until column.size).map { it.digitToInt() }, current)

                val scenicScore = left * right * up * down
                maxScenicScore = max(maxScenicScore, scenicScore)
            }
        }

        return maxScenicScore
    }

    val time = measureTime {
        val input = readLines("Day08")
        println(part1(input))
        println(part2(input))
    }

    println("Real data time $time")

    val timeTest = measureTime {
        val testInput = readLines("Day08_test")
        check(part1(testInput) == 21)
        check(part2(testInput) == 8)
    }

    println("Test data time $timeTest.")
}
