package io.github.aj8gh.aok.y24.d1

import java.util.function.Function.identity
import java.util.stream.Collectors.counting
import java.util.stream.Collectors.groupingBy

private val re = Regex("\\s+")

fun part1(input: List<String>): Int {
  val nums = parse(input)
  val secondSorted = nums.second.sorted()
  return nums.first
    .sorted()
    .mapIndexed { i, n -> n - secondSorted[i] }
    .sumOf { n -> kotlin.math.abs(n) }
}

fun part2(input: List<String>): Int {
  val nums = parse(input)
  val freq = nums.second.stream()
    .collect(groupingBy(identity(), counting()))

  return nums.first.sumOf { n -> n * freq.getOrDefault(n, 0).toInt() }
}

private fun parse(input: List<String>): Pair<List<Int>, List<Int>> {
  val first = mutableListOf<Int>()
  val second = mutableListOf<Int>()

  for (line in input) {
    first.add(line.split(re)[0].toInt())
    second.add(line.split(re)[1].toInt())
  }
  return Pair(first, second)
}
