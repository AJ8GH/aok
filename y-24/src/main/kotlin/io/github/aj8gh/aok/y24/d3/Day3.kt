package io.github.aj8gh.aok.y24.d3

private const val DO = "do()"
private const val DO_NOT = "don't()"

private val re = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
private val re2 = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)")
private val digit = Regex("\\d+")

fun part1(input: List<String>) =
  parse(input, re).sumOf { it.sumOf { s -> mul(s) } }

fun part2(input: List<String>): Int {
  val parsed = parse(input, re2)
  var doing = true
  var sum = 0
  for (line in parsed) {
    for (s in line) {
      when {
        s == DO -> doing = true
        s == DO_NOT -> doing = false
        doing -> sum += mul(s)
      }
    }
  }
  return sum
}

private fun parse(input: List<String>, regex: Regex) = input.map {
  regex.findAll(it)
    .toList()
    .map { matchResult -> matchResult.value }
}

private fun mul(mul: String) = digit.findAll(mul)
  .toList()
  .map { n -> n.value.toInt() }
  .reduce(Int::times)
