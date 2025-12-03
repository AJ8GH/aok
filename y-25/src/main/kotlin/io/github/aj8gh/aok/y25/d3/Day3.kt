package io.github.aj8gh.aok.y25.d3

import kotlin.math.max
import kotlin.math.pow

fun part1(input: List<String>) = solve(input, 2)

fun part2(input: List<String>) = solve(input, 12)

private fun solve(input: List<String>, digits: Int) = input.sumOf { maxJoltage(it, digits) }

private fun maxJoltage(bank: String, digits: Int): Long {
  val joltages = CharArray(digits)
  for (i in bank.indices) {
    for (j in max(0, joltages.size - (bank.length - i))..joltages.lastIndex) {
      if (bank[i] > joltages[j]) {
        joltages[j] = bank[i]
        for (k in j + 1..joltages.lastIndex) {
          joltages[k] = '0'
        }
        break
      }
    }
  }
  return joltages
    .mapIndexed { i, ch -> ch.digitToInt() * 10.0.pow(joltages.lastIndex - i).toLong() }
    .sum()
}
