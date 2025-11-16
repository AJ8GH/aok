package io.github.aj8gh.aok.y24.d11

fun part1(input: List<String>) = solve(input, 25)

fun part2(input: List<String>) = solve(input, 75)

private fun solve(input: List<String>, cycles: Int): Long {
  val cache = input.first()
    .split(" ")
    .map { it.toLong() }
    .groupingBy { it }
    .eachCount()
    .mapValues { it.value.toLong() }
    .toMutableMap()

  repeat(cycles) {
    val merge = mutableMapOf<Long, Long>()
    cache.entries.toMutableList().forEach {
      when {
        it.key == 0L -> add(merge, 1L, it.value)
        it.key.toString().length % 2 == 0 -> {
          val str = it.key.toString()
          val a = str.take(str.length / 2).toLong()
          val b = str.substring(str.length / 2).toLong()
          add(merge, a, it.value)
          add(merge, b, it.value)
        }

        else -> add(merge, it.key * 2024, it.value)
      }
      cache.remove(it.key)
    }
    merge.forEach { e -> add(cache, e.key, e.value) }
    cache
  }

  return cache.values.sum()
}

private fun add(m: MutableMap<Long, Long>, k: Long, v: Long) {
  val existing = m.computeIfAbsent(k) { 0L }
  m[k] = existing + v
}
