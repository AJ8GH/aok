package io.github.aj8gh.aok.y24.d21

import kotlin.math.abs

private const val EMPTY = '#'
private const val A = 'A'
private const val UP = '^'
private const val LEFT = '<'
private const val DOWN = 'v'
private const val RIGHT = '>'
private const val ZERO = '0'
private const val ONE = '1'
private const val TWO = '2'
private const val THREE = '3'
private const val FOUR = '4'
private const val FIVE = '5'
private const val SIX = '6'
private const val SEVEN = '7'
private const val EIGHT = '8'
private const val NINE = '9'

private val DIR_PAD = mapOf(
  Pair(EMPTY, Pair(0, 0)), Pair(UP, Pair(0, 1)), Pair(A, Pair(0, 2)),
  Pair(LEFT, Pair(1, 0)), Pair(DOWN, Pair(1, 1)), Pair(RIGHT, Pair(1, 2)),
)

private val NUM_PAD = mapOf(
  Pair(SEVEN, Pair(0, 0)), Pair(EIGHT, Pair(0, 1)), Pair(NINE, Pair(0, 2)),
  Pair(FOUR, Pair(1, 0)), Pair(FIVE, Pair(1, 1)), Pair(SIX, Pair(1, 2)),
  Pair(ONE, Pair(2, 0)), Pair(TWO, Pair(2, 1)), Pair(THREE, Pair(2, 2)),
  Pair(EMPTY, Pair(3, 0)), Pair(ZERO, Pair(3, 1)), Pair(A, Pair(3, 2)),
)

private val RGX = Regex("\\d+")

fun part1(input: List<String>) = input.sumOf { score(it) }

fun part2(input: List<String>) = 0

private fun score(code: String): Int {
  val robot1 = directions(code, NUM_PAD)
  val robot2 = directions(robot1, DIR_PAD)
  val final = directions(robot2, DIR_PAD)
  return RGX.find(code)!!.value.toInt() * final.length
}

private fun directions(code: String, pad: Map<Char, Pair<Int, Int>>): String {
  val sb = StringBuilder()
  val processed = StringBuilder()
  var currentTarget: Char
  var currentSource = A
  for (c in code) {
    processed.append(c)
    currentTarget = c
    val sourcePos = pad[currentSource]!!
    val targetPos = pad[currentTarget]!!
    val xDiff = sourcePos.second - targetPos.second
    val yDiff = sourcePos.first - targetPos.first
    val empty = pad[EMPTY]!!
    if (sourcePos.first == empty.first) {
      repeat(abs(yDiff)) { sb.append(if (yDiff < 0) DOWN else UP) }
      repeat(abs(xDiff)) { sb.append(if (xDiff < 0) RIGHT else LEFT) }
    } else {
      repeat(abs(xDiff)) { sb.append(if (xDiff < 0) RIGHT else LEFT) }
      repeat(abs(yDiff)) { sb.append(if (yDiff < 0) DOWN else UP) }
    }
    currentSource = currentTarget
    sb.append(A)
  }

  return sb.toString()
}
/*

029A:
<A^A^^>AvvvA
v<<A>>^A<A>A<AAv>A^Av<AAA>^A


980A
179A
456A
379A

+---+---+---+
| 7 | 8 | 9 |
+---+---+---+
| 4 | 5 | 6 |
+---+---+---+
| 1 | 2 | 3 |
+---+---+---+
    | 0 | A |
    +---+---+

    +---+---+
    | ^ | A |
+---+---+---+
| < | v | > |
+---+---+---+

 */
