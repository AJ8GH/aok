package io.github.aj8gh.aok.y25.d3

fun part1(input: List<String>) = input.sumOf { maxJoltage(it) }

fun part2(input: List<String>) = 0

private fun maxJoltage(bank: String): Int {
  var max1 = '0'
  var max2 = '0'
  for (i in bank.indices) {
    if (i != bank.lastIndex && bank[i] > max1) {
      max1 = bank[i]
      max2 = '0'
    } else if (bank[i] > max2) {
      max2 = bank[i]
    }
  }
  return max1.digitToInt() * 10 + max2.digitToInt()
}
