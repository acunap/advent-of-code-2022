import kotlin.math.abs
import kotlin.math.max
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun part1(input: List<String>) : Int {
        var head = Pair(0,0)
        var tail = Pair(0, 0)

        var rows = 1
        var columns = 1

        var tailHistory = mutableSetOf(tail)

        fun draw() {
            rows = if (head.first >= rows) head.first + 1 else rows
            columns = if (head.second >= columns) head.second + 1 else columns

            for (n in 0 until rows) {
                for (m in 0 until columns) {
                    when {
                        n == head.first && m == head.second -> print("H ")
                        n == tail.first && m == tail.second -> print("T ")
                        else -> print("- ")
                    }
                }
                println()
            }

            println()
            println()
        }

        fun moveTail() {
            if (abs(head.first - tail.first) > 1 || abs(head.second - tail.second) > 1) {
                val first = (head.first - tail.first).let {
                    when {
                        it > 0 -> 1
                        it < 0 -> -1
                        else -> 0
                    }
                }

                val second = (head.second - tail.second).let {
                    when {
                        it > 0 -> 1
                        it < 0 -> -1
                        else -> 0
                    }
                }

                tail = tail.first + first to tail.second + second
                tailHistory.add(tail)
            }
        }

        input.map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .forEach {
                when (it.first) {
                    "R" -> {
                        for (i in 0 until it.second) {
                            head = head.first to head.second + 1
                            moveTail()
                        }
                    }
                    "U" -> {
                        for (i in 0 until it.second) {
                            head = head.first + 1 to head.second
                            moveTail()
                        }
                    }
                    "L" -> {
                        for (i in 0 until it.second) {
                            head = head.first to head.second - 1
                            moveTail()
                        }
                    }
                    "D" -> {
                        for (i in 0 until it.second) {
                            head = head.first - 1 to head.second
                            moveTail()
                        }
                    }
                }
            }

        return tailHistory.size
    }

    fun part2(input: List<String>) : Int {
        var knots = (0..9).map { 0 to 0 }.toMutableList()

        var rows = 1
        var columns = 1

        var tailHistory = mutableSetOf(knots.last())

        fun moveTail() {
            for (i in 1 until knots.size) {
                val current = knots[i]
                val previous = knots[i - 1]

                if (abs(previous.first - current.first) > 1 || abs(previous.second - current.second) > 1) {
                    val first = (previous.first - current.first).let {
                        when {
                            it > 0 -> 1
                            it < 0 -> -1
                            else -> 0
                        }
                    }

                    val second = (previous.second - current.second).let {
                        when {
                            it > 0 -> 1
                            it < 0 -> -1
                            else -> 0
                        }
                    }

                    knots = knots.filterIndexed { index, _ -> index != i }.toMutableList()
                    knots.add(i, current.first + first to current.second + second)

                    if (i == knots.lastIndex) {
                        tailHistory.add(current.first + first to current.second + second)
                    }
                }
            }
        }

        input.map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .forEach {
                when (it.first) {
                    "R" -> {
                        for (i in 0 until it.second) {
                            val head = knots.first()
                            knots = knots.drop(1).toMutableList()
                            knots.add(0, head.first to head.second + 1)

                            moveTail()
                        }
                    }
                    "U" -> {
                        for (i in 0 until it.second) {
                            val head = knots.first()
                            knots = knots.drop(1).toMutableList()
                            knots.add(0, head.first + 1 to head.second)

                            moveTail()
                        }
                    }
                    "L" -> {
                        for (i in 0 until it.second) {
                            val head = knots.first()
                            knots = knots.drop(1).toMutableList()
                            knots.add(0, head.first to head.second - 1)

                            moveTail()
                        }
                    }
                    "D" -> {
                        for (i in 0 until it.second) {
                            val head = knots.first()
                            knots = knots.drop(1).toMutableList()
                            knots.add(0, head.first - 1 to head.second)

                            moveTail()
                        }
                    }
                }
            }

        return tailHistory.size
    }

    val time = measureTime {
        val input = readLines("Day09")
        println(part1(input))
        println(part2(input))
    }

    println("Real data time $time")

    val timeTest = measureTime {
        check(part1(readLines("Day09_test")) == 13)
        check(part2(readLines("Day09_2_test")) == 36)
    }

    println("Test data time $timeTest.")
}
