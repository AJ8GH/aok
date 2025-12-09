package io.github.aj8gh.aok.y25.d8

import kotlin.math.pow
import kotlin.math.sqrt

// d(p,q) = sqrt( (p1-q1)^2 + (p2-q2)^2 + (p3-q3)^2 )

fun part1(input: List<String>, connections: Int): Int {
  val boxes = input.map { l ->
    l.split(",")
      .map { s -> s.toInt() }
      .let { Triple(it[0], it[1], it[2]) }
  }

  val closestPairs = minDistance(boxes).entries.sortedBy { it.value }
  val circuits = mutableSetOf<MutableSet<Triple<Int, Int, Int>>>()

  var i = 0
  var allCircuits = setOf<Triple<Int, Int, Int>>()
  outer@ for (e in closestPairs) {
    if (i >= connections - 1) {
      if (!allCircuits.contains(e.key.first())) {
        circuits.add(mutableSetOf(e.key.first()))
      }
      if (!allCircuits.contains(e.key.last())) {
        circuits.add(mutableSetOf(e.key.last()))
      }
    } else {
      if (circuits.isEmpty()) {
        circuits.add(e.key.toMutableSet())
        i++
        continue@outer
      }
      val found = mutableSetOf<Triple<Int, Int, Int>>()
      for (c in circuits.toSet()) {
        if (c.containsAll(e.key)) {
          continue@outer
        }
        if (c.contains(e.key.first()) || c.contains(e.key.last())) {
          found.addAll(e.key)
          found.addAll(c)
          circuits.remove(c)
        }
      }
      if (found.isEmpty()) {
        circuits.add(e.key.toMutableSet())
      } else {
        circuits.add(found)
      }

      i++
      if (i == connections - 1) {
        allCircuits = circuits.flatten().toSet()
      }
    }
  }



  return circuits.map { it.size }
    .sortedDescending()
    .slice(0..2)
    .reduce { a, b -> a * b }
}

fun part2(input: List<String>) = 0

private fun minDistance(
  points: List<Triple<Int, Int, Int>>
): Map<Set<Triple<Int, Int, Int>>, Double> {
  val closest = mutableMapOf<Set<Triple<Int, Int, Int>>, Double>()

  for (i in 0..<points.lastIndex) {
    for (j in i + 1..points.lastIndex) {
      closest[setOf(points[i], points[j])] = distance(points[i], points[j])
    }
  }
  return closest
}

private fun distance(
  p: Triple<Int, Int, Int>,
  q: Triple<Int, Int, Int>
) = sqrt(
  (p.first - q.first).toDouble().pow(2) +
      (p.second - q.second).toDouble().pow(2) +
      (p.third - q.third).toDouble().pow(2)
)
