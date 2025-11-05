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
        return solve(i, j, input)
      }
    }
  }
  return 0
}

fun part2(input: List<String>) = 0

private fun solve(i: Int, j: Int, input: List<String>): Int {
  var position = Pair(i, j)
  val positions = mutableSetOf(position)
  var direction = UP

  while (!end(position, input, direction)) {
    val nextPosition = next(position, direction)
    val nextVal = input[nextPosition.first][nextPosition.second]
    when (nextVal) {
      OBSTACLE -> direction = next(direction)
      else -> {
        positions.add(Pair(position.first, position.second))
        position = next(position, direction)
      }
    }
  }

  return positions.also { it.add(position) }.size
}

private fun end(position: Pair<Int, Int>, input: List<String>, direction: Char) =
  when (direction) {
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
