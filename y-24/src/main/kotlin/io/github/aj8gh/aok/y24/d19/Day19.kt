package io.github.aj8gh.aok.y24.d19

fun part1(input: List<String>): Int {
  val rgx = Regex("^(${input.first().split(", ").joinToString("|")})+$")
  val designs = input.slice(2..input.lastIndex)
  return designs.filter { isPossible(rgx, it) }.size
}

fun part2(input: List<String>) = 0

private fun isPossible(rgx: Regex, design: String) = rgx.matches(design)
