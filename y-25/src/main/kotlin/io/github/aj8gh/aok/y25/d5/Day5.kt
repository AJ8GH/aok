package io.github.aj8gh.aok.y25.d5

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2
import kotlin.math.max
import kotlin.math.min

var DIGITS = Regex("\\d+")

fun part1(input: List<String>) = parse(input, LEVEL_1).let {
  it.second.filter { id ->
    it.first.any { r -> r.contains(id) }
  }.size.toLong()
}

fun part2(input: List<String>) = parse(input, LEVEL_2).first.let {
  merge(it.sortedBy { r -> r.first })
    .sumOf { r -> r.last - r.first + 1 }
}

private fun parse(input: List<String>, level: Int) = input
  .map { DIGITS.findAll(it).map { r -> r.value.toLong() } }
  .let { digits ->
    Pair(
      digits.filter { it.count() == 2 }.map { it.first()..it.last() },
      if (level == LEVEL_2) listOf()
      else digits.filter { it.count() == 1 }.map { it.first() }
    )
  }

private fun merge(ranges: List<LongRange>): List<LongRange> {
  val merged = mutableListOf<LongRange>()
  var i = 0
  while (i <= ranges.lastIndex) {
    var a = ranges[i]
    var j = i + 1
    while (j <= ranges.lastIndex) {
      val b = ranges[j]
      if (a.contains(b.first) || a.contains(b.last)) {
        a = merge(a, b)
        j++
      } else break
    }
    merged.add(a)
    i = j
  }
  return merged
}

private fun merge(a: LongRange, b: LongRange) =
  min(a.first, b.first)..max(a.last, b.last)
