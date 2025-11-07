package io.github.aj8gh.aok.y24.d6

private const val UP = '^'
private const val DOWN = 'v'
private const val LEFT = '<'
private const val RIGHT = '>'
private const val OBSTACLE = '#'

fun part1(input: List<String>): Int {
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (input[i][j] == UP) {
        return solve(Pair(i, j), input).size
      }
    }
  }
  throw IllegalArgumentException("No starting position in input map")
}

fun part2(input: List<String>): Int {
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (input[i][j] == UP) {
        return findLoops(solve(Pair(i, j), input), input, Pair(i, j))
      }
    }
  }
  return 0
}

private fun solve(
  startPosition: Pair<Int, Int>,
  input: List<String>
): Map<Pair<Int, Int>, Set<Char>> {
  val positions = mutableMapOf(Pair(startPosition, mutableSetOf<Char>()))
  var position = startPosition
  var direction = UP

  while (!end(position, input, direction)) {
    val nextPosition = next(position, direction)
    val nextVal = input[nextPosition.first][nextPosition.second]
    when (nextVal) {
      OBSTACLE -> direction = next(direction)
      else -> {
        positions
          .computeIfAbsent(Pair(position.first, position.second)) { mutableSetOf() }
          .add(direction)
        position = next(position, direction)
      }
    }
  }

  return positions.also {
    it.computeIfAbsent(Pair(position.first, position.second)) { mutableSetOf(direction) }
      .add(direction)
  }
}

private fun findLoops(
  positions: Map<Pair<Int, Int>, Set<Char>>,
  input: List<String>,
  start: Pair<Int, Int>
): Int {
  val loopPoints = mutableSetOf<Pair<Int, Int>>()

  positions.forEach {
    if (it.value.size > 1) {
      if (it.value.contains(UP) && it.value.contains(LEFT)) loopPoints.add(Pair(it.key.first, it.key.second - 1))
      if (it.value.contains(UP) && it.value.contains(RIGHT)) loopPoints.add(Pair(it.key.first - 1, it.key.second))
      if (it.value.contains(DOWN) && it.value.contains(RIGHT)) loopPoints.add(Pair(it.key.first, it.key.second + 1))
      if (it.value.contains(DOWN) && it.value.contains(LEFT)) loopPoints.add(Pair(it.key.first + 1, it.key.second))
    }
    addIfLoopPoint(it, positions, loopPoints, input)
  }

  return loopPoints
    .filter { it.first >= 0 && it.first < input.size }
    .filter { it.second >= 0 && it.second < input[it.first].length }
    .filter { it.first != start.first && it.second != start.second }
    .size
}

private fun end(
  position: Pair<Int, Int>,
  input: List<String>,
  direction: Char
) = when (direction) {
  UP -> position.first == 0
  RIGHT -> position.second == input[position.first].length - 1
  DOWN -> position.first == input.size - 1
  LEFT -> position.second == 0
  else -> throw IllegalArgumentException("Unknown direction $direction")
}

private fun next(direction: Char) =
  when (direction) {
    UP -> RIGHT
    RIGHT -> DOWN
    DOWN -> LEFT
    LEFT -> UP
    else -> throw IllegalArgumentException("Unknown direction $direction")
  }

private fun next(position: Pair<Int, Int>, direction: Char) =
  when (direction) {
    UP -> Pair(position.first - 1, position.second)
    RIGHT -> Pair(position.first, position.second + 1)
    DOWN -> Pair(position.first + 1, position.second)
    LEFT -> Pair(position.first, position.second - 1)
    else -> throw IllegalArgumentException("Unknown direction $direction")
  }

private fun addIfLoopPoint(
  point: Map.Entry<Pair<Int, Int>, Set<Char>>,
  positions: Map<Pair<Int, Int>, Set<Char>>,
  loopPoints: MutableSet<Pair<Int, Int>>,
  input: List<String>,
) {
  val pointsByI = positions.entries.groupBy { it.key.first }
  val pointsByJ = positions.entries.groupBy { it.key.second }

  when {
    (point.key.first > 0 && point.value.contains(UP)) -> {
      if (
        pointsByI[point.key.first]!!
          .filter { it.value.contains(RIGHT) }
          .filter { it.key.second >= point.key.second }
          .filter { it != point }
          .any { isUnobstructed(point, it, RIGHT, input) }
      ) {
        loopPoints.add(Pair(point.key.first - 1, point.key.second))
      }
    }

    /*
    . 0 . . . .
    . ^ > > 0 .
    . ^ . v . .
    0 < < v . .
    . . . 0 . .
     */

    (point.key.second < input[point.key.first].length - 1 && point.value.contains(RIGHT)) -> {
      if (
        pointsByJ[point.key.second]!!
          .filter { it.value.contains(DOWN) }
          .filter { it.key.first >= point.key.first }
          .any { isUnobstructed(point, it, DOWN, input) }
      ) {
        val toAdd = Pair(point.key.first, point.key.second + 1)
        if (input[toAdd.first][toAdd.second] != OBSTACLE) {
          loopPoints.add(toAdd)
        }
      }
    }

    (point.key.first < input.size - 1 && point.value.contains(DOWN)) -> {
      if (
        pointsByI[point.key.first]!!
          .filter { it.value.contains(LEFT) }
          .filter { it.key.second <= point.key.second }
          .any { isUnobstructed(point, it, LEFT, input) }
      ) {
        loopPoints.add(Pair(point.key.first + 1, point.key.second))
      }
    }

    (point.key.second > 0 && point.value.contains(LEFT)) -> {
      if (
        pointsByJ[point.key.second]!!
          .filter { it.value.contains(UP) }
          .filter { it.key.first >= point.key.first }
          .any { isUnobstructed(point, it, UP, input) }
      ) {
        loopPoints.add(Pair(point.key.first + 1, point.key.second))
      }
    }
  }
}

private fun isUnobstructed(
  from: Map.Entry<Pair<Int, Int>, Set<Char>>,
  to: Map.Entry<Pair<Int, Int>, Set<Char>>,
  direction: Char,
  input: List<String>,
): Boolean {
  var next = next(from.key, direction)
  while (
    !(next.first == to.key.first && next.second == to.key.second)
    && next.first > 0
    && next.second > 0
    && next.first < input.size
    && next.second < input[next.first].length
  ) {
    if (input[next.first][next.second] == OBSTACLE) {
      return false
    }
    next = next(next, direction)
  }
  return true
}
