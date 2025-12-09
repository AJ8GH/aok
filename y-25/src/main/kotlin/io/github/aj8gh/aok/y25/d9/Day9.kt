package io.github.aj8gh.aok.y25.d9

import kotlin.math.abs
import kotlin.math.max

fun part1(input: List<String>): Long {
  val points = input.map {
    it.split(",")
      .let { s ->
        Pair(s.first().toLong(), s.last().toLong())
      }
  }

  return maxArea(points)
}

fun part2(input: List<String>) = 0L

private fun maxArea(points: List<Pair<Long, Long>>): Long {
  var max = 0L
  for (i in 0..<points.lastIndex) {
    for (j in i + 1..points.lastIndex) {
      val a = points[i]
      val b = points[j]
      val area = (abs(a.first - b.first) + 1) * (abs(a.second - b.second) + 1)
      max = max(max, area)
    }
  }
  return max
}
