package io.github.aj8gh.aok.y24.d12

fun part1(input: List<String>): Int {
  var cost = 0
  val mapped = mutableSetOf<Pair<Int, Int>>()
  for (i in input.indices) {
    for (j in input[i].indices) {
      val result = map(input, Pair(i, j), mapped)
      cost += result.first * result.second
    }
  }
  return cost
}

fun part2(input: List<String>) = 0

private fun map(
  input: List<String>,
  point: Pair<Int, Int>,
  mapped: MutableSet<Pair<Int, Int>>,
  cost: Pair<Int, Int> = Pair(0, 0),
): Pair<Int, Int> {
  var area = cost.first
  var perimeter = cost.second
  val type = input[point.first][point.second]
  val neighbours = validNeighbours(point, input)
  for (n in neighbours) {
    if (mapped.contains(n) || input[n.first][n.second] != type) {
      continue
    }
    area++
    perimeter += perimeter(input, n)
    mapped.add(n)
    val newCost = map(input, n, mapped, Pair(area, perimeter))
    area = newCost.first
    perimeter = newCost.second
  }
  return Pair(area, perimeter)
}

private fun validNeighbours(point: Pair<Int, Int>, input: List<String>) =
  listOf(
    point,
    Pair(point.first + 1, point.second),
    Pair(point.first - 1, point.second),
    Pair(point.first, point.second + 1),
    Pair(point.first, point.second - 1),
  ).filter {
    it.first >= 0
        && it.second >= 0
        && it.first <= input.lastIndex
        && it.second <= input[it.first].lastIndex
  }

private fun perimeter(input: List<String>, point: Pair<Int, Int>): Int {
  val validNeighbours = validNeighbours(point, input)
  var cost = 5 - validNeighbours.size
  for (n in validNeighbours) {
    if (input[n.first][n.second] != input[point.first][point.second]) {
      cost++
    }
  }
  return cost
}
