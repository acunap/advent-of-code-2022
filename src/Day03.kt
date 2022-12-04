fun main() {
    fun Char.itemCodeToPoints(): Int = if (isLowerCase()) code - 96 else code - 38

    fun String.mapCharsToIndexes(): MutableMap<Char, MutableList<Int>> {
        val charsToIndexes = mutableMapOf<Char, MutableList<Int>>()

        this.forEachIndexed { index, char ->
            if (charsToIndexes.containsKey(char)) {
                charsToIndexes[char]?.add(index)
            } else {
                charsToIndexes[char] = mutableListOf(index)
            }
        }

        return charsToIndexes
    }

    fun part1(input: List<String>): Int = input.sumOf { row ->
        val rucksackChangeIndex = row.count() / 2
        row.mapCharsToIndexes().filter { char ->
            char.value.any { index -> index < rucksackChangeIndex } && char.value.any { index -> index >= rucksackChangeIndex }
        }.map { char -> char.key.itemCodeToPoints() }.sum()
    }

    fun part2(input: List<String>): Int =input
        .chunked(3)
        .map { group -> group.map { it.toSet() } }
        .sumOf { group ->
            group.first().filter { char ->
                val result = group.all {
                    it.contains(char)
                }

                result
            }.sumOf { it.itemCodeToPoints() }
        }

    val input = readLines("Day03")
    println(part1(input))
    println(part2(input))

    val testInput = readLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)
}
