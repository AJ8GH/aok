package io.github.aj8gh.aok.y20.d1

fun part1(input: List<String>): Int {
  val map = mutableMapOf<Int, Int>()
  for ((i, s) in input.withIndex()) {
    val n = s.toInt()
    if (map.containsKey(n)) {
      return n * input[map[n]!!].toInt()
    }
    map[2020 - n] = i
  }
  return -1
}

fun part2(input: List<String>) = 0
