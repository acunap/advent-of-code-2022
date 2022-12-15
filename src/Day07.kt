import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun parseDirectories(input: List<String>) = buildMap {
        put("", 0)

        var cwd = ""

        for(line in input) {
            val match = """[$] cd (.*)|(\d*).*""".toRegex().matchEntire(line) ?: continue

            match.groups[1]?.value?.let { dir ->
                cwd = when (dir) {
                    "/" -> ""
                    ".." -> cwd.substringBeforeLast("/", "")
                    else -> if (cwd.isEmpty()) dir else "$cwd/$dir"
                }
            } ?: match.groups[2]?.value?.toIntOrNull()?.let { size ->
                var dir = cwd

                while (true) {
                    put(dir, getOrElse(dir) { 0 } + size)

                    if (dir.isNullOrEmpty()) break

                    dir = dir.substringBeforeLast("/", "")
                }
            }
        }
    }

    fun part1(input: List<String>) = parseDirectories(input).values.sumOf { if (it < 100_000) it else 0 }

    fun part2(input: List<String>): Int {
        val sizes = parseDirectories(input)
        val totalSize = sizes.values.max()

        return sizes.values.filter { 70_000_000 - totalSize + it > 30_000_000 }.min()
    }

    val time = measureTime {
        val input = readLines("Day07")
        println(part1(input))
        println(part2(input))
    }

    println("Real data time $time")

    val timeTest = measureTime {
        val testInput = readLines("Day07_test")
        check(part1(testInput) == 95_437)
        check(part2(testInput) == 24_933_642)
    }

    println("Test data time $timeTest.")
}
