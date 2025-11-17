package io.github.aj8gh.aok.y24.d13

import kotlin.math.floor

val digits = Regex("\\d+")

fun part1(input: List<String>) = solve(input, 0L)

fun part2(input: List<String>) = solve(input, 10_000_000_000_000L)

private fun solve(input: List<String>, offset: Long = 0L) = input
  .map { digits.findAll(it).map { r -> r.value.toDouble() }.toList() }
  .filter { it.isNotEmpty() }
  .map { Pair(it[0], it[1]) }
  .chunked(3)
  .map { score(it, offset) }
  .filter { it > 0 }
  .sum()

private fun score(e: List<Pair<Double, Double>>, offset: Long): Long {
  val a = ((e[2].first + offset) * e[1].second - (e[2].second + offset) * e[1].first) /
      (e[0].first * e[1].second - e[0].second * e[1].first)

  val b = ((e[2].second + offset) * e[0].first - (e[2].first + offset) * e[0].second) /
      (e[0].first * e[1].second - e[0].second * e[1].first)

  return if (floor(a) != a || floor(b) != b) -1L else (3 * a + b).toLong()
}
