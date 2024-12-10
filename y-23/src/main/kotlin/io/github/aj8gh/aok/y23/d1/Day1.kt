package io.github.aj8gh.aok.y23.d1

private val nums = mapOf(
  "one" to "1",
  "two" to "2",
  "three" to "3",
  "four" to "4",
  "five" to "5",
  "six" to "6",
  "seven" to "7",
  "eight" to "8",
  "nine" to "9",
)

private val regexes = listOf(
  Regex("\\D?(\\d).*"),
  Regex(".*(\\d)\\D?"),
)

private val num_regexes = listOf(
  Regex("\\D?(\\d|${nums.keys.joinToString("|")}).*"),
  Regex(".*(\\d|${nums.keys.joinToString("|")})\\D?"),
)

fun part1(input: List<String>): Int = solve(input, regexes)

fun part2(input: List<String>): Int = solve(input, num_regexes)

private fun solve(input: List<String>, regexes: List<Regex>) =
  input.map { line ->
    regexes.joinToString("") { regex ->
      val num = regex.find(line)!!.groupValues[1]
      nums.getOrDefault(num, num)
    }
  }.sumOf { s -> s.toInt() }
