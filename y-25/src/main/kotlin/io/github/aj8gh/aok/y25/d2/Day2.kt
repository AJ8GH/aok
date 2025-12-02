package io.github.aj8gh.aok.y25.d2

fun part1(input: List<String>) = input
  .flatMap { it.split(",") }
  .map { it.split("-") }
  .map { it.first().toLong()..it.last().toLong() }
  .sumOf { sumInvalid(it) }

fun part2(input: List<String>) = 0L

private fun sumInvalid(range: LongRange) = range
  .filter { isInvalid(it) }
  .sum()

private fun isInvalid(n: Long): Boolean {
  val s = n.toString()
  val a = s.substring(0..<s.length / 2)
  val b = s.substring(s.length / 2)
  return a == b
}
