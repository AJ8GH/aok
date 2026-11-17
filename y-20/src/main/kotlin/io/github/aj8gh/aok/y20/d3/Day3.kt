package io.github.aj8gh.aok.y20.d3

const val TREE = '#'

fun part1(input: List<String>) = countTrees(input, Pair(3, 1))

fun part2(input: List<String>) =
  listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
    .map { countTrees(input, it) }
    .reduce { a, b -> a * b }

private fun countTrees(input: List<String>, velocity: Pair<Int, Int>): Long {
  var trees = 0L
  var (i, j) = Pair(0, 0)
  val (right, down) = velocity
  while (i in input.indices) {
    if (input[i][j] == TREE) trees++
    j = (j + right) % input[i].length
    i += down
  }
  return trees
}
