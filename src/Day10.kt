import kotlin.math.max
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    fun part1(input: List<String>) : Int {
        val markedCycleIndexes = listOf(20, 60, 100, 140, 180, 220)
        var cyclesIndex = 1
        var signalStrength = 1
        var accumulateMarkedCycleIndexesSignalStrength = 0

        fun increaseCyclesIndex(steps: Int) {
            for (i in 0 until steps) {
                if (markedCycleIndexes.contains(cyclesIndex)) {
                    accumulateMarkedCycleIndexesSignalStrength += cyclesIndex * signalStrength
                }

                cyclesIndex++
            }
        }

        input.forEach { line ->
            when {
                line == "noop" -> {
                    increaseCyclesIndex(1)
                }
                line.startsWith("addx") -> {
                    increaseCyclesIndex(2)

                    val value = line.split(" ").last().toInt()
                    signalStrength += value
                }
            }
        }

        return accumulateMarkedCycleIndexesSignalStrength
    }

    fun part2(input: List<String>) : Boolean {
        val ctr = MutableList(6 * 40) { "." }
        var cyclesIndex = 1
        var signalStrength = 1

        fun drawCtr() {
            ctr.chunked(40).forEach { row ->
                row.forEach { element ->
                    print("$element")
                }
                println()
            }
            println()
            println()
        }

        fun increaseCyclesIndex(steps: Int) {
            for (i in 0 until steps) {
                val signalIndex = max((cyclesIndex % 40) - 1, 0)

                if (listOf(signalStrength - 1, signalStrength, signalStrength + 1).contains(signalIndex)) {
                    ctr[cyclesIndex - 1] = "#"
                }

                cyclesIndex++
            }
        }

        input.forEach { line ->
            when {
                line == "noop" -> {
                    increaseCyclesIndex(1)
                }
                line.startsWith("addx") -> {
                    increaseCyclesIndex(2)

                    val value = line.split(" ").last().toInt()
                    signalStrength += value
                }
            }
        }

        drawCtr()

        return true
    }

    val time = measureTime {
        val input = readLines("Day10")
        println(part1(input))
        println(part2(input))
    }

    println("Real data time $time")

    val timeTest = measureTime {
        val inputTest = readLines("Day10_test")
        check(part1(inputTest) == 13140)
        check(part2(inputTest))
    }

    println("Test data time $timeTest.")
}
