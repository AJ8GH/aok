package io.github.aj8gh.aok.y25.d6

private const val ADD = "+"

private val sRgx = Regex("\\s+")
private val opRgx = Regex("[+*]\\s+")

fun part1(input: List<String>): Long {
  val nums = input.slice(0..<input.lastIndex)
    .map { it.split(sRgx).filter { s -> s.isNotBlank() }.map { s -> s.toLong() } }
  val operators = input.last().split(sRgx)
  return nums.first().indices.mapIndexed { i, _ ->
    calculate(nums.map { it[i] }, operators[i])
  }.sum()
}

fun part2(input: List<String>): Long {
  val ops = opRgx.findAll(input.last()).map { it.value }.toList()
  var total = 0L
  var i = 0
  for (j in ops.indices) {
    val op = ops[j]
    val rightIndex = (op.lastIndex + i).let { if (j == ops.lastIndex) it else it - 1 }
    val nums = mutableListOf<Long>()
    for (k in rightIndex downTo i) {
      var num = 0L
      for (line in input.slice(0..<input.lastIndex)) {
        line[k].takeIf { it != ' ' }?.let {
          num += num * 9L + it.digitToInt()
        }
      }
      nums.add(num)
    }
    i += op.length
    total += calculate(nums, op.first().toString())
  }
  return total
}

private fun calculate(operands: List<Long>, operator: String) =
  operands.reduce { a, b ->
    when (operator) {
      ADD -> a + b
      else -> a * b
    }
  }
