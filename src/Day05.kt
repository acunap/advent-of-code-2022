import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun parseCrates(input: List<String>): MutableList<MutableList<String>> {
        return input.map {
                row -> row.chunked(4).map {
                    it.trim().replace("[\\[\\]]".toRegex(), "")
                }
            }
            .dropLast(1)
            .reversed()
            .let { crates ->
                val transposed = MutableList<MutableList<String>>(crates.first().size) { mutableListOf() }

                crates.forEach { strings ->
                    strings.forEachIndexed { index, s ->
                        transposed[index].add(s)
                    }
                }

                transposed.map { row ->
                    row.filter {
                        !it.isNullOrEmpty()
                    }
                } as MutableList<MutableList<String>>
            }
    }

    fun parseMoves(input: List<String>): List<List<Int>> {
        val regex = "(\\d+)".toRegex()

        return input.map {
            regex.findAll(it)
                .map { matches -> matches.value.toInt() }
                .mapIndexed { index, i -> if (index != 0) i - 1 else i }
                .toList()
        }
    }

    fun part1(input: String): String {
        val parsedData = input.split("\n\n").map { it.lines() }
        val crates = parseCrates(parsedData[0])
        val moves = parseMoves(parsedData[1])

        moves.forEach { (move, from, to) ->
            (1..move).forEach { _ ->
                val moved = crates[from].removeLast()

                if (!moved.isNullOrEmpty()) {
                    crates[to].add(moved)
                }
            }
    }

        return crates.joinToString("") { it.last() }
    }

    fun part2(input: String): String {
        val parsedData = input.split("\n\n").map { it.lines() }
        val crates = parseCrates(parsedData[0])
        val moves = parseMoves(parsedData[1])

        moves.forEach { (move, from, to) ->
            val moved = mutableListOf<String>()

            (1..move).forEach { _ ->
                moved.add(crates[from].removeLast())
            }

            crates[to].addAll(moved.reversed())
        }

        return crates.joinToString("") { it.last() }
    }

    val time = measureTime {
        val input = readText("Day05")
        println(part1(input))
        println(part2(input))
    }

    println("Real data time $time")

    val timeTest = measureTime {
        val testInput = readText("Day05_test")
        check(part1(testInput) == "CMZ")
        check(part2(testInput) == "MCD")
    }

    println("Test data time $timeTest.")
}
