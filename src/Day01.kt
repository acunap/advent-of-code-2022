fun main() {
    fun parseElves(input: String) = input.split("\n\n").map { elf ->
        elf.lines().map { it.toInt() }
    }

    fun takeTopNElves(elves: List<List<Int>>, n: Int) = elves.map { it.sum() }
        .sortedDescending()
        .take(n)

    fun part1(input: String): Int {
        return takeTopNElves(parseElves(input), 1).sum()
    }

    fun part2(input: String): Int {
        return takeTopNElves(parseElves(input), 3).sum()
    }

    val input = readText("Day01")
    println(part1(input))
    println(part2(input))

    val testInput = readText("Day01_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 85)
}
