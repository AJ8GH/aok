package io.github.aj8gh.aok.y24.d19

fun part1(input: List<String>) = possibleDesigns(parse(input)).size.toLong()

fun part2(input: List<String>): Long {
  val cache = mutableMapOf<String, Long>()
  val parsed = parse(input)
  val towels = parsed.first.toSet()
  return parsed.second.sumOf { combos(towels, it, cache) }
}

private fun parse(input: List<String>) = Pair(
  input.first().split(", "),
  input.slice(2..input.lastIndex),
)

private fun possibleDesigns(input: Pair<List<String>, List<String>>) = input.second
  .filter { Regex("^(${input.first.joinToString("|")})+$").matches(it) }

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
