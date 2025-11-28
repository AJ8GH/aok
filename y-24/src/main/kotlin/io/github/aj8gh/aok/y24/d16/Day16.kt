package io.github.aj8gh.aok.y24.d16

import io.github.aj8gh.aok.y24.d16.Direction.EAST
import io.github.aj8gh.aok.y24.d16.Direction.NORTH
import io.github.aj8gh.aok.y24.d16.Direction.SOUTH
import io.github.aj8gh.aok.y24.d16.Direction.WEST
import kotlin.Int.Companion.MAX_VALUE

const val START = 'S'
const val END = 'E'
const val WALL = '#'

const val TURN_COST = 1000
const val MOVE_COST = 1

const val NO_TURN = 0
const val CLOCKWISE_TURN = 90
const val ONE_EIGHTY_TURN = 180
const val ANTI_CLOCKWISE_TURN = 270

enum class Direction(val i: Int) {
  EAST(0),
  SOUTH(1),
  WEST(2),
  NORTH(3);
}

val TURNS = listOf(NO_TURN, CLOCKWISE_TURN, ANTI_CLOCKWISE_TURN, ONE_EIGHTY_TURN)
val DIRS = Direction.entries

data class Route(
  val point: Pair<Int, Int>,
  val dir: Direction,
  val score: Int,
  var previous: StringBuilder = StringBuilder()
) {
  fun id() = "${point.first},${point.second}"
  fun pointDir() = Triple(point.first, point.second, dir)
}

fun part1(input: List<String>) = solve(input, 1)

fun part2(input: List<String>) = solve(input, 2)

private fun solve(input: List<String>, level: Int): Int {
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

  val routePoints = mutableMapOf<Int, StringBuilder>()
  val shortest = findShortestPath(input, start, end, routePoints)
  return when (level) {
    1 -> shortest
    2 -> routePoints[routePoints.keys.min()]!!
      .split(":").toSet()
      .filter { it.isNotEmpty() }.size

    else -> -1
  }
}

private fun findShortestPath(
  input: List<String>,
  start: Pair<Int, Int>,
  end: Pair<Int, Int>,
  shortestRoutePoints: MutableMap<Int, StringBuilder> = mutableMapOf(),
): Int {
  val visited = mutableMapOf<Triple<Int, Int, Direction>, Int>()
  val routes = ArrayDeque<Route>()
  routes.add(Route(start, EAST, 0))
  while (routes.isNotEmpty()) {
    val route = routes.removeFirst()
    if ((visited[route.pointDir()] ?: MAX_VALUE) >= route.score) {
      visited[route.pointDir()] = route.score
      route.previous.append(":${route.id()}")
      if (get(input, route) == END) {
        shortestRoutePoints.computeIfAbsent(route.score) { StringBuilder() }
          .append(":${route.previous}")
      }
      addPossibleRoutes(input, routes, route)
    }
  }
  return DIRS.minOf { visited[Triple(end.first, end.second, it)] ?: MAX_VALUE }
}

private fun addPossibleRoutes(
  input: List<String>,
  routes: ArrayDeque<Route>,
  route: Route,
) {
  if (get(input, route) != END) {
    TURNS.map { next(route, it, StringBuilder(route.previous)) }
      .filter { get(input, it) != WALL }
      .forEach { routes.add(it) }
  }
}

private fun next(
  route: Route,
  turn: Int,
  shortestRoutePoints: StringBuilder = StringBuilder(),
): Route {
  val turnScore = (if (turn == ANTI_CLOCKWISE_TURN) CLOCKWISE_TURN else turn) / CLOCKWISE_TURN
  val score = route.score + turnScore * TURN_COST + MOVE_COST
  val dir = DIRS[(turn / CLOCKWISE_TURN + route.dir.i) % DIRS.size]
  val point = when (dir) {
    EAST -> Pair(route.point.first, route.point.second + 1)
    SOUTH -> Pair(route.point.first + 1, route.point.second)
    WEST -> Pair(route.point.first, route.point.second - 1)
    NORTH -> Pair(route.point.first - 1, route.point.second)
  }
  return Route(point, dir, score, shortestRoutePoints)
}

private fun get(input: List<String>, route: Route) =
  input[route.point.first][route.point.second]
