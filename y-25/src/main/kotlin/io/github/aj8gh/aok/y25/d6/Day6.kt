package io.github.aj8gh.aok.y25.d6

private val spacesRgx = Regex("\\s+")
private val opChunkRgx = Regex("[+*]\\s+")

fun part1(input: List<String>): Long {
  val nums = input
    .slice(0..<input.lastIndex)
    .map {
      it.split(spacesRgx)
        .filter { s -> s.isNotBlank() }
        .map { s -> s.toLong() }
    }

  val operators = input.last().split(spacesRgx)

  var total = 0L
  for (i in nums.first().indices) {
    val operands = mutableListOf<Long>()
    for (l in nums) operands.add(l[i])

    total += calculate(operands, operators[i])
  }
  return total
}

fun part2(input: List<String>): Long {
  val operators = opChunkRgx.findAll(input.last()).map { it.value }.toList()

  var total = 0L
  var j = 0
  for (i in operators.indices) {
    val opChunk = operators[i]
    var endNumI =
      if (i == operators.lastIndex) opChunk.lastIndex + j
      else opChunk.lastIndex + j - 1

    val nums = mutableListOf<Long>()
    for (k in endNumI downTo j) {
      var n = 0L
      for (s in input.slice(0..<input.lastIndex)) {
        val c = s[k]
        if (c == ' ') {
          continue
        }
        n *= 10L
        n += c.digitToInt()
      }
      nums.add(n)
    }
    j += opChunk.length
    total += calculate(nums, opChunk.first().toString())
  }
  return total
}

private fun calculate(operands: List<Long>, operator: String) = operands
  .reduce { a, b ->
    if (operator == "+") a + b else a * b
  }
