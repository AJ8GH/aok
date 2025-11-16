package io.github.aj8gh.aok.y24.d12

fun part1(input: List<String>) = solve(input, 1)

fun part2(input: List<String>) = solve(input, 2)

private fun solve(input: List<String>, level: Int): Int {
  var cost = 0
  val mapped = mutableSetOf<Pair<Int, Int>>()
  val mappedEdges = mutableSetOf<Pair<Pair<Int, Int>, Char>>()
  for (i in input.indices) {
    for (j in input[i].indices) {
      val result =
        if (level == 1) map(input, Pair(i, j), level, mapped)
        else map(input, Pair(i, j), level, mapped, mappedEdges)
      cost += result.first * result.second
    }
  }
  return cost
}

private fun map(
  input: List<String>,
  point: Pair<Int, Int>,
  level: Int,
  mapped: MutableSet<Pair<Int, Int>>,
  mappedEdges: MutableSet<Pair<Pair<Int, Int>, Char>> = mutableSetOf(),
  cost: Pair<Int, Int> = Pair(0, 0),
): Pair<Int, Int> {
  var area = cost.first
  var perim = cost.second
  val type = input[point.first][point.second]
  val neighbours = validNeighbours(point, input)
  for (n in neighbours) {
    if (mapped.contains(n) || input[n.first][n.second] != type) {
      continue
    }
    area++
    mapped.add(n)
    perim += if (level == 1) perimeter(input, n) else edges(input, n, mappedEdges)
    val newCost = map(input, n, level, mapped, mappedEdges, Pair(area, perim))
    area = newCost.first
    perim = newCost.second
  }
  return Pair(area, perim)
}

private fun validNeighbours(point: Pair<Int, Int>, input: List<String>) =
  listOf(
    point,
    Pair(point.first + 1, point.second),
    Pair(point.first - 1, point.second),
    Pair(point.first, point.second + 1),
    Pair(point.first, point.second - 1),
  ).filter { isValid(it, input) }

private fun perimeter(input: List<String>, point: Pair<Int, Int>): Int {
  val validNeighbours = validNeighbours(point, input)
  var cost = 5 - validNeighbours.size
  for (n in validNeighbours) {
    if (
      input[n.first][n.second] != input[point.first][point.second]) {
      cost++
    }
  }
  return cost
}

private fun edges(
  input: List<String>,
  point: Pair<Int, Int>,
  mapped: MutableSet<Pair<Pair<Int, Int>, Char>>,
): Int {
  var edges = 0
  for (dir in listOf('U', 'R', 'D', 'L')) {
    if (mapped.contains(Pair(point, dir))) {
      continue
    }
    mapped.add(Pair(point, dir))
    val next = next(point, dir)
    if (!isValid(next, input) || get(next, input) != get(point, input)) {
      edges++
      mapEdge(point, dir, next(dir), mapped, input)
      mapEdge(point, dir, prev(dir), mapped, input)
    }
  }
  return edges
}

private fun mapEdge(
  point: Pair<Int, Int>,
  edgeDir: Char,
  travelDir: Char,
  mapped: MutableSet<Pair<Pair<Int, Int>, Char>>,
  input: List<String>,
) {
  var current = point
  while (true) {
    val edgePoint = next(current, edgeDir)
    if (!isValid(current, input) || get(current, input) != get(point, input)) {
      break
    }

    if (isValid(edgePoint, input) && get(edgePoint, input) == get(current, input)) {
      break
    }

    mapped.add(Pair(current, edgeDir))
    current = next(current, travelDir)
  }
}

private fun next(dir: Char) = when (dir) {
  'U' -> 'R'
  'R' -> 'D'
  'D' -> 'L'
  'L' -> 'U'
  else -> throw IllegalArgumentException("Bad dir $dir")
}

private fun prev(dir: Char) = when (dir) {
  'U' -> 'L'
  'R' -> 'U'
  'D' -> 'R'
  'L' -> 'D'
  else -> throw IllegalArgumentException("Bad dir $dir")
}

private fun next(point: Pair<Int, Int>, dir: Char) = when (dir) {
  'U' -> Pair(point.first - 1, point.second)
  'R' -> Pair(point.first, point.second + 1)
  'D' -> Pair(point.first + 1, point.second)
  'L' -> Pair(point.first, point.second - 1)
  else -> throw IllegalArgumentException("Bad dir $dir")
}

private fun isValid(point: Pair<Int, Int>, input: List<String>) =
  point.first >= 0
      && point.second >= 0
      && point.first <= input.lastIndex
      && point.second <= input[point.first].lastIndex

private fun get(point: Pair<Int, Int>, input: List<String>) =
  input[point.first][point.second]
