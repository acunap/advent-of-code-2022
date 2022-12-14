import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun parsePairs(input: List<String>) = input.map { line ->
        line.split(",").map {
            it.split("-").map(String::toInt).let { (a, b) -> (a..b).toSet() } }
    }

    fun part1(input: List<String>) = parsePairs(input).count { (a, b) -> a.containsAll(b) || b.containsAll(a) }

    fun part2(input: List<String>) = parsePairs(input).count { (a, b) -> a.intersect(b).isNotEmpty() }

    val time = measureTime {
        val input = readLines("Day04")
        println(part1(input))
        println(part2(input))
    }

    println("Real data time $time")

    val timeTest = measureTime {
        val testInput = readLines("Day04_test")
        check(part1(testInput) == 2)
        check(part2(testInput) == 4)
    }

    println("Test data time $timeTest.")
}
