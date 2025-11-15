package io.github.aj8gh.aok.y24.d10

fun part1(input: List<String>): Int {
  var total = 0
  for (i in input.indices) {
    for (j in input[i].indices) {
      if (input[i][j] == '0') {
        total += score(input, Pair(i, j), mutableSetOf())
      }
    }
  }
  return total
}

fun part2(input: List<String>) = 0

private fun score(
  input: List<String>,
  point: Pair<Int, Int>,
  found: MutableSet<Pair<Int, Int>>
): Int {
  listOf(
    Pair(point.first + 1, point.second),
    Pair(point.first - 1, point.second),
    Pair(point.first, point.second + 1),
    Pair(point.first, point.second - 1),
  ).filter {
    it.first >= 0
        && it.second >= 0
        && it.first < input.size
        && it.second < input[it.first].length
  }.forEach {
    val newVal = input[it.first][it.second]
    if (isNext(input[point.first][point.second], newVal)) {
      if (newVal == '9') {
        found.add(it)
      }
      score(input, it, found)
    }
  }

  return found.size
}

private fun isNext(old: Char, new: Char) = when (old) {
  '0' -> new == '1'
  '1' -> new == '2'
  '2' -> new == '3'
  '3' -> new == '4'
  '4' -> new == '5'
  '5' -> new == '6'
  '6' -> new == '7'
  '7' -> new == '8'
  '8' -> new == '9'
  else -> false
}
