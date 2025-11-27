package io.github.aj8gh.aok.y24.d20

import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.abs

const val WALL = '#'
const val UP = '^'
const val DOWN = 'v'
const val RIGHT = '>'
const val LEFT = '<'
const val START = 'S'
const val END = 'E'
val DIRS = listOf(UP, DOWN, RIGHT, LEFT)

fun part1(input: List<String>, minSaving: Int): Int {
  val grid = input.map { it.toMutableList() }
  var start = Pair(-1, -1)
  var end = Pair(-1, -1)
  for (i in grid.indices) {
    for (j in grid[i].indices) {
      when (grid[i][j]) {
        END -> end = Pair(i, j)
        START -> start = Pair(i, j)
      }
    }
  }

  var count = 0
  val visited = mapTrack(start, end, grid)
  var current = start
  val savings = mutableMapOf<Int, Int>()

  while (current != end) {
    val cheatPoints = cheatPoints(current, grid)
    cheat@ for (p in cheatPoints) {
      val currentSteps = visited[current]!!
      val preCheatSteps = visited[p.second]!!
      if (preCheatSteps < currentSteps) {
        continue@cheat
      }
      val postCheatSteps = currentSteps + 2
      val saving = preCheatSteps - postCheatSteps
      savings[saving] = (savings[saving] ?: 0) + 1
      if (saving >= minSaving) {
        count++
      }
    }
    current = next(current, grid, visited)
  }

  return count
}

fun part2(input: List<String>, minSaving: Int): Int {
  val grid = input.map { it.toMutableList() }
  var start = Pair(-1, -1)
  var end = Pair(-1, -1)
  for (i in grid.indices) {
    for (j in grid[i].indices) {
      when (grid[i][j]) {
        END -> end = Pair(i, j)
        START -> start = Pair(i, j)
      }
    }
  }

  var count = 0
  val visited = mapTrack(start, end, grid)
  val savings = mutableMapOf<Int, Int>()

  /*
   (x1 - x2) + (y1 - y2)
  0,0 |    |
  ----+----+----
      |    |
  ----+----+----
  0,0 |    | 2,2

  0-2 + 0-2

         *
      *     *
   *           *
*        *        *
   *           *
      *     *
         *



   */


  for (e in visited) {
    val cheatPoints = cheatPoints(e.key, grid, 20)
    cheat@ for (p in cheatPoints) {
      val currentSteps = visited[e.key]!!
      val preCheatSteps = visited[p.second]!!
      if (preCheatSteps < currentSteps) {
        continue@cheat
      }
      val postCheatSteps = currentSteps + p.third
      val saving = preCheatSteps - postCheatSteps
      if (saving >= minSaving) {
        savings[saving] = (savings[saving] ?: 0) + 1
        count++
      }
    }
  }

  savings.entries.sortedBy { it.key }
    .forEach { println("There are ${it.value} cheats that save ${it.key} picoseconds.") }

  return count
}

private fun mapTrack(
  start: Pair<Int, Int>,
  end: Pair<Int, Int>,
  grid: List<List<Char>>,
): Map<Pair<Int, Int>, Int> {
  val visited = mutableMapOf(Pair(start, 0))
  var current = start
  var steps = 0

  while (current != end) {
    current = next(current, grid, visited)
    visited[current] = ++steps
  }

  return visited
}

private fun next(
  point: Pair<Int, Int>,
  grid: List<List<Char>>,
  visited: Map<Pair<Int, Int>, Int>
): Pair<Int, Int> {
  for (dir in DIRS) {
    val next = next(point, dir)
    if (grid[next.first][next.second] != WALL && (visited[next] ?: MAX_VALUE) > visited[point]!!) {
      return next
    }
  }
  throw IllegalArgumentException("No next path found for point $point")
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

private fun isEdge(point: Pair<Int, Int>, grid: List<List<Char>>) =
  point.first == 0
      || point.second == 0
      || point.first == grid.lastIndex
      || point.second == grid[point.first].lastIndex

private fun cheatPoints(
  point: Pair<Int, Int>,
  grid: List<List<Char>>,
  maxDistance: Int = 2,
): List<Triple<Pair<Int, Int>, Pair<Int, Int>, Int>> {
  val cheatPoints = mutableListOf<Triple<Pair<Int, Int>, Pair<Int, Int>, Int>>()

  for (i in 1..<grid.lastIndex) {
    for (j in 1..<grid[i].lastIndex) {
      val end = Pair(i, j)
      val md = manhattanDistance(point, Pair(i, j))
      if (
        point != end
        && grid[i][j] != WALL
        && md <= maxDistance
      ) {
        cheatPoints.add(Triple(point, Pair(i, j), md))
      }
    }
  }

//  for (dir in DIRS) {
//    val next = next(point, dir)
//    if (grid[next.first][next.second] != WALL || isEdge(next, grid)) {
//      continue
//    }
//
//    val nextNext = next(next, dir)
//    if (grid[nextNext.first][nextNext.second] == WALL) {
//      continue
//    }
//
//    cheatPoints.add(Pair(next, nextNext))
//  }
  return cheatPoints
}

private fun manhattanDistance(a: Pair<Int, Int>, b: Pair<Int, Int>) =
  abs(a.first - b.first) + abs(a.second - b.second)
