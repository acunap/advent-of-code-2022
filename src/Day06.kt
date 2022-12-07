import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun String.hasDuplicates() = this.toSet().size != this.count()

    fun part1(input: String, offset: Int): Int {
        repeat((0 until input.count() - (offset - 1)).count()) {
            if (!input.subSequence(it, it + offset).toString().hasDuplicates()) {
                return it + offset
            }
        }

        return 0
    }

    val time = measureTime {
        val input = readText("Day06")
        println(part1(input, 4))
        println(part1(input, 14))
    }

    println("Real data time $time")

    val timeTest = measureTime {
        val testInputsFirstPart = mapOf(
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
            "nppdvjthqldpwncqszvftbrmjlhg" to 6,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11
        )

        testInputsFirstPart.forEach {
            check(part1(it.key, 4) == it.value)
        }

        val testInputsSecondPart = mapOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
            "nppdvjthqldpwncqszvftbrmjlhg" to 23,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26
        )

        testInputsSecondPart.forEach {
            check(part1(it.key, 14) == it.value)
        }
    }

    println("Test data time $timeTest.")
}
