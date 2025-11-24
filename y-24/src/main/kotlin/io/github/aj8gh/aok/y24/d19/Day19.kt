package io.github.aj8gh.aok.y24.d19

fun part1(input: List<String>): Int {
  val rgx = Regex("^(${input.first().split(", ").joinToString("|")})+$")
  return input
    .slice(2..input.lastIndex)
    .filter { isPossible(rgx, it) }.size
}

fun part2(input: List<String>): Int {
  val towels = input.first().split(", ")
  val rgx = Regex("^(${towels.joinToString("|")})+$")
  return input
    .slice(2..input.lastIndex)
    .filter { isPossible(rgx, it) }
    .sumOf { combos(towels, it) }
}

private fun isPossible(rgx: Regex, design: String) = rgx.matches(design)

private fun combos(towels: List<String>, design: String): Int {

  return 0
}
