package io.github.aj8gh.aok.y24.d20

import kotlin.Int.Companion.MAX_VALUE

const val WALL = '#'
const val UP = '^'
const val DOWN = 'v'
const val RIGHT = '>'
const val LEFT = '<'
const val START = 'S'
const val END = 'E'
val DIRS = listOf(UP, DOWN, RIGHT, LEFT)

data class Route(
  val point: Pair<Int, Int>,
  val dir: Char,
  val score: Int = 0,
) {
  fun pointDir() = Pair(point, dir)
}

fun part1(input: List<String>, minScore: Int): Int {
  var count = 0
  val grid = input.map { it.toMutableList() }
  val wallPoints = mutableSetOf<Pair<Int, Int>>()
  var start = Pair(-1, -1)
  var end = Pair(-1, -1)
  for (i in grid.indices) {
    for (j in grid[i].indices) {
      when (grid[i][j]) {
        WALL -> if (
          i > 0 &&
          j > 0 &&
          i < grid.lastIndex
          && j < grid[i].lastIndex
        ) {
          wallPoints.add(Pair(i, j))
        }

        END -> end = Pair(i, j)
        START -> start = Pair(i, j)
      }
    }
  }
  val originalScore = findShortestPath(start, end, grid)
  var prevPoint: Pair<Int, Int>? = null
  var prevLeft: Char? = null
  var prevRight: Char? = null
  val tried = mutableSetOf<Pair<Int, Int>>()

  for (i in 1..<grid.lastIndex) {
    for (j in 1..<grid[i].lastIndex - 1) {
      if (prevPoint != null) {
        grid[prevPoint.first][prevPoint.second] = prevLeft!!
        grid[prevPoint.first][prevPoint.second + 1] = prevRight!!
      }
      val point = Pair(i, j)
      if (
        (wallPoints.contains(point)
            || wallPoints.contains(Pair(i, j + 1)))
        && (grid[i][j] != WALL && tried.contains(Pair(i, j + 1))
            || (grid[i][j + 1] != WALL && tried.contains(Pair(i, j))))
      ) {
        tried.add(Pair(i, j))
        tried.add(Pair(i, j + 1))
        prevPoint = point
        prevLeft = grid[i][j]
        prevRight = grid[i][j + 1]
        grid[i][j] = '1'
        grid[i][j + 1] = '2'
      }
      val score = findShortestPath(start, end, grid)
      if (originalScore - score == minScore) {
        count++
      }
    }
  }

  grid[prevPoint!!.first][prevPoint.second] = prevLeft!!
  grid[prevPoint.first][prevPoint.second + 1] = prevRight!!

  for (i in 1..<grid.lastIndex - 1) {
    for (j in 1..<grid[i].lastIndex) {
      if (prevPoint != null) {
        grid[prevPoint.first][prevPoint.second] = prevLeft!!
        grid[prevPoint.first + 1][prevPoint.second] = prevRight!!
      }
      val point = Pair(i, j)
      if (
        (wallPoints.contains(point)
            || wallPoints.contains(Pair(i + 1, j)))
        && (grid[i][j] != WALL && tried.contains(Pair(i + 1, j))
            || (grid[i + 1][j] != WALL && tried.contains(Pair(i, j))))
      ) {
        tried.add(Pair(i, j))
        tried.add(Pair(i + 1, j))
        prevPoint = point
        prevLeft = grid[i][j]
        prevRight = grid[i + 1][j]
        grid[i][j] = '1'
        grid[i + 1][j] = '2'
      }
      val score = findShortestPath(start, end, grid)
      if (originalScore - score >= minScore) {
        count++
      }
    }
  }

  return count
}

fun part2(input: List<String>) = 0

private fun findShortestPath(
  start: Pair<Int, Int>,
  end: Pair<Int, Int>,
  grid: List<List<Char>>,
): Int {
  val visited = mutableMapOf<Pair<Pair<Int, Int>, Char>, Int>()
  val routes = ArrayDeque(listOf(Route(start, UP)))
  while (routes.isNotEmpty()) {
    val route = routes.removeFirst()
    if ((visited[route.pointDir()] ?: MAX_VALUE) > route.score) {
      visited[route.pointDir()] = route.score
      if (route.point != end) {
        addPossibleRoutes(route, routes, grid)
      }
    }
  }

  return visited
    .filter { it.key.first == end }
    .map { it.value }
    .min()
}

private fun addPossibleRoutes(
  route: Route,
  routes: ArrayDeque<Route>,
  grid: List<List<Char>>,
) {
  DIRS.forEach {
    val next = when {
      it == route.dir -> next(route)
      else -> Route(route.point, it, route.score)
    }
    if (isValid(next.point, grid)) {
      routes.add(next)
    }
  }
}

private fun next(route: Route) = when (route.dir) {
  UP -> Route(Pair(route.point.first - 1, route.point.second), route.dir, route.score + 1)
  DOWN -> Route(Pair(route.point.first + 1, route.point.second), route.dir, route.score + 1)
  RIGHT -> Route(Pair(route.point.first, route.point.second + 1), route.dir, route.score + 1)
  LEFT -> Route(Pair(route.point.first, route.point.second - 1), route.dir, route.score + 1)
  else -> throw IllegalArgumentException("Bad dir ${route.dir}")
}

private fun isValid(
  point: Pair<Int, Int>,
  grid: List<List<Char>>
) = point.first >= 0
    && point.second >= 0
    && point.first <= grid.lastIndex
    && point.second <= grid[point.first].lastIndex
    && grid[point.first][point.second] != WALL
