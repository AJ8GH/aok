package io.github.aj8gh.aok.y25.d1

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2

private const val KEY_NUMBER = 0
private const val START = 50
private const val MOD = 100
private const val R = 'R'
private const val L = 'L'

fun part1(input: List<String>) = solve(input, LEVEL_1)

fun part2(input: List<String>) = solve(input, LEVEL_2)

private fun solve(input: List<String>, level: Int): Int {
  var pw = 0
  val parsed = input.map { Pair(it.first(), it.substring(1).toInt()) }
  var current = START

  for (p in parsed) {
    val previous = current
    (p.second % MOD).let {
      when (p.first) {
        R -> current += it
        L -> current -= it
      }
    }

    if (level == LEVEL_2) {
      pw += p.second / MOD
      if (current > MOD) pw++
      if (previous != KEY_NUMBER && current < KEY_NUMBER) pw++
    }

    if (current >= MOD) current %= MOD
    if (current < KEY_NUMBER) current += MOD
    if (current == KEY_NUMBER) pw++
  }

  return pw
}
