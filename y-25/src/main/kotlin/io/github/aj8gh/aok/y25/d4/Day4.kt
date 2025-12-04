package io.github.aj8gh.aok.y25.d4

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2

private const val ROLL = '@'
private const val EMPTY = '.'
private const val THRESHOLD = 4

fun part1(input: List<String>) = findAccessible(input, LEVEL_1)

fun part2(input: List<String>) = findAccessible(input, LEVEL_2)

private fun findAccessible(input: List<String>, level: Int): Int {
  val grid = input.map { it.toMutableList() }
  var total = 0
  for (i in grid.indices) {
    for (j in grid[i].indices) {
      total += score(Pair(i, j), grid, level)
    }
  }
  return total
}

private fun score(
  point: Pair<Int, Int>,
  input: List<MutableList<Char>>,
  level: Int
): Int {
  if (isOffgrid(point, input) || input[point.first][point.second] != ROLL) {
    return 0
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
    if (isOffgrid(p, input)) continue
    if (input[p.first][p.second] == ROLL && ++total >= THRESHOLD) return 0
  }

  return if (level == LEVEL_1) 1
  else {
    input[point.first][point.second] = EMPTY
    points.sumOf { score(it, input, level) } + 1
  }
}

private fun isOffgrid(
  point: Pair<Int, Int>,
  input: List<List<Char>>
) = point.first < 0
    || point.first > input.lastIndex
    || point.second < 0
    || point.second > input[point.first].lastIndex
