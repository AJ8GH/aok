package io.github.aj8gh.aok.y25.d1

private const val KEY_NUMBER = 0
private const val START = 50
private const val MOD = 100
private const val R = 'R'
private const val L = 'L'

fun part1(input: List<String>): Int {
  var pw = 0;
  val parsed = input.map { Pair(it.first(), it.substring(1).toInt()) }
  var current = START

  for (p in parsed) {
    if (p.first == R) current += p.second
    if (p.first == L) current -= p.second
    current %= MOD
    if (current == KEY_NUMBER) pw++
  }

  return pw
}

fun part2(input: List<String>): Int {
  var pw = 0;
  val parsed = input.map { Pair(it.first(), it.substring(1).toInt()) }
  var current = START

  for (p in parsed) {
    val previous = current
    val remainder = p.second % MOD
    val multiplier = p.second / MOD
    pw += multiplier

    when (p.first) {
      R -> {
        current += remainder
        if (current > MOD) pw++
      }

      L -> {
        current -= remainder
        if (previous != KEY_NUMBER && current < KEY_NUMBER) {
          pw++
        }
      }
    }
    if (current >= MOD) current %= MOD
    if (current < KEY_NUMBER) current += MOD
    if (current == KEY_NUMBER) pw++
  }

  return pw
}
