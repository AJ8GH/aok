package io.github.aj8gh.aok.y25.d4

private const val ROLL = '@'
private const val EMPTY = '.'
private const val THRESHOLD = 4

fun part1(input: List<String>): Int {
  var total = 0
  val grid = input.map { it.toMutableList() }
  for (i in grid.indices) {
    for (j in grid[i].indices) {
      if (isAccessibleRoll(Pair(i, j), grid)) total++
    }
  }
  return total
}

fun part2(input: List<String>): Int {
  var total = 0
  val grid = input.map { it.toMutableList() }
  var removed = 0
  while (true) {
    for (i in grid.indices) {
      for (j in grid[i].indices) {
        if (isAccessibleRoll(Pair(i, j), grid)) {
          grid[i][j] = EMPTY
          total++
          removed++
        }
      }
    }
    if (removed == 0) {
      break
    }
    removed = 0
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
