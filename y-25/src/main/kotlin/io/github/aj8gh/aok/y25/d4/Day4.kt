package io.github.aj8gh.aok.y25.d4

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2

private const val ROLL = '@'
private const val EMPTY = '.'
private const val THRESHOLD = 4

fun part1(input: List<String>) = findAccessible(parse(input), LEVEL_1)

fun part2(input: List<String>): Int {
  var total = 0
  val grid = parse(input)
  while (true) {
    val removed = findAccessible(grid, LEVEL_2)
    if (removed == 0) return total
    total += removed
  }
}

private fun parse(input: List<String>) = input.map { it.toMutableList() }

private fun findAccessible(input: List<MutableList<Char>>, level: Int): Int {
  var total = 0
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (isAccessibleRoll(Pair(i, j), input)) {
        if (level == LEVEL_2) {
          input[i][j] = EMPTY
        }
        total++
      }
    }
  }
  return total
}

private fun isAccessibleRoll(point: Pair<Int, Int>, input: List<List<Char>>): Boolean {
  if (input[point.first][point.second] != ROLL) {
    return false
  }

  val points = listOf(
    Pair(point.first + 1, point.second),
    Pair(point.first + 1, point.second + 1),
    Pair(point.first + 1, point.second - 1),
    Pair(point.first - 1, point.second),
    Pair(point.first - 1, point.second + 1),
    Pair(point.first - 1, point.second - 1),
    Pair(point.first, point.second + 1),
    Pair(point.first, point.second - 1),
  )
  var total = 0

  for (p in points) {
    if (
      p.first < 0
      || p.first > input.lastIndex
      || p.second < 0
      || p.second > input[p.first].lastIndex
    ) {
      continue
    }
    if (input[p.first][p.second] == ROLL && ++total >= THRESHOLD) {
      return false
    }
  }
  return true
}
