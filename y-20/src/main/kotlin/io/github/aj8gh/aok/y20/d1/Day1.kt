package io.github.aj8gh.aok.y20.d1

const val TARGET = 2020

fun part1(input: List<String>): Int {
  val neededToPair = mutableMapOf<Int, Int>()
  for (s in input) {
    val n = s.toInt()
    neededToPair[n]?.let { return n * it }
    neededToPair[TARGET - n] = n
  }
  error("No answer found")
}

fun part2(input: List<String>): Int {
  val sorted = input.map { it.toInt() }.sorted()
  for ((i, n) in sorted.withIndex()) {
    val need = TARGET - n
    var j = i + 1
    var k = sorted.lastIndex
    while (j < k) {
      val a = sorted[j]
      val b = sorted[k]
      val sum = a + b
      when {
        sum == need -> return a * b * n
        sum > need -> k--
        else -> j++
      }
    }
  }
  error("No answer found")
}
