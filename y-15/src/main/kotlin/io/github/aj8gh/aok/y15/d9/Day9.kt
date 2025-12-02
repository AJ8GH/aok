package io.github.aj8gh.aok.y15.d9

fun part1(input: List<String>): Int {
  val parsed = input.groupBy { it.split(" ").let { spl -> Pair(spl[0], spl[2]) } }
    .mapValues { it.value.first().split(" ").last().toInt() }
  val byFirst = parsed.entries.groupBy { it.key.first }
  val bySecond = parsed.entries.groupBy { it.key.second }
  for (e in bySecond) {
  }

  return 0
}

fun part2(input: List<String>) = 0
