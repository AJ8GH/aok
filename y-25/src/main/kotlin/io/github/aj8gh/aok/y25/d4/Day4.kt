package io.github.aj8gh.aok.y25.d4

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2

private const val ROLL = '@'
private const val THRESHOLD = 4

fun part1(input: List<String>) = solve(input, LEVEL_1)

fun part2(input: List<String>) = solve(input, LEVEL_2)

private fun solve(input: List<String>, level: Int): Int {
  val grid = mutableMapOf<Pair<Int, Int>, Boolean>()
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (input[i][j] == ROLL) {
        grid[Pair(i, j)] = true
      }
    }
  }

  return grid.keys.sumOf { score(it, grid, level) }
}

private fun score(
  point: Pair<Int, Int>,
  grid: MutableMap<Pair<Int, Int>, Boolean>,
  level: Int,
): Int {
  if (!(grid[point] ?: false)) return 0

  val points = setOf(
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
    if (grid[p] ?: false && ++total >= THRESHOLD) return 0
  }

  return if (level == LEVEL_1) 1
  else {
    grid[point] = false
    points.sumOf { score(it, grid, level) } + 1
  }
}
