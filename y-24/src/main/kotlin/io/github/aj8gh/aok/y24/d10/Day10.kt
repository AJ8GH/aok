package io.github.aj8gh.aok.y24.d10

const val START = '0'
const val END = '9'
lateinit var input: List<String>

fun part1(input: List<String>) = solve(input, 1)

fun part2(input: List<String>) = solve(input, 2)

private fun solve(
  input: List<String>,
  level: Int,
): Int {
  var total = 0
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (input[i][j] == START) {
        total += when (level) {
          1 -> score(input, Pair(i, j), mutableSetOf())
          2 -> allPaths(input, Pair(i, j), 0)
          else -> throw IllegalArgumentException("Bad level $level")
        }
      }
    }
  }
  return total
}

private fun score(
  input: List<String>,
  point: Pair<Int, Int>,
  found: MutableSet<Pair<Int, Int>>,
): Int {
  validNeighbours(point, input).forEach {
    val newVal = input[it.first][it.second]
    if (isNext(input[point.first][point.second], newVal)) {
      if (newVal == END) found.add(it)
      score(input, it, found)
    }
  }
  return found.size
}

private fun allPaths(
  input: List<String>,
  point: Pair<Int, Int>,
  total: Int,
): Int {
  var out = total
  for (newPoint in validNeighbours(point, input)) {
    val newVal = input[newPoint.first][newPoint.second]
    if (isNext(input[point.first][point.second], newVal)) {
      if (newVal == END) {
        out++
      } else {
        out = allPaths(input, newPoint, out)
      }
    }
  }
  return out
}

private fun validNeighbours(point: Pair<Int, Int>, input: List<String>) =
  listOf(
    Pair(point.first + 1, point.second),
    Pair(point.first - 1, point.second),
    Pair(point.first, point.second + 1),
    Pair(point.first, point.second - 1),
  ).filter {
    it.first >= 0
        && it.second >= 0
        && it.first <= input.lastIndex
        && it.second <= input[it.first].lastIndex
  }

private fun isNext(old: Char, new: Char) = old + 1 == new
