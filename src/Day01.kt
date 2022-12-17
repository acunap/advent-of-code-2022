fun String.parseElves() = this.split("\n\n").map { elf -> elf.lines().map { it.toInt() } }

fun List<List<Int>>.takeTopNElves(n: Int) = this.map { it.sum() }.sortedDescending().take(n)

fun main() {
    val filename = "Day01"

    check(readText("$filename-test").parseElves().takeTopNElves(1).sum() == 24000)
    check(readText("$filename-test").parseElves().takeTopNElves(3).sum() == 45000)

    readText(filename).parseElves().takeTopNElves(1).sum().also { println("Part 1 solution: $it") }
    readText(filename).parseElves().takeTopNElves(3).sum().also { println("Part 2 solution: $it") }
}
