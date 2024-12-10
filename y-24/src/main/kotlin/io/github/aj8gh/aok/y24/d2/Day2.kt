package io.github.aj8gh.aok.y24.d2

import kotlin.math.abs

private val re = Regex("\\s+")
fun part1(input: List<String>): Int {
  val reports = parse(input)
  val out = toAllDiffs(reports)
  return out.filter { isValid(it) }.size
}

fun part2(input: List<String>): Int {
  val reports = parse(input)
  val out = toAllDiffs(reports)
  return out.filterIndexed { i, it -> isValidTolerant(reports[i], it) }.size
}

private fun parse(input: List<String>) =
  input.map { it.split(re).map { n -> n.toInt() } }

private fun toAllDiffs(reports: List<List<Int>>) = reports.map { toDiffs(it) }

private fun toDiffs(report: List<Int>): List<Int> {
  val diffs = mutableListOf<Int>()
  for (j in report.indices) {
    if (j + 1 >= report.size) {
      continue
    }
    val a = report[j]
    val b = report[j + 1]
    val diff = a - b
    diffs.add(diff)
  }
  return diffs
}

private fun isValidTolerant(report: List<Int>, diffs: List<Int>): Boolean {
  if (isValid(diffs)) {
    return true
  }
  for (i in report.indices) {
    val newDiff = toDiffs(report.filterIndexed { j, _ -> j != i })
    if (isValid(newDiff)) {
      return true
    }
  }
  return false
}

private fun isValid(diffs: List<Int>) =
  diffs.all { abs(it) in 1..3 }
      && diffs.map { it > 0 }.toSet().size == 1
