package io.github.aj8gh.aok.y24.d15

import io.github.oshai.kotlinlogging.KotlinLogging


const val ROBO_START = '@'
const val WALL = '#'
const val EMPTY = '.'
const val BOX = 'O'
const val LEFT_BOX = '['
const val RIGHT_BOX = ']'
const val UP = '^'
const val DOWN = 'v'
const val LEFT = '<'
const val RIGHT = '>'

val DIRS = setOf(UP, DOWN, LEFT, RIGHT)

fun part1(input: List<String>) = solve(input, 1)

fun part2(input: List<String>) = solve(input, 2)

private fun solve(input: List<String>, level: Int): Int {
  val grid = input
    .filter { it.startsWith(WALL) }
    .map {
      it.flatMap { c ->
        when {
          level == 1 -> listOf(c)
          c == ROBO_START -> listOf(ROBO_START, EMPTY)
          c == BOX -> listOf(LEFT_BOX, RIGHT_BOX)
          else -> listOf(c, c)
        }
      }
    }
    .map { it.toCharArray() }

  val directions = input
    .filter { it.isNotBlank() && DIRS.contains(it[0]) }
    .joinToString("")

  for (i in grid.indices) {
    for (j in grid[i].indices) {
      if (grid[i][j] == ROBO_START) {
        print(grid)
        move(grid, directions, Pair(i, j))
        print(grid)
        return gps(grid)
      }
    }
  }
  return -1
}

private fun move(
  grid: List<CharArray>,
  dirs: String,
  start: Pair<Int, Int>,
) {
  var current = start
  for (dir in dirs) {
    if (canMove(grid, dir, current)) {
      move(grid, dir, current)
      current = next(current, dir)
    }
  }
}

private fun canMove(
  grid: List<CharArray>,
  dir: Char,
  point: Pair<Int, Int>,
): Boolean {
  val next = next(point, dir)
  return when (val nextVal = grid[next.first][next.second]) {
    WALL -> false
    EMPTY -> true
    BOX -> canMove(grid, dir, next)
    LEFT_BOX, RIGHT_BOX -> {
      if (dir == UP || dir == DOWN) {
        val nextBoxDir = if (nextVal == LEFT_BOX) RIGHT else LEFT
        val nextBoxPoint = next(next, nextBoxDir)
        return canMove(grid, dir, nextBoxPoint) && canMove(grid, dir, next)
      }
      return canMove(grid, dir, next)
    }

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
    LEFT_BOX, RIGHT_BOX -> {
      if (dir == UP || dir == DOWN) {
        val nextBoxDir = if (nextVal == LEFT_BOX) RIGHT else LEFT
        val nextBoxPoint = next(next, nextBoxDir)
        move(grid, dir, nextBoxPoint)
      }
      move(grid, dir, next).also { swap(grid, dir, point) }
    }

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
      if (grid[i][j] == BOX || grid[i][j] == LEFT_BOX) {
        sum += i * 100 + j
      }
    }
  }
  return sum
}

private fun next(point: Pair<Int, Int>, dir: Char) =
  when (dir) {
    LEFT -> Pair(point.first, point.second - 1)
    RIGHT -> Pair(point.first, point.second + 1)
    UP -> Pair(point.first - 1, point.second)
    DOWN -> Pair(point.first + 1, point.second)
    else -> throw IllegalArgumentException("Bad dir $dir")
  }

private fun print(grid: List<CharArray>) = KotlinLogging
  .logger { }
  .info { "\n\n${grid.joinToString("\n") { it.joinToString("") }}\n\n" }
