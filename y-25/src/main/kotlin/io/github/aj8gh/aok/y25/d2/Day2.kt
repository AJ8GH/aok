package io.github.aj8gh.aok.y25.d2

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2

fun part1(input: List<String>) = solve(input, LEVEL_1)

fun part2(input: List<String>) = solve(input, LEVEL_2)

private fun solve(input: List<String>, level: Int) = input
  .flatMap { it.split(",") }
  .map { it.split("-") }
  .map { it.first().toLong()..it.last().toLong() }
  .sumOf { sumInvalid(it, level) }

private fun sumInvalid(range: LongRange, level: Int) = range
  .filter { if (level == LEVEL_1) isInvalid(it) else isInvalid2(it) }
  .sum()

private fun isInvalid(n: Long): Boolean {
  val s = n.toString()
  val a = s.substring(0..<s.length / 2)
  val b = s.substring(s.length / 2)
  return a == b
}

private fun isInvalid2(n: Long): Boolean {
  val s = n.toString()
  var chunkSize = s.length / 2
  while (chunkSize > 0) {
    if (s.length % chunkSize == 0) {
      val chunks = s.chunked(chunkSize)
      if (chunks.all { it == chunks.first() }) {
        return true
      }
    }
    chunkSize--
  }
  return false
}
