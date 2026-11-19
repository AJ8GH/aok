package io.github.aj8gh.aok.y20.d6

fun part1(input: List<String>) = countYeses(input)

fun part2(input: List<String>) = countYeses(input, true)

private fun countYeses(input: List<String>, unanimous: Boolean = false): Int {
  var total = 0
  val group = IntArray(26)
  var peopleInGroup = 0
  for ((i, line) in input.withIndex()) {
    if (line.isNotEmpty()) peopleInGroup++
    for (char in line.trim()) {
      group[char.code - 97] += 1
    }
    if (i == input.lastIndex || line.isEmpty()) {
      for (count in group) {
        if ((!unanimous && count > 0) || count == peopleInGroup) total++
      }
      group.fill(0)
      peopleInGroup = 0
      continue
    }
  }
  return total
}
