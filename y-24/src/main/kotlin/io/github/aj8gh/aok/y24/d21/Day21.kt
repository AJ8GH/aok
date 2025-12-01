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
  val robot1 = allDirections(code, NUM_PAD)
  val robot2 = robot1.flatMap { allDirections(it, DIR_PAD) }
  val final = robot2.map { directions(it, DIR_PAD) }
  val min = final.minBy { it.length }
  return RGX.find(code)!!.value.toInt() * min.length
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

private fun allDirections(
  code: String,
  pad: Map<Char, Pair<Int, Int>>,
): List<String> {
  var results = listOf<StringBuilder>()
  val processed = StringBuilder()
  var currentTarget: Char
  var currentSource = A
  for (c in code) {
    processed.append(c)
    currentTarget = c
    results = if (results.isEmpty()) {
      permutations(currentSource, currentTarget, pad)
    } else {
      results.flatMap { r ->
        val perms = permutations(currentSource, currentTarget, pad)
        val mapped = mutableListOf<StringBuilder>()
        for (p in perms) {
          mapped += StringBuilder(r).append(p)
        }
        mapped
      }
    }
    currentSource = currentTarget
  }
  return results.map { it.toString() }
}

private fun permutations(
  source: Char,
  target: Char,
  pad: Map<Char, Pair<Int, Int>>,
): List<java.lang.StringBuilder> {
  val sourcePos = pad[source]!!
  val targetPos = pad[target]!!
  val xDiff = sourcePos.second - targetPos.second
  val yDiff = sourcePos.first - targetPos.first
  val xCount = abs(xDiff)
  val yCount = abs(yDiff)
  val xDir = if (xDiff < 0) RIGHT else LEFT
  val yDir = if (yDiff < 0) DOWN else UP
  val empty = pad[EMPTY]!!

  data class Perm(val sb: StringBuilder, var numX: Int, var numY: Int)

  if (yCount == 0 && xCount == 0) return listOf(StringBuilder(A.toString()))
  if (yCount == 0) return listOf(StringBuilder(xDir.toString().repeat(xCount)).append(A))
  if (xCount == 0) return listOf(StringBuilder(yDir.toString().repeat(yCount)).append(A))

  var perms = listOf(
    Perm(StringBuilder(xDir.toString()), 1, 0),
    Perm(StringBuilder(yDir.toString()), 0, 1),
  )

  while (!perms.all { it.numX == xCount && it.numY == yCount }) {
    perms = perms.flatMap {
      val result = mutableListOf<Perm>()
      if (it.numX < xCount) {
        result.add(Perm(StringBuilder(it.sb).append(xDir), it.numX + 1, it.numY))
      }
      if (it.numY < yCount) {
        result.add(Perm(StringBuilder(it.sb).append(yDir), it.numX, it.numY + 1))
      }
      result
    }
  }

  return perms.map { it.sb.append(A) }
    .filter {
      var currentPos = sourcePos
      var outOfBounds = false
      for (dir in it) {
        if (currentPos == empty) {
          outOfBounds = true
          break
        }
        currentPos = when (dir) {
          UP -> Pair(currentPos.first - 1, currentPos.second)
          DOWN -> Pair(currentPos.first + 1, currentPos.second)
          LEFT -> Pair(currentPos.first, currentPos.second - 1)
          RIGHT -> Pair(currentPos.first, currentPos.second + 1)
          else -> break
        }
      }
      !outOfBounds
    }
}
/*

<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
v<<A>>^A<A>AvA<^AA>A<vAAA>^A
<A^A>^^AvvvA
029A

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
