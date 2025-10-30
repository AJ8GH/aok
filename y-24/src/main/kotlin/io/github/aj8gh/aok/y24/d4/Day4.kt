package io.github.aj8gh.aok.y24.d4

import java.util.function.BiFunction

const val X = 'X'
const val M = 'M'
const val A = 'A'
const val S = 'S'

val directions = listOf("N", "S", "E", "W", "NE", "NW", "SE", "SW")

fun part1(input: List<String>) = solve(input, X, ::findXmas)

fun part2(input: List<String>) = solve(input, A, ::findXShapedMas)

private fun solve(
  input: List<String>,
  trigger: Char,
  func: BiFunction<List<String>, Pair<Int, Int>, Int>
): Int {
  var count = 0
  for (i in input.indices) {
    val row = input[i]
    for (j in row.indices) {
      val c = row[j]
      if (c == trigger) {
        count += func.apply(input, Pair(i, j))
      }
    }
  }
  return count
}

private fun findXmas(input: List<String>, p: Pair<Int, Int>) =
  directions.sumOf { findXmas(X, input, p, it) }

private fun findXmas(c: Char, input: List<String>, p: Pair<Int, Int>, dir: String): Int {
  val p = toPair(dir, p)
  if (!isValid(p, input)) {
    return 0
  }
  val newC = input[p.first][p.second]
  return when {
    isNext(c, newC) && newC == S -> 1
    isNext(c, newC) -> findXmas(newC, input, p, dir)
    else -> 0
  }
}

private fun findXShapedMas(input: List<String>, p: Pair<Int, Int>) = if (
  isX(toPair("NE", p), toPair("SW", p), input)
  && isX(toPair("NW", p), toPair("SE", p), input)
) 1 else 0

private fun isX(p1: Pair<Int, Int>, p2: Pair<Int, Int>, input: List<String>): Boolean {
  if (!isValid(p1, input) || !isValid(p2, input)) {
    return false
  }
  val c1 = input[p1.first][p1.second]
  val c2 = input[p2.first][p2.second]
  return (c1 == M && c2 == S) || (c1 == S && c2 == M)
}


private fun toPair(direction: String, p: Pair<Int, Int>) = when (direction) {
  "N" -> Pair(p.first - 1, p.second)
  "S" -> Pair(p.first + 1, p.second)
  "E" -> Pair(p.first, p.second + 1)
  "W" -> Pair(p.first, p.second - 1)
  "NE" -> Pair(p.first - 1, p.second + 1)
  "NW" -> Pair(p.first - 1, p.second - 1)
  "SE" -> Pair(p.first + 1, p.second + 1)
  "SW" -> Pair(p.first + 1, p.second - 1)
  else -> throw IllegalArgumentException("Bad direction")
}

private fun isNext(c: Char, newC: Char) = when (c) {
  X -> newC == M
  M -> newC == A
  A -> newC == S
  else -> false
}

private fun isValid(p: Pair<Int, Int>, input: List<String>) =
  p.first >= 0
      && p.first < input.size
      && p.second >= 0
      && p.second < input[p.first].length
