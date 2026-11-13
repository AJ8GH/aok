package io.github.aj8gh.aok.y20.d2

val digits = Regex("\\d+")
val chars = Regex("[a-z]+")

fun part1(input: List<String>): Int {
  var validPasswords = 0
  for (line in input) {
    val (min, max) = digits.findAll(line).map { it.value.toInt() }.toList()
    val (restricted, password) = chars.findAll(line).map { it.value }.toList()
    var count = 0
    for (c in password) {
      if (c == restricted.first()) count++
      if (count > max) break
    }
    if (count in min..max) validPasswords++
  }
  return validPasswords
}

fun part2(input: List<String>) = 0
