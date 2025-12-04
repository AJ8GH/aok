package io.github.aj8gh.aok.y25.d4

private const val ROLL = '@'
private const val THRESHOLD = 4

fun part1(input: List<String>): Int {
  var total = 0
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (isAccessibleRoll(Pair(i, j), input)) total++
    }
  }
  return total
}

fun part2(input: List<String>) = 0

private fun isAccessibleRoll(point: Pair<Int, Int>, input: List<String>): Boolean {
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
