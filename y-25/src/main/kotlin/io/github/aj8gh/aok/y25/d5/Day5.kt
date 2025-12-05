package io.github.aj8gh.aok.y25.d5

import kotlin.math.max
import kotlin.math.min

var DIGITS = Regex("\\d+")

fun part1(input: List<String>): Long {
  val digits = input.map { DIGITS.findAll(it).map { r -> r.value.toLong() } }
  val ranges = digits.filter { it.count() == 2 }.map { it.first()..it.last() }
  val ids = digits.filter { it.count() == 1 }.map { it.first() }
  return ids.filter { isFresh(it, ranges) }.size.toLong()
}

fun part2(input: List<String>): Long {
  val digits = input.map { DIGITS.findAll(it).map { r -> r.value.toLong() } }
  val ranges = digits.filter { it.count() == 2 }.map { it.first()..it.last() }
  var merged = merge(ranges)
  var didMerge = ranges.size != merged.size
  while (didMerge) {
    val newMerged = merge(merged)
    didMerge = newMerged.size != merged.size
    merged = newMerged
  }
  return merged.sumOf { it.last - it.first + 1 }
}

private fun isFresh(id: Long, ranges: List<LongRange>) = ranges.any { it.contains(id) }

private fun merge(ranges: List<LongRange>): List<LongRange> {
  val processed = ranges.associateWith { false }.toMutableMap()
  val merged = mutableListOf<LongRange>()
  for (a in processed.keys) {
    if (processed[a]!!) continue
    processed[a] = true

    var result = a
    for (b in processed.keys) {
      if (processed[b]!!) continue
      if (isOverlapping(result, b)) {
        result = min(result.first, b.first)..max(result.last, b.last)
        processed[b] = true
      }
    }
    merged.add(result)
  }
  return merged
}

private fun isOverlapping(a: LongRange, b: LongRange): Boolean {
  if (a.first <= b.last && a.first >= b.first) {
    return true
  }

  if (b.first <= a.last && b.first >= a.first) {
    return true
  }

  return false
}
