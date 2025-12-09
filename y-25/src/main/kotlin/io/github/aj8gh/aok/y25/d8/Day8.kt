package io.github.aj8gh.aok.y25.d8

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2
import kotlin.Int.Companion.MAX_VALUE
import kotlin.collections.Map.Entry
import kotlin.math.pow
import kotlin.math.sqrt

fun part1(input: List<String>, connections: Int) = solve(input, connections, LEVEL_1)

fun part2(input: List<String>) = solve(input = input, level = LEVEL_2)

private fun solve(input: List<String>, connections: Int = MAX_VALUE, level: Int): Long {
  val boxes = input.map { l ->
    l.split(",")
      .map { s -> s.toLong() }
      .let { Triple(it[0], it[1], it[2]) }
  }

  val closestPairs = distances(boxes).entries.sortedBy { it.value }
  val circuits = connect(closestPairs, connections, boxes.toSet())

  return if (level == LEVEL_1) circuits.map { it.size }
    .sortedDescending()
    .slice(0..2)
    .reduce { a, b -> a * b }.toLong()
  else circuits.first().first().first * circuits.first().last().first
}

private fun connect(
  distances: List<Entry<Set<Triple<Long, Long, Long>>, Double>>,
  connections: Int = MAX_VALUE,
  boxes: Set<Triple<Long, Long, Long>>,
): Set<Set<Triple<Long, Long, Long>>> {
  var circuits = boxes.map { setOf(it) }.toSet()
  for ((i, e) in distances.withIndex()) {
    if (i == connections) {
      break
    }
    circuits = connect(e.key, circuits)
    if (connections == MAX_VALUE && circuits.size == 1) {
      return setOf(e.key)
    }
  }
  return circuits
}

private fun connect(
  box: Set<Triple<Long, Long, Long>>,
  circuits: Set<Set<Triple<Long, Long, Long>>>,
): Set<Set<Triple<Long, Long, Long>>> {
  val out = mutableSetOf<Set<Triple<Long, Long, Long>>>()
  val newCircuit = mutableSetOf<Triple<Long, Long, Long>>()
  for (c in circuits) {
    if (c.contains(box.first()) && c.contains(box.last())) {
      return circuits
    }
    if (c.contains(box.first()) || c.contains(box.last())) {
      newCircuit.addAll(box)
      newCircuit.addAll(c)
    } else {
      out.add(c)
    }
  }
  if (newCircuit.isEmpty()) {
    out.add(box)
  } else {
    out.add(newCircuit)
  }
  return out
}

private fun distances(points: List<Triple<Long, Long, Long>>) =
  (0..<points.lastIndex).flatMap { i ->
    (i + 1..points.lastIndex).map { j -> setOf(points[i], points[j]) }
  }.associateWith { distance(it.first(), it.last()) }

private fun distance(
  p: Triple<Long, Long, Long>,
  q: Triple<Long, Long, Long>
) = sqrt(
  (p.first - q.first).toDouble().pow(2) +
      (p.second - q.second).toDouble().pow(2) +
      (p.third - q.third).toDouble().pow(2)
)
