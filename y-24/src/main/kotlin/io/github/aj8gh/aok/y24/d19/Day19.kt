package io.github.aj8gh.aok.y24.d19

fun part1(input: List<String>): Long {
  val rgx = Regex("^(${input.first().split(", ").joinToString("|")})+$")
  return input
    .slice(2..input.lastIndex)
    .filter { isPossible(rgx, it) }
    .size
    .toLong()
}

fun part2(input: List<String>): Long {
  val towels = input.first().split(", ")
  val rgx = Regex("^(${towels.joinToString("|")})+$")
  val cache = mutableMapOf<String, Long>()
  return input
    .slice(2..input.lastIndex)
    .filter { isPossible(rgx, it) }
    .sumOf { combos(towels.toSet(), it, cache) }
}

private fun isPossible(rgx: Regex, design: String) = rgx.matches(design)

private fun combos(
  towels: Set<String>,
  design: String,
  cache: MutableMap<String, Long>,
): Long {
  cache[design]?.let { return it }
  var total = 0L
  for (i in design.indices) {
    if (towels.contains(design.substring(0..i))) {
      if (i == design.lastIndex) {
        return ++total
      }
      total += combos(towels, design.substring(i + 1), cache)
    }
  }
  cache[design] = total
  return total
}
