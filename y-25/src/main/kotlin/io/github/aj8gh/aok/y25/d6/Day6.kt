package io.github.aj8gh.aok.y25.d6

private val spaces = Regex("\\s+")

fun part1(input: List<String>): Long {
  val nums = input
    .slice(0..<input.lastIndex)
    .map {
      it.split(spaces)
        .filter { s -> s.isNotBlank() }
        .map { s -> s.toLong() }
    }

  val operators = input.last().split(spaces)

  var total = 0L
  for (i in nums.first().indices) {
    val operands = mutableListOf<Long>()
    for (l in nums) operands.add(l[i])

    total += calculate(operands, operators[i])
  }
  return total
}

fun part2(input: List<String>) = 0L

private fun calculate(operands: List<Long>, operator: String) = operands
  .reduce { a, b ->
    if (operator == "+") a + b else a * b
  }
