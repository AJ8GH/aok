package io.github.aj8gh.aok.y24.d18

import kotlin.Int.Companion.MAX_VALUE

data class Route(
  var point: Pair<Int, Int>,
  var direction: Char,
  var score: Int = 0,
) {
  fun pointDir() = Triple(point.first, point.second, direction)
}

const val UP = '^'
const val DOWN = 'v'
const val RIGHT = '>'
const val LEFT = '<'
const val CORRUPT = '#'
const val EMPTY = "."

val DIRECTIONS = listOf(UP, DOWN, RIGHT, LEFT)
val DIGITS = Regex("\\d+")

fun part1(input: List<String>, bytes: Int) = parse(input, bytes)
  .let { findShortestPath(it.second, it.third) }

fun part2(input: List<String>, bytes: Int): String {
  val parsed = parse(input, bytes)
  val corrupted = parsed.first
  val grid = parsed.second
  val max = parsed.third
  for (i in (bytes + 1)..corrupted.lastIndex) {
    val corrupt = corrupted[i]
    grid[corrupt.first][corrupt.second] = CORRUPT
    val result = findShortestPath(grid, max)
    if (result == -1) {
      return "${corrupt.second},${corrupt.first}"
    }
  }
  return "-1,-1"
}

private fun parse(
  input: List<String>,
  bytes: Int
): Triple<List<Pair<Int, Int>>, List<CharArray>, Pair<Int, Int>> {
  val corrupted = input
    .map { DIGITS.findAll(it).map { r -> r.value.toInt() }.toList() }
    .map { Pair(it[1], it.first()) }

  val maxY = corrupted.maxBy { it.first }.first
  val maxX = corrupted.maxBy { it.second }.second
  val grid = mutableListOf<CharArray>()
  repeat(maxY + 1) { grid.add(EMPTY.repeat(maxX + 1).toCharArray()) }
  for (i in 0..<bytes) {
    val p = corrupted[i]
    grid[p.first][p.second] = CORRUPT
  }
  return Triple(corrupted, grid, Pair(maxX, maxY))
}

private fun findShortestPath(grid: List<CharArray>, max: Pair<Int, Int>): Int {
  val visited = mutableMapOf<Triple<Int, Int, Char>, Int>()
  val routes = ArrayDeque(listOf(Route(Pair(0, 0), DOWN)))

  while (routes.isNotEmpty()) {
    val route = routes.removeFirst()
    if ((visited[route.pointDir()] ?: MAX_VALUE) > route.score) {
      visited[route.pointDir()] = route.score
      if (route.point != max) {
        addPossibleRoutes(grid, routes, route)
      }
    }
  }

  return visited.entries
    .filter { Pair(it.key.first, it.key.second) == max }
    .minOfOrNull { it.value } ?: -1
}

private fun addPossibleRoutes(
  grid: List<CharArray>,
  routes: ArrayDeque<Route>,
  route: Route,
) {
  for (dir in DIRECTIONS) {
    if (route.direction == dir) {
      continue
    }
    routes.add(Route(route.point, dir, route.score))
  }
  val nextPoint = next(route.point, route.direction)
  if (isValid(nextPoint, grid)) {
    routes.add(Route(nextPoint, route.direction, route.score + 1))
  }
}

private fun next(point: Pair<Int, Int>, direction: Char) = when (direction) {
  UP -> Pair(point.first - 1, point.second)
  DOWN -> Pair(point.first + 1, point.second)
  RIGHT -> Pair(point.first, point.second + 1)
  LEFT -> Pair(point.first, point.second - 1)
  else -> throw IllegalArgumentException("Bad direction $direction")
}

private fun isValid(point: Pair<Int, Int>, grid: List<CharArray>) =
  point.first >= 0
      && point.second >= 0
      && point.first <= grid.lastIndex
      && point.second <= grid[point.first].lastIndex
      && grid[point.first][point.second] != CORRUPT
