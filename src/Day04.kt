fun main() {
    fun <T : Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>): Boolean {
        return this.start <= other.start && this.endInclusive >= other.endInclusive
    }

    fun <T : Comparable<T>> ClosedRange<T>.overlap(other: ClosedRange<T>): Boolean {
        return this.start >= other.start && this.start <= other.endInclusive || this.endInclusive >= other.start && this.endInclusive <= other.endInclusive
    }

    fun part1(input: List<String>): Int = input.map { line ->
            line.split(",").map { pair ->
                pair.split("-")
            }
        }.map { pair ->
            pair.map {
                it[0].toInt().rangeTo(it[1].toInt())
            }
        }.filter { ranges ->
            ranges[0].contains(ranges[1]) || ranges[1].contains(ranges[0])
        }.size

    fun part2(input: List<String>): Int = input.map { line ->
        line.split(",").map { pair ->
            pair.split("-")
        }
    }.map { pair ->
        pair.map {
            it[0].toInt().rangeTo(it[1].toInt())
        }
    }.filter { ranges ->
        ranges[0].overlap(ranges[1]) || ranges[1].overlap(ranges[0])
    }.size

    val input = readLines("Day04")
    println(part1(input))
    println(part2(input))

    val testInput = readLines("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)
}

/*


.2345678.  2-8
..34567..  3-7

.....6...  6-6
...456...  4-6
*/
