package io.github.aj8gh.aok.y24.d7

const val ADD = "+"
const val MULT = "*"
const val CONCAT = "||"

val permutationCaches = listOf(
  mutableMapOf(Pair(1, listOf(listOf(ADD), listOf(MULT)))),
  mutableMapOf(Pair(1, listOf(listOf(ADD), listOf(MULT), listOf(CONCAT))))
)

fun part1(input: List<String>) = solve(input, 1)

fun part2(input: List<String>) = solve(input, 2)

private fun solve(input: List<String>, level: Int) = input
  .map { Regex("\\d+").findAll(it).map { r -> r.value.toLong() }.toList() }
  .map { Pair(it.first(), it.slice(1..<it.size)) }
  .filter { isSolvable(it, level) }
  .sumOf { it.first }

private fun isSolvable(nums: Pair<Long, List<Long>>, level: Int): Boolean {
  val permutations = permutations(nums.second.size - 1, level)
  for (perm in permutations) {
    var total = nums.second[0]
    for (i in perm.indices) {
      val b = nums.second[i + 1]
      total = apply(total, b, perm[i])
    }

    if (total == nums.first) return true
  }

  return false
}

private fun permutations(size: Int, level: Int): List<List<String>> {
  permutationCaches[level - 1].let { cache ->
    for (i in cache.keys.max() + 1..size) {
      cache[i] = cache[1]!!.flatten()
        .flatMap { op ->
          cache[i - 1]!!.map {
            it + op
          }
        }
    }
    return cache[size]!!
  }
}

private fun apply(a: Long, b: Long, op: String) =
  when (op) {
    ADD -> a + b
    MULT -> a * b
    CONCAT -> (a.toString() + b.toString()).toLong()
    else -> throw IllegalArgumentException("Unknown operator $op")
  }
