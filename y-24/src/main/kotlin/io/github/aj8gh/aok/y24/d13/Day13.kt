package io.github.aj8gh.aok.y24.d13

import kotlin.math.floor

val digits = Regex("\\d+")

fun part1(input: List<String>): Long {
  return input
    .map { digits.findAll(it).map { r -> r.value.toDouble() }.toList() }
    .filter { it.isNotEmpty() }
    .map { Pair(it[0], it[1]) }
    .chunked(3)
    .map { score(it) }
    .filter { it > 0 }
    .sum()
}

fun part2(input: List<String>) = 0L

private fun score(e: List<Pair<Double, Double>>): Long {
  val a = (e[2].first * e[1].second - e[2].second * e[1].first) /
      (e[0].first * e[1].second - e[0].second * e[1].first)

  val b = (e[2].second * e[0].first - e[2].first * e[0].second) /
      (e[0].first * e[1].second - e[0].second * e[1].first)

  if (a < 0 || b < 0 || floor(a) != a || floor(b) != b) {
    return -1L
  }

  return (3 * a + b).toLong()
}
