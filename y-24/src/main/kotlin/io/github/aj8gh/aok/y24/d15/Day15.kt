package io.github.aj8gh.aok.y24.d15

import io.github.oshai.kotlinlogging.KotlinLogging


const val ROBO_START = '@'
const val WALL = '#'
const val EMPTY = '.'
const val BOX = 'O'

val DIRS = setOf('>', '<', '^', 'v')

fun part1(input: List<String>): Int {
  val grid = input
    .filter { it.startsWith(WALL) }
    .map { it.toCharArray() }

  val directions = input
    .filter { it.isNotBlank() && DIRS.contains(it[0]) }
    .joinToString("")

  for (i in grid.indices) {
    for (j in grid[i].indices) {
      if (grid[i][j] == ROBO_START) {
        move(grid, directions, Pair(i, j))
        print(grid)
        return gps(grid)
      }
    }
  }
  return -1
}

fun part2(input: List<String>) = 0

private fun move(
  grid: List<CharArray>,
  dirs: String,
  start: Pair<Int, Int>,
) {
  var current = start
  for (dir in dirs) {
    if (canMove(current, dir, grid)) {
      move(grid, dir, current)
      current = next(current, dir)
    }
  }
}

private fun canMove(
  point: Pair<Int, Int>,
  dir: Char,
  grid: List<CharArray>,
): Boolean {
  val next = next(point, dir)
  return when (val nextVal = grid[next.first][next.second]) {
    WALL -> false
    EMPTY -> true
    BOX -> canMove(next, dir, grid)
    else -> throw IllegalArgumentException("Bad val $nextVal")
  }
}

private fun move(
  grid: List<CharArray>,
  dir: Char,
  point: Pair<Int, Int>,
) {
  val next = next(point, dir)
  when (val nextVal = grid[next.first][next.second]) {
    EMPTY -> swap(grid, dir, point)
    BOX -> move(grid, dir, next).also { swap(grid, dir, point) }
    else -> throw IllegalArgumentException("Bad val $nextVal")
  }
}

private fun swap(
  grid: List<CharArray>,
  dir: Char,
  point: Pair<Int, Int>,
) {
  val next = next(point, dir)
  val nextVal = grid[next.first][next.second]
  grid[next.first][next.second] = grid[point.first][point.second]
  grid[point.first][point.second] = nextVal
}

private fun gps(grid: List<CharArray>): Int {
  var sum = 0
  for (i in grid.indices) {
    for (j in grid[i].indices) {
      if (grid[i][j] == BOX) {
        sum += i * 100 + j
      }
    }
  }
  return sum
}

private fun next(point: Pair<Int, Int>, dir: Char) =
  when (dir) {
    '<' -> Pair(point.first, point.second - 1)
    '>' -> Pair(point.first, point.second + 1)
    '^' -> Pair(point.first - 1, point.second)
    'v' -> Pair(point.first + 1, point.second)
    else -> throw IllegalArgumentException("Bad dir $dir")
  }

private fun print(grid: List<CharArray>) = KotlinLogging
  .logger { }
  .info { "\n\n${grid.joinToString("\n") { it.joinToString("") }}\n\n" }
