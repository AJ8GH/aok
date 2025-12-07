package io.github.aj8gh.aok.y25.d7

private const val START = 'S'
private const val SPLITTER = '^'

fun part1(input: List<String>): Long {
  var splits = 0L
  val beams = mutableSetOf(input.first().indices.first { input.first()[it] == START })

  for (row in input.slice(1..input.lastIndex)) {
    for (beam in beams.toSet()) {
      if (row[beam] == SPLITTER) {
        splits++
        beams.remove(beam)
        beams.add(beam - 1)
        beams.add(beam + 1)
      }
    }
  }

  return splits
}

fun part2(input: List<String>): Long {
  val timelines = input.first().indices
    .filter { input.first()[it] == START }
    .associateWith { 1L }
    .toMutableMap()

  for (row in input.slice(1..input.lastIndex)) {
    for (k in timelines.keys.toSet()) {
      if (row[k] == SPLITTER) {
        timelines[k - 1] = (timelines[k - 1] ?: 0) + timelines[k]!!
        timelines[k + 1] = (timelines[k + 1] ?: 0) + timelines[k]!!
        timelines.remove(k)
      }
    }
  }

  return timelines.values.sum()
}
