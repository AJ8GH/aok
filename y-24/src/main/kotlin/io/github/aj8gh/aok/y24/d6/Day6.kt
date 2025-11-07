package io.github.aj8gh.aok.y24.d6

import java.util.function.BiFunction

private const val UP = '^'
private const val DOWN = 'v'
private const val LEFT = '<'
private const val RIGHT = '>'
private const val OBSTACLE = '#'
private const val LOOP_POINT = '0'

fun part1(input: List<String>) = solve(input, ::findPositions)

fun part2(input: List<String>) = solve(input, ::findLoops)

fun solve(
  input: List<String>,
  func: BiFunction<Pair<Int, Int>, List<CharArray>, Int>,
): Int {
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (input[i][j] == UP) {
        return func.apply(Pair(i, j), input.map { it.toCharArray() })
      }
    }
  }
  throw IllegalArgumentException("No starting position in input map")
}

private fun findPositions(
  startPosition: Pair<Int, Int>,
  input: List<CharArray>,
): Int {
  val positions = mutableSetOf<Pair<Int, Int>>()
  var position = startPosition
  var direction = UP

  while (!end(position, input, direction)) {
    val nextPosition = next(position, direction)
    val nextVal = input[nextPosition.first][nextPosition.second]
    positions.add(position)
    when (nextVal) {
      OBSTACLE -> direction = next(direction)
      else -> position = nextPosition
    }
  }

  return positions.also { it.add(position) }.size
}

private fun findLoops(
  startPosition: Pair<Int, Int>,
  input: List<CharArray>,
): Int {
  var loopPoints = 0
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (Pair(i, j) == startPosition || input[i][j] == OBSTACLE) {
        continue
      }
      val withObstacle = withObstacle(Pair(i, j), input)
      loopPoints += incrementIfLoop(withObstacle, startPosition)
    }
  }

  return loopPoints
}

private fun withObstacle(
  position: Pair<Int, Int>,
  input: List<CharArray>
): List<CharArray> {
  val out = input.map { it.copyOf() }
  out[position.first][position.second] = LOOP_POINT
  return out
}

private fun incrementIfLoop(
  input: List<CharArray>,
  startPosition: Pair<Int, Int>
): Int {
  val positions = mutableSetOf<Pair<Pair<Int, Int>, Char>>()
  var position = startPosition
  var direction = UP

  while (!end(position, input, direction)) {
    val nextPosition = next(position, direction)
    val nextVal = input[nextPosition.first][nextPosition.second]
    when {
      !positions.add(Pair(position, direction)) -> return 1
      nextVal == OBSTACLE || nextVal == LOOP_POINT -> direction = next(direction)
      else -> position = nextPosition
    }
  }
  return 0
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

private fun end(
  position: Pair<Int, Int>,
  input: List<CharArray>,
  direction: Char
) = when (direction) {
  UP -> position.first == 0
  RIGHT -> position.second == input[position.first].size - 1
  DOWN -> position.first == input.size - 1
  LEFT -> position.second == 0
  else -> throw IllegalArgumentException("Unknown direction $direction")
}
