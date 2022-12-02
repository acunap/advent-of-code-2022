import java.lang.IllegalArgumentException
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .map { listOf(ShapeStrategy.fromString(it[0]), ShapeStrategy.fromString(it[1])) }
            .sumOf { (opponent, player) -> player.pointsAsShape() + player.pointsAsOutcome(opponent) }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .sumOf {
                val opponent = ShapeStrategy.fromString(it[0])
                val outcome = Outcomes.fromString(it[1])
                val player = ShapeStrategy.fromOutcomeExpected(outcome, opponent)

                player.pointsAsShape() + player.pointsAsOutcome(opponent)
            }
    }

    val input = readLines("Day02")
    println(part1(input))
    println(part2(input))

    val testInput = readLines("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)
}

interface ShapeStrategy {
    fun pointsAsShape(): Int
    fun pointsAsOutcome(other: ShapeStrategy): Int

    fun winsTo(): ShapeStrategy
    fun drawsTo(): ShapeStrategy
    fun losesTo(): ShapeStrategy

    companion object {
        fun fromString(code: String) = when(code) {
            "A", "X" -> RockStrategy()
            "B", "Y" -> PaperStrategy()
            "C", "Z" -> ScissorStrategy()
            else -> throw IllegalArgumentException()
        }

        fun fromOutcomeExpected(outcome: Outcomes, opponent: ShapeStrategy) = when(outcome) {
            Outcomes.LOSE -> opponent.winsTo()
            Outcomes.DRAW -> opponent.drawsTo()
            Outcomes.WIN -> opponent.losesTo()
        }
    }
}

class RockStrategy: ShapeStrategy {
    override fun pointsAsShape() = 1

    override fun pointsAsOutcome(other: ShapeStrategy) = when(other) {
        is RockStrategy -> 3
        is PaperStrategy -> 0
        is ScissorStrategy -> 6
        else -> throw IllegalArgumentException()
    }

    override fun winsTo() = ScissorStrategy()
    override fun drawsTo() = RockStrategy()
    override fun losesTo() = PaperStrategy()
}

class PaperStrategy: ShapeStrategy {
    override fun pointsAsShape() = 2

    override fun pointsAsOutcome(other: ShapeStrategy) = when(other) {
        is RockStrategy -> 6
        is PaperStrategy -> 3
        is ScissorStrategy -> 0
        else -> throw IllegalArgumentException()
    }

    override fun winsTo() = RockStrategy()
    override fun drawsTo() = PaperStrategy()
    override fun losesTo() = ScissorStrategy()
}

class ScissorStrategy: ShapeStrategy {
    override fun pointsAsShape() = 3

    override fun pointsAsOutcome(other: ShapeStrategy) = when(other) {
        is RockStrategy -> 0
        is PaperStrategy -> 6
        is ScissorStrategy -> 3
        else -> throw IllegalArgumentException()
    }

    override fun winsTo() = PaperStrategy()
    override fun drawsTo() = ScissorStrategy()
    override fun losesTo() = RockStrategy()
}

enum class Outcomes(val code: String) {
    LOSE("X"),
    DRAW("Y"),
    WIN("Z");

    companion object {
        fun fromString(code: String) = Outcomes.values().find { outcome -> outcome.code == code } ?: throw IllegalArgumentException()
    }
}