package io.github.aj8gh.aok.y25.d7

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2

private const val START = 'S'
private const val SPLITTER = '^'

fun part1(input: List<String>) = solve(input, LEVEL_1)

fun part2(input: List<String>) = solve(input, LEVEL_2)

private fun solve(input: List<String>, level: Int): Long {
  val tl = input.first()
    .map { if (it == START) 1L else 0L }
    .toMutableList()

  var splits = 0L
  for (row in input.slice(1..input.lastIndex)) {
    for (i in tl.indices) {
      if (tl[i] != 0L && row[i] == SPLITTER) {
        splits++
        tl[i - 1] = tl[i - 1] + tl[i]
        tl[i + 1] = tl[i + 1] + tl[i]
        tl[i] = 0
      }
    }
  }

  return if (level == LEVEL_1) splits else tl.sum()
}
