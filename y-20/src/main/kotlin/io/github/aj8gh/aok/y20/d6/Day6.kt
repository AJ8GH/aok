package io.github.aj8gh.aok.y20.d6

fun part1(input: List<String>): Int {
  var total = 0
  val group = mutableSetOf<Char>()
  for ((i, line) in input.withIndex()) {
    for (char in line.trim()) {
      group.add(char)
    }
    if (i == input.lastIndex || line.isEmpty()) {
      total += group.count()
      group.clear()
      continue
    }
  }
  return total
}

fun part2(input: List<String>) = 0
