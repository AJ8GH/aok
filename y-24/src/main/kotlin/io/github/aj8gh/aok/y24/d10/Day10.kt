package io.github.aj8gh.aok.y24.d10

import java.util.function.BiFunction

private const val START = '0'
private const val END = '9'

fun part1(input: List<String>) = solve(input, ::score)

fun part2(input: List<String>) = solve(input, ::allPaths)

private fun solve(
  input: List<String>,
  func: BiFunction<List<String>, Pair<Int, Int>, Int>
): Int {
  var total = 0
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (input[i][j] == START) {
        total += func.apply(input, Pair(i, j))
      }
    }
  }
  return total
}

private fun score(
  input: List<String>,
  point: Pair<Int, Int>,
  found: MutableSet<Pair<Int, Int>> = mutableSetOf(),
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
  total: Int = 0,
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
