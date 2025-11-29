package io.github.aj8gh.aok.y24.d8

import java.util.function.BiFunction

private const val EMPTY = '.'

fun part1(input: List<String>) = solve(input, ::getAntinodes)

fun part2(input: List<String>) = solve(input, ::getAllAntinodes)

private fun solve(
  input: List<String>,
  func: BiFunction<Pair<Pair<Int, Int>, Pair<Int, Int>>, List<String>, Collection<Pair<Int, Int>>>,
): Int {
  val nodes = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
  for (i in input.indices) {
    for (j in input[i].indices) {
      val c = input[i][j]
      if (c != EMPTY) {
        nodes.computeIfAbsent(c) { mutableListOf() }.add(Pair(i, j))
      }
    }
  }

  val antinodes = mutableSetOf<Pair<Int, Int>>()
  for (e in nodes.entries) {
    for (i in e.value.indices) {
      for (j in i + 1..<e.value.size) {
        antinodes.addAll(func.apply(Pair(e.value[i], e.value[j]), input))
      }
    }
  }
  return antinodes.size
}

private fun getAllAntinodes(
  locs: Pair<Pair<Int, Int>, Pair<Int, Int>>,
  input: List<String>,
): Collection<Pair<Int, Int>> {
  val xDiff = locs.first.second - locs.second.second
  val yDiff = locs.first.first - locs.second.first
  val antinodes = mutableSetOf(locs.first, locs.second)
  var current = locs.first

  while (isValid(current, input)) {
    current = Pair(current.first - yDiff, current.second - xDiff)
    antinodes.add(current)
  }

  current = locs.first
  while (isValid(current, input)) {
    current = Pair(current.first + yDiff, current.second + xDiff)
    antinodes.add(current)
  }

  return antinodes.filter { isValid(it, input) }
}

private fun getAntinodes(
  locs: Pair<Pair<Int, Int>, Pair<Int, Int>>,
  input: List<String>,
): Set<Pair<Int, Int>> {
  val xDiff = (locs.first.second - locs.second.second)
  val yDiff = (locs.first.first - locs.second.first)

  return setOf(
    Pair(locs.first.first + yDiff, locs.first.second + xDiff),
    Pair(locs.second.first - yDiff, locs.second.second - xDiff),
  ).filter { isValid(it, input) }
    .toSet()
}

private fun isValid(loc: Pair<Int, Int>, input: List<String>) =
  loc.first >= 0
      && loc.first < input.size
      && loc.second >= 0
      && loc.second < input[loc.first].length
