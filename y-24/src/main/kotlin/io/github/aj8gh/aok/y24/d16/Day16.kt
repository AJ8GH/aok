package io.github.aj8gh.aok.y24.d16

import kotlin.Int.Companion.MAX_VALUE

/*
  keep map of position visited and score
  bfs of each possible path
  abandon path if position is visited and saved score is lower than current
  if current score is lower, update in visited map
  return score for end position in map
*/

const val START = 'S'
const val END = 'E'
const val WALL = '#'

const val TURN_COST = 1000
const val MOVE_COST = 1

const val NO_TURN = 0
const val CLOCKWISE_TURN = 90
const val ONE_EIGHTY_TURN = 180
const val ANTI_CLOCKWISE_TURN = 270

const val EAST = '>'
const val SOUTH = 'v'
const val WEST = '<'
const val NORTH = '^'

val TURNS = listOf(NO_TURN, CLOCKWISE_TURN, ANTI_CLOCKWISE_TURN, ONE_EIGHTY_TURN)
val DIRS = listOf(EAST, SOUTH, WEST, NORTH)

data class Route(
  val point: Pair<Int, Int>,
  val dir: Char,
  val score: Int,
)

fun part1(input: List<String>): Int {
  var start = Pair(-1, -1)
  var end = Pair(-1, -1)
  for (i in input.indices) {
    for (j in input[i].indices) {
      when (input[i][j]) {
        START -> start = Pair(i, j)
        END -> end = Pair(i, j)
      }
    }
  }
  return findShortestPath(input, start, end)
}

fun part2(input: List<String>) = 0

private fun findShortestPath(
  input: List<String>,
  start: Pair<Int, Int>,
  end: Pair<Int, Int>,
): Int {
  val visited = mutableMapOf<Pair<Int, Int>, Int>()
  val routes = ArrayDeque<Route>()
  routes.add(Route(start, EAST, 0))
  while (routes.isNotEmpty()) {
    val route = routes.removeLast()
    if ((visited[route.point] ?: MAX_VALUE) > route.score) {
      visited[route.point] = route.score
      addPossibleRoutes(input, routes, route)
    }
  }
  return visited[end]!!
}

private fun addPossibleRoutes(
  input: List<String>,
  routes: ArrayDeque<Route>,
  route: Route,
) {
  if (get(input, route) != END) {
    TURNS.map { next(route, it) }
      .filter { get(input, it) != WALL }
      .forEach { routes.add(it) }
  }
}

private fun next(route: Route, turn: Int): Route {
  val turnScore = (if (turn == ANTI_CLOCKWISE_TURN) CLOCKWISE_TURN else turn) / CLOCKWISE_TURN
  val score = route.score + turnScore * TURN_COST + MOVE_COST
  val dir = DIRS[(turn / CLOCKWISE_TURN + DIRS.indexOf(route.dir)) % DIRS.size]
  val point = when (dir) {
    EAST -> Pair(route.point.first, route.point.second + 1)
    SOUTH -> Pair(route.point.first + 1, route.point.second)
    WEST -> Pair(route.point.first, route.point.second - 1)
    NORTH -> Pair(route.point.first - 1, route.point.second)
    else -> throw IllegalArgumentException("Bad dir $dir")
  }
  return Route(point, dir, score)
}

private fun get(input: List<String>, route: Route) =
  input[route.point.first][route.point.second]
