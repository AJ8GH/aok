package io.github.aj8gh.aok.y24.d20

import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val WALL = '#'
private const val UP = '^'
private const val DOWN = 'v'
private const val RIGHT = '>'
private const val LEFT = '<'
private const val START = 'S'
private const val END = 'E'

private val DIRS = listOf(UP, DOWN, RIGHT, LEFT)

fun part1(input: List<String>, minSaving: Int) = solve(input, minSaving, 2)

fun part2(input: List<String>, minSaving: Int) = solve(input, minSaving, 20)

private fun solve(input: List<String>, minSaving: Int, maxDistance: Int): Int {
  val start = input.indices
    .flatMap { i -> input[i].indices.map { j -> Pair(i, j) } }
    .first { input[it.first][it.second] == START }

  var count = 0
  val visited = mapTrack(start, input)
  for (e in visited) {
    val cheatPoints = cheatPoints(e.key, input, maxDistance)
    cheat@ for (p in cheatPoints) {
      val currentSteps = visited[e.key]!!
      val preCheatSteps = visited[p.second]!!
      if (preCheatSteps < currentSteps) {
        continue@cheat
      }
      val postCheatSteps = currentSteps + p.third
      val saving = preCheatSteps - postCheatSteps
      if (saving >= minSaving) {
        count++
      }
    }
  }
  return count
}

private fun mapTrack(
  start: Pair<Int, Int>,
  input: List<String>,
): Map<Pair<Int, Int>, Int> {
  val visited = mutableMapOf(Pair(start, 0))
  var current = start
  var steps = 0
  while (input[current.first][current.second] != END) {
    current = next(current, input, visited)
    visited[current] = ++steps
  }
  return visited
}

private fun next(
  a: Pair<Int, Int>,
  input: List<String>,
  visited: Map<Pair<Int, Int>, Int>
): Pair<Int, Int> {
  for (dir in DIRS) {
    val b = next(a, dir)
    if (input[b.first][b.second] != WALL && (visited[b] ?: MAX_VALUE) > visited[a]!!) {
      return b
    }
  }
  throw IllegalArgumentException("No next path found for point $a")
}

private fun next(
  point: Pair<Int, Int>,
  dir: Char,
) = when (dir) {
  UP -> Pair(point.first - 1, point.second)
  DOWN -> Pair(point.first + 1, point.second)
  RIGHT -> Pair(point.first, point.second + 1)
  LEFT -> Pair(point.first, point.second - 1)
  else -> throw IllegalArgumentException("Bad dir $dir")
}

private fun cheatPoints(
  a: Pair<Int, Int>,
  input: List<String>,
  dist: Int,
): List<Triple<Pair<Int, Int>, Pair<Int, Int>, Int>> {
  val cheatPoints = mutableListOf<Triple<Pair<Int, Int>, Pair<Int, Int>, Int>>()
  for (i in max(1, a.first - dist)..<min(input.lastIndex, a.first + dist + 1)) {
    for (j in max(1, a.second - dist)..<min(input[i].lastIndex, a.second + dist + 1)) {
      val end = Pair(i, j)
      val md = manhattanDistance(a, Pair(i, j))
      if (a != end && input[i][j] != WALL && md <= dist) {
        cheatPoints.add(Triple(a, Pair(i, j), md))
      }
    }
  }
  return cheatPoints
}

private fun manhattanDistance(a: Pair<Int, Int>, b: Pair<Int, Int>) =
  abs(a.first - b.first) + abs(a.second - b.second)
